package com.wyl.blog.web.admin;

import com.wyl.blog.entity.Tag;
import com.wyl.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author russe
 */

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/TagManagePage")
    public String tags(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/TagManagePage";
    }

    @GetMapping("TagManagePage/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/TagInputPage";
    }

    @GetMapping("TagManagePage/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/TagInputPage";
    }

    @GetMapping("TagManagePage/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "delete successful");
        return "redirect:/admin/TagManagePage";
    }

    @PostMapping("/TagManagePage")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "This Tag exists already!");
        }
        if (result.hasErrors()) {
            return "admin/TagInputPage";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "Tag creates failed");
        } else {
            attributes.addFlashAttribute("message", "Tag creates success");
        }
        return "redirect:/admin/TagManagePage";
    }

    @PostMapping("/TagManagePage/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "This Tag exists already!");
        }
        if (result.hasErrors()) {
            return "admin/TagInputPage";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "Tag updates failed");
        } else {
            attributes.addFlashAttribute("message", "Tag updates success");
        }
        return "redirect:/admin/TagManagePage";
    }
}
