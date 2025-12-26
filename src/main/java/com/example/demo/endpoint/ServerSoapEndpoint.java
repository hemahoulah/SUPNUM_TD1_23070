package com.example.demo.endpoint;

import com.example.demo.service.ServerService;
// import com.example.demo.soap.CreateServerRequest;
// import com.example.demo.soap.CreateServerResponse;
// import com.example.demo.soap.ListServersResponse;
// import com.example.demo.soap.Server;
// import com.example.demo.soap.ServerIdRequest;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class ServerSoapEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/server";

    private final ServerService serverService;

    public ServerSoapEndpoint(ServerService serverService) {
        this.serverService = serverService;
    }

    // CREATE SERVER
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createServerRequest")
    @ResponsePayload
    public CreateServerResponse createServer(@RequestPayload CreateServerRequest request) {

        // Créer le serveur via le service
        com.example.demo.model.Server serverModel = serverService.createServer(request.getName());

        // Convertir le modèle JPA en objet SOAP (JAXB)
        Server serverSoap = new Server();
        serverSoap.setId(serverModel.getId());
        serverSoap.setName(serverModel.getName());
        serverSoap.setStatus(serverModel.getStatus()); // <-- utiliser getStatus()

        CreateServerResponse response = new CreateServerResponse();
        response.setServer(serverSoap);
        return response;
    }

    // LIST SERVERS
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listServersRequest")
    @ResponsePayload
    public ListServersResponse listServers() {

        List<com.example.demo.model.Server> serversModel = serverService.getAllServers();

        ListServersResponse response = new ListServersResponse();
        List<Server> serversSoap = serversModel.stream().map(s -> {
            Server soap = new Server();
            soap.setId(s.getId());
            soap.setName(s.getName());
            soap.setStatus(s.getStatus()); // <-- utiliser getStatus()
            return soap;
        }).collect(Collectors.toList());

        response.getServers().addAll(serversSoap);
        return response;
    }

    // START SERVER
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "serverIdRequest")
    public void startServer(@RequestPayload ServerIdRequest request) {
        serverService.startServer(request.getId());
    }

    // STOP SERVER
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "serverIdRequest")
    public void stopServer(@RequestPayload ServerIdRequest request) {
        serverService.stopServer(request.getId());
    }

    // DELETE SERVER
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "serverIdRequest")
    public void deleteServer(@RequestPayload ServerIdRequest request) {
        serverService.deleteServer(request.getId());
    }
}





