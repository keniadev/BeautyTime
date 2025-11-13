package org.esfe.BeautyTimeApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ruta real donde se guardan los archivos
        Path uploadDir = Paths.get("src/main/resources/static/uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Expone /uploads/** para que el navegador pueda acceder a las imágenes
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
