package com.wyl.blog.web.admin;

import com.wyl.blog.entity.Type;
import com.wyl.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/TypeManagePage")
    public String types(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        model.addAttribute("page", typeService.listType(pageable));
        return "admin/TypeManagePage";
    }

    @GetMapping("/TypeManagePage/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/TypeInputPage";
    }

    @GetMapping("/TypeManagePage/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/TypeInputPage";
    }

    @GetMapping("/TypeManagePage/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "delete successful");
        return "redirect:/admin/TypeManagePage";
    }

    @PostMapping("/TypeManagePage")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName((type.getName()));
        if (type1 != null) {
            result.rejectValue("name", "nameError", "This type exists already!");
        }
        if (result.hasErrors()) {
            return "admin/TypeInputPage";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "Type create failed");
        } else {
            attributes.addFlashAttribute("message", "Type creates successful");
        }
        return "redirect:/admin/TypeManagePage";
    }

    @PostMapping("/TypeManagePage/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName((type.getName()));
        if (type1 != null) {
            result.rejectValue("name", "nameError", "This type exists already!");
        }
        if (result.hasErrors()) {
            return "admin/TypeInputPage";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "Type updates failed");
        } else {
            attributes.addFlashAttribute("message", "Type updates successful");
        }
        return "redirect:/admin/TypeManagePage";
    }


}
