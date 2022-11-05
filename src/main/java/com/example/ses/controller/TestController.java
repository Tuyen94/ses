package com.example.ses.controller;

import com.example.ses.socket.SesServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final SesServer sesServer;

    @RequestMapping("/test")
    public String test(@RequestParam int messageNumberPerSecond, @RequestParam int times) throws Exception {
        sesServer.sendRequest(messageNumberPerSecond, times);
        return "Ok";
    }
}
