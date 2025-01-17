package com.caching.demo.controllers;

import com.caching.demo.helpers.DemoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/server")
    public void createServer() {
        this.demoService.addServer();
    }

    @PostMapping("/request")
    public void createRequest() {
        this.demoService.serveRequest();
    }
}
