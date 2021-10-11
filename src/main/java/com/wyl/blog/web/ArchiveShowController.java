package com.wyl.blog.web;

import com.wyl.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author russe
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;
    @GetMapping("/ArchivePage")
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "ArchivePage";
    }
}
