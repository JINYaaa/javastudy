package com.qdc.demoeurekazuul.config;

import com.qdc.demoeurekazuul.filter.*;
import org.springframework.context.annotation.Bean;

public class FilterConfig {
    @Bean
    public MyPreFilter myPreFilter(){
        return new MyPreFilter();
    }
    @Bean
    public MyPreFilter2 myPreFilter2(){
        return new MyPreFilter2();
    }
    @Bean
    public MyRountingFilter myRountingFilter(){
        return new MyRountingFilter();
    }
    @Bean
    public MyRountingFilter2 myRountingFilter2(){
        return new MyRountingFilter2();
    }
    @Bean
    public MyErrorFilter myErrorFilter(){
        return new MyErrorFilter();
    }
    @Bean
    public MyPostFilter myPostFilter(){
        return new MyPostFilter();
    }
}
