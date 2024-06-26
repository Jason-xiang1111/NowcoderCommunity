package com.peaceful.community.controller;

import com.peaceful.community.domain.DiscussPost;
import com.peaceful.community.domain.Page;
import com.peaceful.community.domain.User;
import com.peaceful.community.service.DiscussPostService;
import com.peaceful.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        // 方法调用前，SpringMVC会自动实例化Model和Page，并将Page注入Model
        // 所以，在thymeleaf中可以直接访问Page对象中的数据
        // 分页相关信息
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        // 将查询的帖子中uer_id与用户的username相匹配
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list != null)
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                // 查询Post表中的user_id的user
                User user = userService.findUserById((post.getUserId()));
                map.put("user", user);
                discussPosts.add(map);
            }

        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage(){
        return "/error/500";
    }

}
