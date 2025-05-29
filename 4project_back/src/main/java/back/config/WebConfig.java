package back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 또는 "/**"
                .allowedOrigins("http://192.168.0.32:8080", "http://192.168.0.32:3000", "http://localhost:3000") // 프론트 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // 쿠키 허용!
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 실제 파일 경로: 예) /home/user/uploads/ 또는 C:/project/uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/uploads/"); // 상대 경로일 경우 (JAR 기준)
    }
    
}
