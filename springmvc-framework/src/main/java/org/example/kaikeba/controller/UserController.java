package org.example.kaikeba.controller;

import org.example.kaikeba.annotation.Controller;
import org.example.kaikeba.annotation.RequestMapping;
import org.example.kaikeba.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {


    @RequestMapping("query")
    @ResponseBody
    public Map<String, Object> queryUser(Integer id, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        return map;
    }

    @RequestMapping("save")
    @ResponseBody
    public void saveUser() {
        System.out.println("保存用户");
    }

}
