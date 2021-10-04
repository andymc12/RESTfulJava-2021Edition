package net.andymc12.restfuljava.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class IPAddressCheckFilterWebCfg implements WebMvcConfigurer {

   @Autowired
   IPAddressCheckFilter ipAddressCheckFilter;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(ipAddressCheckFilter);
   }
}
