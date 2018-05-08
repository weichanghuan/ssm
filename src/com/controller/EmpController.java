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
    // 调用业务
    // @Autowired实现自动装配,默认按类型装配
    @Autowired
    private EmpService empService;// =new EmpServiceImpl();

    /*
     * @Value("${A_A}") private String a;
     */

    // 支持日期转换
    @InitBinder
    // 调用控制器前执行
    public void ss(WebDataBinder wdb) {
        wdb.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @RequestMapping("/show.s")
    public String show(Model model) {
        // 调用业务方法
        List<Emp> emps = empService.getAllEmp();
        model.addAttribute("emps", emps);
        return "index"; // 返回页面
    }

    @RequestMapping("/delEmp.s")
    public String del(int id) {
        int temp = empService.delEmpBy(id);
        return "redirect:show.s"; // 返回页面
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
        // 下拉列表显示部门
        model.addAttribute("fq", fq);
        return "fuzzyquery"; // 返回页面
    }

    @RequestMapping(value = "/addEmp.s", method = RequestMethod.POST)
    public String addemp(Emp emp, Model model) {
        int temp = empService.addEmp(emp);
        if (temp > 0) {
            // 获取所有员工
            List<Emp> emps = empService.getAllEmp();
            // 利用model将值传递给页面
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
            // 利用model将值传递给页面
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
            // 获取所有员工
            List<Emp> emps = empService.getAllEmp();
            // 利用model将值传递给页面
            model.addAttribute("emps", emps);
            // ....
            return "index";
        } else {
            return "redirect:error.jsp";
        }
    }

    @RequestMapping(value = "/test/{id}")
    public void updateemp(@PathVariable Integer id) {
        System.out.println("进入了");
        System.out.println(id);
    }

    // 实现文件上传
    @RequestMapping("/upload.s")
    public void upload(HttpServletRequest request, @RequestParam(value = "file") CommonsMultipartFile file, String pname) throws Exception {
        // 注意一个文件对应一个CommonsMultipartFile类的对象
        /*
         * System.out.println("获取上传文件的名称:"+file.getOriginalFilename());
         * System.out.println("获取上传文件的类型:"+file.getContentType());
         * System.out.println("获取上传文件的大小:"+file.getSize());
         */
        // 上传文件
        String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
        File file1 = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file1.exists() && !file1.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file1.mkdir();
        }
        // 1.将文件保存在服务器目录
        // 获取保存文件的位置
        // 文件名(即为上传文件的名.用户自定)
        String uploadFileName = file.getOriginalFilename();
        // 扩展名(即为上传文件的扩展名.用户自定)
        String expname = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        // 生成新的文件名
        String fileName = System.currentTimeMillis() + expname;
        // 文件的完成路径
        savePath = savePath + File.separator + fileName;
        File saveFile = new File(savePath); // 创建文件
        file.transferTo(saveFile);// 保存文件

    }

    /**
     * 读取某个文件夹下的所有文件
     */
    public boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
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
     * 删除某个文件夹下的所有文件夹和文件
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
                        System.out.println("删除文件成功");
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
        return "fileupload"; // 返回页面
    }

    // 实现文件上传
    @RequestMapping("/uploadfile.s")
    public String uploadfile(HttpServletRequest request, @RequestParam(value = "file") CommonsMultipartFile file, String pname) throws Exception {
        // 注意一个文件对应一个CommonsMultipartFile类的对象
        /*
         * System.out.println("获取上传文件的名称:"+file.getOriginalFilename());
         * System.out.println("获取上传文件的类型:"+file.getContentType());
         * System.out.println("获取上传文件的大小:"+file.getSize());
         */
        // 上传文件
        String savePath = "E:\\ServerFiles";
        File file1 = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file1.exists() && !file1.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file1.mkdir();
        }
        // 1.将文件保存在服务器目录
        // 获取保存文件的位置
        // 文件名(即为上传文件的名.用户自定)
        String uploadFileName = file.getOriginalFilename();
        // 扩展名(即为上传文件的扩展名.用户自定)
        String expname = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        // 生成新的文件名
        String fileName = System.currentTimeMillis() + expname;
        // 文件的完成路径
        savePath = savePath + File.separator + fileName;
        File saveFile = new File(savePath); // 创建文件
        file.transferTo(saveFile);// 保存文件
        return "fileupload"; // 返回页面
    }

    @RequestMapping("/fileuploadAjaxPage.s")
    public String fileAjax() {
        return "ajaxfileupload"; // 返回页面
    }

    @RequestMapping("/importPicFile.s")
    @ResponseBody
    public String uploadfileAjax(HttpServletRequest request, @RequestParam(value = "file") CommonsMultipartFile file, String pname,
            HttpServletResponse response) throws Exception {
        // 注意一个文件对应一个CommonsMultipartFile类的对象
        /*
         * System.out.println("获取上传文件的名称:"+file.getOriginalFilename());
         * System.out.println("获取上传文件的类型:"+file.getContentType());
         * System.out.println("获取上传文件的大小:"+file.getSize());
         */
        // 上传文件
        String savePath = "E:\\ServerFiles";
        File file1 = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file1.exists() && !file1.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file1.mkdir();
        }
        // 1.将文件保存在服务器目录
        // 获取保存文件的位置
        // 文件名(即为上传文件的名.用户自定)
        String uploadFileName = file.getOriginalFilename();
        // 扩展名(即为上传文件的扩展名.用户自定)
        String expname = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        // 生成新的文件名
        String fileName = System.currentTimeMillis() + expname;
        // 文件的完成路径
        savePath = savePath + File.separator + fileName;
        File saveFile = new File(savePath); // 创建文件
        file.transferTo(saveFile);// 保存文件
        response.setContentType("text/json");
        /* 设置字符集为'UTF-8' */
        response.setCharacterEncoding("UTF-8");
        Result result = new Result();
        result.setResult(new String("成功".getBytes("UTF-8"), "UTF-8"));
        String jSonString = JSONUtil.toJSonString(result);
        return new String(jSonString.getBytes("UTF-8"), "UTF-8");
    }

    @RequestMapping("/md5.s")
    public String md5() {
        return "md5"; // 返回页面
    }
}
