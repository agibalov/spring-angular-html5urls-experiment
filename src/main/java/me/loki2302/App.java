package me.loki2302;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SpringBootApplication
public class App extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
            }
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/bower_components/**")
                .addResourceLocations("classpath:/resources/bower_components/");

        registry.addResourceHandler(
                "/",
                "/page1",
                "/page2",
                "/pages/{.*}"
        ).addResourceLocations("classpath:/resources/index.html")
                .resourceChain(true)
                .addResolver(new ConstantResourceResolver());
    }

    @RestController
    public static class HomeController {
        @RequestMapping("/test")
        public String hello() {
            return "hello there!";
        }
    }

    public static class ConstantResourceResolver extends AbstractResourceResolver {
        @Override
        protected Resource resolveResourceInternal(
                HttpServletRequest request,
                String requestPath,
                List<? extends Resource> locations,
                ResourceResolverChain chain) {

            if(locations.size() != 1) {
                throw new RuntimeException("There should be exactly one location, got " + locations.size());
            }

            return locations.get(0);
        }

        @Override
        protected String resolveUrlPathInternal(
                String resourceUrlPath,
                List<? extends Resource> locations,
                ResourceResolverChain chain) {

            return null;
        }
    }
}
