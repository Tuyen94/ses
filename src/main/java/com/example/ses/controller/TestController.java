package com.example.ses.controller;

import com.example.ses.socket.SesServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(@RequestParam int messageNumberPerSecond, @RequestParam int times) throws Exception {
        SesServer.sendRequest(messageNumberPerSecond, times);
        return "Ok";
    }
}
