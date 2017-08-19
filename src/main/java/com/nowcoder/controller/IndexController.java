package com.nowcoder.controller;

import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.User;
import com.nowcoder.service.ToutiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Administrator on 2017/8/10.
 */
//@Controller
public class IndexController {
    private static final Logger logger= LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index() {
        logger.info("visit index:");
        return "hello NowCoder"+"<br> say:"+toutiaoService.say();
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId, @PathVariable("userId") int userId, @RequestParam(value = "type", defaultValue = "1") int type, @RequestParam(value = "key", defaultValue = "nowcoder") String key) {
        return String.format("GID:{%s},UID:{%d},TYPE:{%d},KEY:{%s}", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"})
    public String news(Model model) {
        model.addAttribute("value1", "vv1");
        List<String> strList = Arrays.asList(new String[]{"red", "yello", "blue"});
        Map<Integer, Integer> intMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            intMap.put(Integer.valueOf(i * i), Integer.valueOf(i * i * i));
        }
        model.addAttribute("strList", strList);
        model.addAttribute("intMap", intMap);
        //model.addAttribute("user", new User("xiaoming", 25));
        return "news";
    }

    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        for (Cookie cookie : request.getCookies()) {
            sb.append("Cookie:");
            sb.append(cookie.getName() + ":" + cookie.getValue() + "<br>");
        }
        return sb.toString();
    }

    @RequestMapping("/response")
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcoderid, @RequestParam(value = "key", defaultValue = "key") String key, @RequestParam(value = "value", defaultValue = "value") String value, HttpServletResponse response) {
        response.addCookie(new Cookie(key, value));
        return "nowcoderid from cookie:" + nowcoderid;
    }

    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code) {
        RedirectView red = new RedirectView("/vm", true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value="key",required = false) String key){
        if("admin".equals(key)){
            return "hello";
        }
        throw new IllegalArgumentException("key 错了！");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "ERROR:"+e.getMessage();
    }
}

