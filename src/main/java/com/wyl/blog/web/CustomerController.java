package com.wyl.blog.web;

import com.wyl.blog.entity.Customer;
import com.wyl.blog.service.BlogService;
import com.wyl.blog.service.CustomerService;
import com.wyl.blog.service.TagService;
import com.wyl.blog.service.TypeService;
import com.wyl.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author russe
 */
@Controller
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public String loginPage(){
        return "CustomerLogin";
    }

    @GetMapping("/Register")
    public String registerPage(Model model){
        model.addAttribute("customer",new Customer());
        return "Register";
    }

    @PostMapping("/")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session,
                          RedirectAttributes attributes, @PageableDefault(size = 5,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Customer customer= customerService.checkCustomer(username, password);
        if(customer != null){
            //customer.setPassword(null);
            session.setAttribute("customer", customer);
            model.addAttribute("page",blogService.listBlog(pageable));
            model.addAttribute("types",typeService.listTypeTop(6));
            model.addAttribute("tags",tagService.listTagTop(10));
            model.addAttribute("recommendBlogs",blogService.listBlogTop(4));
            return "index";
        }
        else{
            attributes.addFlashAttribute("message","Your nickname or password wrong");
            return "redirect:";
        }
    }

    @PostMapping("/Register")
    public String doRegister(Customer customer,RedirectAttributes attributes){
        Customer c = customerService.addCustomer(customer);
        c.setPassword(MD5Util.code(c.getPassword()));
        if (c == null) {
            attributes.addFlashAttribute("message", "Customer create failed");
        } else {
            attributes.addFlashAttribute("message", "Customer creates successful");
        }
        return "redirect:";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("customer");
        return "redirect:";
    }
}
