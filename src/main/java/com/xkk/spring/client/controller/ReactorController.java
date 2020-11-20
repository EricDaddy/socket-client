package com.xkk.spring.client.controller;

import com.xkk.spring.client.reactor.ReactorTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/5 15:13
 */
@RestController
@RequestMapping("reactor")
public class ReactorController {
    @Autowired
    private ReactorTest reactorTest;

    @RequestMapping("mutiReactor")
    public void sendToSocket() throws IOException {
        reactorTest.connectServer();
    }
}
