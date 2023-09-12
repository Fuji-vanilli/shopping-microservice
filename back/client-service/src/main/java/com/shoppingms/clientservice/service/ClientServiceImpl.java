package com.shoppingms.clientservice.service;

import com.shoppingms.clientservice.dto.ClientMapper;
import com.shoppingms.clientservice.dto.ClientRequest;
import com.shoppingms.clientservice.model.Client;
import com.shoppingms.clientservice.repository.ClientRepository;
import com.shoppingms.clientservice.utils.Response;
import com.shoppingms.clientservice.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    @Override
    public Response add(ClientRequest request) {
        List<String> errors= ClientValidator.validate(request);
        if(!errors.isEmpty()){
            log.error("some request not valid");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    Map.of(
                            "errors", errors
                    ),
                    "some request not valid"
            );
        }
        if(clientRepository.existsByEmail(request.getEmail())){
            log.error("client with the email: {} is already exist on database", request.getEmail());
            return generateResponse(
                    HttpStatus.CONFLICT,
                    null,
                    null,
                    "client with the email: "+request.getEmail()+" is already exist on database"
            );
        }

        Client client= clientMapper.mapToClient(request);
        client.setCreatedDate(Instant.now());

        clientRepository.save(client);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("{code}")
                .buildAndExpand("api/get/"+client.getCode())
                .toUri();

        log.info("new client added successfully---------");
        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "client", clientMapper.mapToClientResponse(client)
                ),
                "new client added....!"
        );
    }

    @Override
    public Response get(String email) {
        Optional<Client> clientOptional= clientRepository.findByEmail(email);
        if(clientOptional.isEmpty()){
            log.error("the client with the email {} doesn't exist on database...Please try again....", email);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the client with the email: "+email+" doesn't exist on database...Please try again...."
            );
        }
        Client client= clientOptional.get();
        log.info("client with the email: {} getted successfully!", email);
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "client", clientMapper.mapToClientResponse(client)
                ),
                "client with the email: "+email+" getted successfully!"
        );
    }

    @Override
    public Response all() {
        log.info("all client getted successfully!");
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "clients", clientRepository.findAll().stream()
                                .map(clientMapper::mapToClientResponse)
                                .toList()
                ),
                "all client getted successfully"
        );
    }

    @Override
    public Response delete(String email) {
        if(!clientRepository.existsByEmail(email)){
            log.error("the client with the email {} doesn't exist on database...Please try again....", email);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the client with the email: "+email+" doesn't exist on database...Please try again...."
            );
        }
        clientRepository.deleteByEmail(email);
        log.info("client with the email: {} deleted successfully!", email);
        return generateResponse(
                HttpStatus.OK,
                null,
                null,
                "client with the email: "+email+" deleted successfully!"
        );
    }

    private Response generateResponse(HttpStatus status, URI location, Map<?, ?> data, String message) {
        return Response.builder()
                .timeStamp(LocalDateTime.now())
                .status(status)
                .statusCode(status.value())
                .data(data)
                .location(location)
                .message(message)
                .build();
    }
}
