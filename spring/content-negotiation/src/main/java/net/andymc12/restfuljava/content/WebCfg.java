package net.andymc12.restfuljava.content;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Component
@ComponentScan("net.andymc12.restfuljava.content")
public class WebCfg implements WebMvcConfigurer {

   @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
       messageConverters.add(new YamlHttpMessageConverter());
       messageConverters.add(new MappingJackson2HttpMessageConverter());
       messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
    }
}