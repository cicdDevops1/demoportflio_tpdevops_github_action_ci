package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Blog;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.BlogRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.BlogService;

import java.util.List;

@Service
public class BlogIServiceImplement implements BlogService {

    private final BlogRepository blogRepository;
    private final SectionRepository sectionRepository;
    private final AuthService authService;
    private final MessageSource messageSource;

    public BlogIServiceImplement(BlogRepository blogRepository,
                                 SectionRepository sectionRepository,
                                 AuthService authService,
                                 MessageSource messageSource) {
        this.blogRepository = blogRepository;
        this.sectionRepository = sectionRepository;
        this.authService = authService;
        this.messageSource = messageSource;
    }

    // 🔹 Lister tous les blogs d’un utilisateur par slug
    @Override
    public List<Blog> getAllBlogs(String slug) {
        return blogRepository.findBySectionUserSlugAndSectionSection(slug, Section.Sections.Blogs);    }

    @Override
    public Blog getBlogById(Long id, String slug) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("blog.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        if (!blog.getSection().getUser().getSlug().equals(slug)) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("blog.notfound.slug", new Object[]{id}, LocaleContextHolder.getLocale())
            );
        }
        return blog;
    }

    @Override
    public Blog addBlog(Blog blog, String slug) {
        User currentUser = authService.getCurrentUser();
        if (!currentUser.getSlug().equals(slug)) {
            throw new AccessDeniedException(
                    messageSource.getMessage("blog.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        Long sectionId = blog.getSection().getId();
        Section sectionFromDb = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{sectionId}, LocaleContextHolder.getLocale())
                ));

        if (!sectionFromDb.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("blog.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        blog.setSection(sectionFromDb);
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Blog blog, String slug) {
        Blog existing = blogRepository.findById(blog.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("blog.notfound", new Object[]{blog.getId()}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        checkOwnership(currentUser, existing);

        // ✅ On ne change pas la section
        existing.setTitre(blog.getTitre());
        existing.setContenu(blog.getContenu());
        existing.setDatePublication(blog.getDatePublication());
        existing.setResume(blog.getResume());
        existing.setCategorie(blog.getCategorie());
        existing.setImagePrincipale(blog.getImagePrincipale());

        return blogRepository.save(existing);
    }

    @Override
    public Object deleteBlog(Long id, String slug) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("blog.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        checkOwnership(currentUser, blog);

        blogRepository.delete(blog);
        return null;
    }

    // 🔹 Vérifie que le blog appartient bien à l’utilisateur
    private void checkOwnership(User currentUser, Blog blog) {
        if (!blog.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("blog.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }
    }
}
