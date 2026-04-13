package com.cssw.tutorial.client;

import com.cssw.tutorial.client.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
