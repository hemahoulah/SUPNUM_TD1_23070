package com.example.demo.controller;


import com.example.demo.model.Server;
import com.example.demo.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servers")
public class ServerController {

    @Autowired
    private ServerService service;

    @PostMapping
    public Server create(@RequestParam String name) {
        return service.createServer(name);
    }

    @GetMapping
    public List<Server> list() {
        return service.getAllServers();
    }

    @PutMapping("/{id}/rename")
    public Server rename(@PathVariable Long id, @RequestParam String name) {
        return service.renameServer(id, name);
    }

    @PutMapping("/{id}/start")
    public Server start(@PathVariable Long id) {
        return service.startServer(id);
    }

    @PutMapping("/{id}/stop")
    public Server stop(@PathVariable Long id) {
        return service.stopServer(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteServer(id);
    }
}

