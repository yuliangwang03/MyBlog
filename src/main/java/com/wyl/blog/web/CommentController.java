package com.wyl.blog.web;

import com.wyl.blog.dao.CommentRepository;
import com.wyl.blog.entity.Comment;
import com.wyl.blog.entity.User;
import com.wyl.blog.service.BlogService;
import com.wyl.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author russe
 */
@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    // @Value(value = "${avatar}")
    private String avatar = "/images/face.jpg";

    @Transactional
    @GetMapping("comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> commentList = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "DetailPage :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        /**根据blogId查询到对应的blog，并set到comment中*/
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar((user.getAvatar()));
            comment.setAdminComment(true);
            comment.setUsername(user.getUsername());
        }
        else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
