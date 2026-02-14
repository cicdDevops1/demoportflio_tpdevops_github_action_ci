package com.example.demoportflio_tpdevops_github_action_ci.controller;

import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import com.example.demoportflio_tpdevops_github_action_ci.model.Blog;

import com.example.demoportflio_tpdevops_github_action_ci.service.BlogService;



@RestController
@RequestMapping("/{slug}/blogs")
public class BlogController extends BaseController {

    private final BlogService blogService;


    public BlogController(BlogService blogService, MessageSource messageSource) {
        super(messageSource);
        this.blogService = blogService;

    }

    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> addBlog(@Valid @RequestBody Blog blog,
                                          @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "blog.create-success" ,  blogService.addBlog(blog, slug)  ,lang,
                HttpStatus.OK

        );
    }

    @GetMapping("/lists")
    public ResponseEntity<Object> listBlog(@RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "blog.list-success" ,  blogService.getAllBlogs(slug)  ,lang,
                HttpStatus.OK

        );
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getBlogById(@PathVariable Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "blog.detail-success" , blogService.getBlogById(id, slug)  ,lang,
                HttpStatus.OK
        );
    }

    @PostMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updateBlog(@Valid @RequestBody Blog blog,
                                             @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "blog.update-success" ,  blogService.updateBlog(blog, slug)  ,lang,
                HttpStatus.OK

        );
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deleteBlog(@PathVariable Long id,
                                             @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {

        return buildResponse(
                "blog.delete-success" , blogService.deleteBlog(id, slug)  ,lang,
                HttpStatus.OK

        );
    }
}
