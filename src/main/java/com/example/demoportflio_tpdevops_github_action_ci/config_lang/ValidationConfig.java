package com.example.demoportflio_tpdevops_github_action_ci.config_lang;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ValidationConfig implements WebMvcConfigurer {

    private LocalValidatorFactoryBean validatorFactory;

    @Bean
    public LocalValidatorFactoryBean validatorFactory(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        this.validatorFactory = bean;
        return bean;
    }

    @Override
    public Validator getValidator() {
        return this.validatorFactory;
    }
}
