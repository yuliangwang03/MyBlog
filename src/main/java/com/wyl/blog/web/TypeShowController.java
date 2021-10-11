package com.wyl.blog.web;

import com.wyl.blog.entity.Type;
import com.wyl.blog.service.BlogService;
import com.wyl.blog.service.TypeService;
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
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/SortPage/{id}")
    public String types(@PageableDefault(size = 5,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model, @PathVariable Long id){
        List<Type> types = typeService.listTypeTop(1000);
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId",id);
        return "SortPage";
    }
}
