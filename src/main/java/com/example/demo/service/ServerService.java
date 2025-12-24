package com.example.demo.service;



import com.example.demo.model.Server;
import com.example.demo.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    @Autowired
    private ServerRepository repo;

    public Server createServer(String name) {
        Server server = new Server(name);
        return repo.save(server);
    }

    public List<Server> getAllServers() {
        return repo.findAll();
    }

    public Server renameServer(Long id, String newName) {
        Server server = repo.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
        server.setName(newName);
        return repo.save(server);
    }

    public Server startServer(Long id) {
        Server server = repo.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
        server.setStatus(true);
        return repo.save(server);
    }

    public Server stopServer(Long id) {
        Server server = repo.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
        server.setStatus(false);
        return repo.save(server);
    }

    public void deleteServer(Long id) {
        Server server = repo.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
        if (server.getStatus()) {
            throw new RuntimeException("Cannot delete a running server");
        }
        repo.delete(server);
    }
}
