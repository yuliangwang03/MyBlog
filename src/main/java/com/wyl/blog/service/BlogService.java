package com.wyl.blog.service;

import com.wyl.blog.entity.Blog;
import com.wyl.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    /**列出符合所有query参数的文章*/
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    /**列出符合tag或type id的文章*/
    Page<Blog> listBlog(Long id, Pageable pageable);


    /**列出符合query参数的文章*/
    Page<Blog> listBlog(String query,Pageable pageable);


    /**列出最新文章*/
    List<Blog> listBlogTop(Integer size);

    /**String 对应的年份， List<Blog> 是当前年份post的文章列表*/
    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
