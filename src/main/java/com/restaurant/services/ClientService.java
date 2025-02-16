package com.restaurant.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restaurant.entities.Client;
import com.restaurant.repositories.ClientRepository;
import com.restaurant.requests.ClientRequest;
import com.restaurant.responses.ClientResponse;

@Service
public class ClientService {

    @Value("${constants.pagination.size}")
    private int pageSize;

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponse save(ClientRequest clientRequest) {
        Client client = convertRequestToEntity(clientRequest);
        clientRepository.save(client);
        return convertEntityToResponse(client);
    }

    public ClientResponse find(long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null)
            return null;
        return convertEntityToResponse(client);
    }

    public ClientResponse findByIdCard(String idCard) {
        Client client = clientRepository.findByIdCard(idCard).orElse(null);
        if (client == null)
            return null;
        return convertEntityToResponse(client);
    }

    public List<ClientResponse> list() {
        return clientRepository.findAll().stream().map(this::convertEntityToResponse).toList();
    }

    public List<ClientResponse> list(String field) {
        return clientRepository.findAll(Sort.by(field)).stream().map(this::convertEntityToResponse).toList();
    }

    public List<ClientResponse> list(int page) {
        return clientRepository.findAll(PageRequest.of(page, pageSize)).map(this::convertEntityToResponse).toList();
    }

    public List<ClientResponse> list(int page, String field) {
        return clientRepository.findAll(PageRequest.of(page, pageSize, Sort.by(field))).map(this::convertEntityToResponse).toList();
    }

    private ClientResponse convertEntityToResponse(Client client) {
        return ClientResponse
                .builder()
                .id(client.getId())
                .email(client.getEmail())
                .phone(client.getPhone())
                .idCard(client.getIdCard())
                .lastname(client.getLastname())
                .firstname(client.getFirstname())
                .build();
    }

    private Client convertRequestToEntity(ClientRequest clientRequest) {
        return Client
                .builder()
                .id(clientRequest.id())
                .email(clientRequest.email())
                .phone(clientRequest.phone())
                .idCard(clientRequest.idCard())
                .dateCreation(LocalDateTime.now())
                .lastname(clientRequest.lastname())
                .firstname(clientRequest.firstname())
                .build();
    }
}
