package com.cssw.tutorial.client;

import com.cssw.tutorial.client.model.Client;
import com.cssw.tutorial.client.model.ClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Client", description = "API of client")
@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "Method that returns list of clients")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ClientDTO> findAll() {
        List<Client> clients = this.clientService.findAll();
        return clients.stream().map(e -> mapper.map(e, ClientDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update", description = "Method that saves or updates a client")
    @RequestMapping(value = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClientDTO dto) {
        this.clientService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes a client")
    @RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws Exception {
        this.clientService.delete(id);
    }

}
