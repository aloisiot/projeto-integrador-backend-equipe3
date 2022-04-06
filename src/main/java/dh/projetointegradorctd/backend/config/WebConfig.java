package dh.projetointegradorctd.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan("com.baeldung.web")
public class WebConfig implements WebMvcConfigurer {

    public static final String BASE_PATH = "/api/v1";

    @Profile(value = {"dev"})
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS");

        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(BASE_PATH, HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
