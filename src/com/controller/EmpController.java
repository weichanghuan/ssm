package com.controller;

import com.entity.Emp;
import com.service.EmpService;
import com.util.EmpsCondition;
import com.util.Page;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class EmpController {
    // ����ҵ��
    // @Autowiredʵ���Զ�װ��,Ĭ�ϰ�����װ��
    @Autowired
    private EmpService empService;// =new EmpServiceImpl();

    /*
     * @Value("${A_A}") private String a;
     */

    // ֧������ת��
    @InitBinder
    // ���ÿ�����ǰִ��
    public void ss(WebDataBinder wdb) {
        wdb.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @RequestMapping("/show.s")
    public String show(Model model) {
        // ����ҵ�񷽷�
        List<Emp> emps = empService.getAllEmp();
        model.addAttribute("emps", emps);
        return "index"; // ����ҳ��
    }

    @RequestMapping("/delEmp.s")
    public String del(int id) {
        int temp = empService.delEmpBy(id);
        return "redirect:show.s"; // ����ҳ��
    }

    @RequestMapping(value = "/fuzzyquery.s", method = RequestMethod.GET)
    public String fuzzyquery(Model model, String fq, Integer pageno) {
        pageno = (pageno == null ? 1 : pageno);
        EmpsCondition empsCondition = new EmpsCondition();
        empsCondition.setStr(fq);
        Page page = new Page();
        page.setPageno(pageno == null ? 1 : pageno);
        page.setPagesize(2);
        page = this.empService.getEmpsByEmpsCondition(empsCondition, page);
        List<Emp> e = page.getList();
        model.addAttribute("emps", e);
        model.addAttribute("page", page);
        // �����б���ʾ����
        model.addAttribute("fq", fq);
        return "fuzzyquery"; // ����ҳ��
    }

    @RequestMapping(value = "/addEmp.s", method = RequestMethod.POST)
    public String addemp(Emp emp, Model model) {
        int temp = empService.addEmp(emp);
        if (temp > 0) {
            // ��ȡ����Ա��
            List<Emp> emps = empService.getAllEmp();
            // ����model��ֵ���ݸ�ҳ��
            model.addAttribute("emps", emps);
            // ....
            return "index";
        } else {
            return "redirect:error.jsp";
        }

    }

    @RequestMapping(value = "/update.s", method = RequestMethod.GET)
    public String update(int id, Model model) {
        try {
            Emp emp = empService.getEmpByid(id);
            // ����model��ֵ���ݸ�ҳ��
            model.addAttribute("emp", emp);
            // ....
            return "update";
        } catch (Exception e) {
            // TODO: handle exception
            return "redirect:error.jsp";
        }

    }

    @RequestMapping(value = "/updateemp.s", method = RequestMethod.POST)
    public String updateemp(Emp emp, Model model) {
        int temp = empService.updateEmp(emp);
        if (temp > 0) {
            // ��ȡ����Ա��
            List<Emp> emps = empService.getAllEmp();
            // ����model��ֵ���ݸ�ҳ��
            model.addAttribute("emps", emps);
            // ....
            return "index";
        } else {
            return "redirect:error.jsp";
        }
    }

    @RequestMapping(value = "/test/{id}")
    public void updateemp(@PathVariable Integer id) {
        System.out.println("������");
        System.out.println(id);
    }

    // ʵ���ļ��ϴ�
    @RequestMapping("/upload.s")
    public void upload(HttpServletRequest request, @RequestParam(value = "file") CommonsMultipartFile file, String pname) throws Exception {
        // ע��һ���ļ���Ӧһ��CommonsMultipartFile��Ķ���
        /*
         * System.out.println("��ȡ�ϴ��ļ�������:"+file.getOriginalFilename());
         * System.out.println("��ȡ�ϴ��ļ�������:"+file.getContentType());
         * System.out.println("��ȡ�ϴ��ļ��Ĵ�С:"+file.getSize());
         */
        // �ϴ��ļ�
        String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
        File file1 = new File(savePath);
        // �ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
        if (!file1.exists() && !file1.isDirectory()) {
            System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
            // ����Ŀ¼
            file1.mkdir();
        }
        // 1.���ļ������ڷ�����Ŀ¼
        // ��ȡ�����ļ���λ��
        // �ļ���(��Ϊ�ϴ��ļ�����.�û��Զ�)
        String uploadFileName = file.getOriginalFilename();
        // ��չ��(��Ϊ�ϴ��ļ�����չ��.�û��Զ�)
        String expname = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        // �����µ��ļ���
        String fileName = System.currentTimeMillis() + expname;
        // �ļ������·��
        savePath = savePath + File.separator + fileName;
        File saveFile = new File(savePath); // �����ļ�
        file.transferTo(saveFile);// �����ļ�

    }

    /**
     * ��ȡĳ���ļ����µ������ļ�
     */
    public boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("�ļ�");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("�ļ���");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath=" + readfile.getAbsolutePath());
                        System.out.println("name=" + readfile.getName());

                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    /**
     * ɾ��ĳ���ļ����µ������ļ��к��ļ�
     */

    public boolean deletefile(String delpath) throws FileNotFoundException, IOException {
        try {

            File file = new File(delpath);
            if (!file.isDirectory()) {
                System.out.println("1");
                file.delete();
            } else if (file.isDirectory()) {
                System.out.println("2");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        System.out.println("path=" + delfile.getPath());
                        System.out.println("absolutepath=" + delfile.getAbsolutePath());
                        System.out.println("name=" + delfile.getName());
                        delfile.delete();
                        System.out.println("ɾ���ļ��ɹ�");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "\\" + filelist[i]);
                    }
                }
                file.delete();

            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile()   Exception:" + e.getMessage());
        }
        return true;
    }

    @RequestMapping("/fileuploadPage.s")
    public String file() {
        return "fileupload"; // ����ҳ��
    }

    // ʵ���ļ��ϴ�
    @RequestMapping("/uploadfile.s")
    public String uploadfile(HttpServletRequest request, @RequestParam(value = "file") CommonsMultipartFile file, String pname) throws Exception {
        // ע��һ���ļ���Ӧһ��CommonsMultipartFile��Ķ���
        /*
         * System.out.println("��ȡ�ϴ��ļ�������:"+file.getOriginalFilename());
         * System.out.println("��ȡ�ϴ��ļ�������:"+file.getContentType());
         * System.out.println("��ȡ�ϴ��ļ��Ĵ�С:"+file.getSize());
         */
        // �ϴ��ļ�
        String savePath = "E:\\ServerFiles";
        File file1 = new File(savePath);
        // �ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
        if (!file1.exists() && !file1.isDirectory()) {
            System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
            // ����Ŀ¼
            file1.mkdir();
        }
        // 1.���ļ������ڷ�����Ŀ¼
        // ��ȡ�����ļ���λ��
        // �ļ���(��Ϊ�ϴ��ļ�����.�û��Զ�)
        String uploadFileName = file.getOriginalFilename();
        // ��չ��(��Ϊ�ϴ��ļ�����չ��.�û��Զ�)
        String expname = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        // �����µ��ļ���
        String fileName = System.currentTimeMillis() + expname;
        // �ļ������·��
        savePath = savePath + File.separator + fileName;
        File saveFile = new File(savePath); // �����ļ�
        file.transferTo(saveFile);// �����ļ�
        return "fileupload"; // ����ҳ��
    }

    @RequestMapping("/fileuploadAjaxPage.s")
    public String fileAjax() {
        return "ajaxfileupload"; // ����ҳ��
    }

    @RequestMapping("/importPicFile.s")
    @ResponseBody
    public String uploadfileAjax(HttpServletRequest request, @RequestParam(value = "file") CommonsMultipartFile file, String pname,
            HttpServletResponse response) throws Exception {
        // ע��һ���ļ���Ӧһ��CommonsMultipartFile��Ķ���
        /*
         * System.out.println("��ȡ�ϴ��ļ�������:"+file.getOriginalFilename());
         * System.out.println("��ȡ�ϴ��ļ�������:"+file.getContentType());
         * System.out.println("��ȡ�ϴ��ļ��Ĵ�С:"+file.getSize());
         */
        // �ϴ��ļ�
        String savePath = "E:\\ServerFiles";
        File file1 = new File(savePath);
        // �ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
        if (!file1.exists() && !file1.isDirectory()) {
            System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
            // ����Ŀ¼
            file1.mkdir();
        }
        // 1.���ļ������ڷ�����Ŀ¼
        // ��ȡ�����ļ���λ��
        // �ļ���(��Ϊ�ϴ��ļ�����.�û��Զ�)
        String uploadFileName = file.getOriginalFilename();
        // ��չ��(��Ϊ�ϴ��ļ�����չ��.�û��Զ�)
        String expname = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        // �����µ��ļ���
        String fileName = System.currentTimeMillis() + expname;
        // �ļ������·��
        savePath = savePath + File.separator + fileName;
        File saveFile = new File(savePath); // �����ļ�
        file.transferTo(saveFile);// �����ļ�
        response.setContentType("text/json");
        /* �����ַ���Ϊ'UTF-8' */
        response.setCharacterEncoding("UTF-8");
        Result result = new Result();
        result.setResult(new String("�ɹ�".getBytes("UTF-8"), "UTF-8"));
        String jSonString = JSONUtil.toJSonString(result);
        return new String(jSonString.getBytes("UTF-8"), "UTF-8");
    }

    @RequestMapping("/md5.s")
    public String md5() {
        return "md5"; // ����ҳ��
    }
}
