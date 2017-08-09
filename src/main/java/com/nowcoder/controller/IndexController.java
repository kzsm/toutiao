package com.nowcoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

/**
 * Created by Administrator on 2017/8/10.
 */
@Controller
public class IndexController {
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "hello NowCoder";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId, @PathVariable("userId") int userId, @RequestParam(value = "type", defaultValue = "1") int type, @RequestParam(value = "key", defaultValue = "nowcoder") String key) {
        return String.format("GID:{%s},UID:{%d},TYPE:{%d},KEY:{%s}", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"})
    public String news(Model model){
        model.addAttribute("value1","vv1");
        return "news";
    }
}

