package ru.belyaeva.springapp1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("ru.belyaeva.springapp1")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    //этот интерфейс реализуется, если надо настроить SpringMvc под себя
    //вместо стандартного шаблонизатора мы используем Thymeleaf
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    // Declare your Static resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("style/**").addResourceLocations("/WEB-INF/views/cats/style/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("images/**").addResourceLocations("/WEB-INF/views/cats/images/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("cats/style/**").addResourceLocations("/WEB-INF/views/cats/style/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("cats/images/**").addResourceLocations("/WEB-INF/views/cats/images/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("cats/22/images/**").addResourceLocations("/WEB-INF/views/cats/images/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("cats/22/style/**").addResourceLocations("/WEB-INF/views/cats/style/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("cats/23/images/**").addResourceLocations("/WEB-INF/views/cats/images/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("cats/23/style/**").addResourceLocations("/WEB-INF/views/cats/style/")
                .setCachePeriod(31556926);


        /*registry.addResourceHandler("cats/**").addResourceLocations("/WEB-INF/views/cats/style/")
                .setCachePeriod(31556926);*/
    }
}
