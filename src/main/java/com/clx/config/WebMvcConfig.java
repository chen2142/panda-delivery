package com.clx.config;

import com.clx.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * Message converter that extends the mvc framework
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("Extended message converter");
        //Create a message converter object
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //Set up the object converter and use jackson to convert Java objects to json.
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //Append the above message converter object to the converter collection of the mvc framework
        converters.add(0,messageConverter);
    }
}
