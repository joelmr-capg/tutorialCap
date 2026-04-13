package com.cssw.tutorial.client;

import com.cssw.tutorial.client.model.Client;
import com.cssw.tutorial.client.model.ClientDTO;

import java.util.List;

public interface ClientService {

    Client get(Long id);

    List<Client> findAll();

    void save(Long id, ClientDTO dto);

    void delete(Long id) throws Exception;
}
