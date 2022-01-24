package com.wj.hystrix;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//    @HystrixCommand(
//            fallbackMethod = "getDefaultUser",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000" )
//            }
//    )
    @RequestMapping(value = "/")
    public String hello() throws InterruptedException {
        Thread.sleep(3000);
        return "Welcome Hystrix";
    }

    public String getDefaultUser() {

        return "getDefaultUser";
    }
}
