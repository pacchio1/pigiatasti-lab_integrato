package it.Pigiatasti.SeVedemo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //config.addAllowedOrigin("127.0.0.1");  // Set this to your frontend URL in a production environment
        config.addAllowedOrigin("http://localhost");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);


        System.out.println("CORS Configuration:");
        System.out.println("Allowed Origin: " + config.getAllowedOrigins());
        System.out.println("Allowed Headers: " + config.getAllowedHeaders());
        System.out.println("Allowed Methods: " + config.getAllowedMethods());
        return new CorsFilter(source);
    }
}
