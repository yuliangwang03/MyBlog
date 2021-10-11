package com.wyl.blog.service;

import com.wyl.blog.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author russe
 */

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);


}
