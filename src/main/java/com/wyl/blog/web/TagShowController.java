package com.wyl.blog.web;

import com.wyl.blog.entity.Tag;
import com.wyl.blog.service.BlogService;
import com.wyl.blog.service.TagService;
import com.wyl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author russe
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/TagPage/{id}")
    public String tag(@PageableDefault(size = 5,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model, @PathVariable Long id){
        List<Tag> tags = tagService.listTagTop(1000);
        if(id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId",id);
        return "TagPage";
    }
}
