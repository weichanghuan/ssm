package com.controller;

import com.entity.User;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    private Map<String, User> userList = new HashMap<String, User>();

    public TestController() {
        userList.put("1", new User("1", "Dt30", "1111", "dt30@qq.com"));
        userList.put("2", new User("2", "Dt30", "2222", "dt30@qq.com"));
        userList.put("3", new User("3", "Dt30", "3333", "dt30@qq.com"));
        userList.put("4", new User("4", "Dt30", "4444", "dt30@qq.com"));
    }

    @RequestMapping("/chk.s")
    @ResponseBody
    public String show(HttpServletRequest request) {
        for (Map.Entry<String, User> user : userList.entrySet()) {
            System.out.println(user.hashCode());
        }
        return JSONUtil.toJSonString(userList);
    }

    @RequestMapping("/notify.s")
    @ResponseBody
    public String notify(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        Enumeration<String> er = request.getParameterNames();
        while (er.hasMoreElements()) {
            String name = (String) er.nextElement();
            String value = request.getParameter(name);
            if (isFirst) {
                sb.append(name + "=" + value);
                isFirst = false;
            }
            else {
                if (value != null) {
                    sb.append("&" + name + "=" + value);
                }
                else {
                    sb.append("&" + name + "=");
                }
            }
        }
        System.out.println( sb.toString());
        return "SUCCESS";
    }


}
