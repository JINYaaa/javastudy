package com.qdc.demoeurekaconsumer1.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    //依赖restTemple对象
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @RequestMapping(value = "/users")
    public String testAllUser(){
        return restTemplate.getForObject("http://EUREKA-PROVIDER1/user/userall",String.class);
    }

    @RequestMapping(value = "/details/{userid}")
    public String testgetUserById(@PathVariable(value = "userid")String id){
        return restTemplate.getForObject("http://EUREKA-PROVIDER1/user/details?userid="+id,String.class);
    }

    @RequestMapping(value = "/port1")
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

//    @RequestMapping(value = "/addUser")
//    public ResponseEntity<String> testaddUser(@ResponseBody User user){
//        return restTemplate.postForEntity("http://EUREKA-PROVIDER1/user/add",user,String.class)
//    }

    @RequestMapping(value = "/port")
    public String testPort(){
        return restTemplate.getForObject("http://EUREKA-PROVIDER1/port",String.class);
    }

    @GetMapping("/sayHi")
    @HystrixCommand(fallbackMethod = "sayFallback",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "30000")})
    public String hello(@RequestParam(value = "sleep_seconds") int sleep_seconds) throws InterruptedException{
        ServiceInstance serviceInstance=loadBalancerClient.choose("eureka-provider1");
        String url="http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/user/sayHi?sleep_seconds="+sleep_seconds;
        System.out.println(url);
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.getForObject(url,String.class);
    }

    public String sayHiFallback(int sleep_seconds){
        return "服务器User暂时无法响应，请稍侯……";
    }



}
