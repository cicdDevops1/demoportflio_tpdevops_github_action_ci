package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

public class SlugUtils {

        public static String generateSlug(String name, Long id) {
            if (name == null) return id != null ? id.toString() : "";
            String slug = name.toLowerCase();
            slug = slug.replaceAll("[\\s_]+", "_");
            slug = slug.replaceAll("[^a-z0-9_]", "");
            if (id != null) {
                slug = slug + "_" + id;
            }
            return slug;
        }


}
