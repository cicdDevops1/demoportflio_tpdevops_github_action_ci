package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Blog;

public interface BlogService {
     List<Blog> getAllBlogs(String slug);
     Blog getBlogById(Long id,  String slug);
     Blog addBlog(Blog blog, String slug);
     Blog updateBlog(Blog blog, String slug);



     Object deleteBlog(Long id, String slug);
}
