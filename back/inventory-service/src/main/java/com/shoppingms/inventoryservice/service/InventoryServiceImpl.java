package com.shoppingms.inventoryservice.service;

import com.shoppingms.inventoryservice.dto.InventoryMapper;
import com.shoppingms.inventoryservice.dto.InventoryRequest;
import com.shoppingms.inventoryservice.model.Inventory;
import com.shoppingms.inventoryservice.repository.InventoryRepository;
import com.shoppingms.inventoryservice.utils.Response;
import com.shoppingms.inventoryservice.validator.InventoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
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
public class InventoryServiceImpl implements InventoryService{
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    @Override
    public Response add(InventoryRequest request) {
        List<String> errors= InventoryValidator.validate(request);
        if(!errors.isEmpty()){
            log.error("some field not valid!!! Please try again");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    Map.of(
                            "errors", errors
                    ),
                    "some field not valid...Please try again!"
            );
        }
        if(inventoryRepository.existsByCodeProduct(request.getCodeProduct())){
            Inventory inventory= inventoryRepository.findByCodeProduct(request.getCodeProduct())
                    .orElseThrow(()-> {
                        throw new RuntimeException("error to get the product!!!");
                    });
            BigDecimal quantity= inventory.getQuantity();
            inventory.setQuantity(quantity.add(request.getQuantity()));

            inventoryRepository.save(inventory);
            return generateResponse(
                    HttpStatus.OK,
                    null,
                    Map.of(
                            "inventory", inventoryMapper.mapToInventoryResponse(inventory)
                    ),
                    "Quantity of the product: "+request.getCodeProduct()+" added successfully"
            );
        }
        Inventory inventory= inventoryMapper.mapToInventory(request);

        inventoryRepository.save(inventory);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{code}")
                .buildAndExpand("api/inventory/get/"+inventory.getCodeProduct())
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "order", inventoryMapper.mapToInventoryResponse(inventory)
                ),
                "new order enregistry successfully!"
        );
    }

    @Override
    public Response get(String code) {
        Optional<Inventory> inventory= inventoryRepository.findByCodeProduct(code);
        if(inventory.isEmpty()){
            log.error("inventory doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "inventory doesn't exist on the database"
            );
        }

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "inventory", inventoryMapper.mapToInventoryResponse(inventory.get())
                ),
                "inventory with the code: "+code+" getting successfully!"
        );
    }

    @Override
    public Response subtract(InventoryRequest request) {
        Inventory inventory= inventoryRepository.findByCodeProduct(request.getCodeProduct())
                .orElseThrow(()-> {
                    throw new RuntimeException("error to get the inventory");
                });

        if(inventory.getQuantity().compareTo(request.getQuantity())< 0){
            log.error("the product not enough for subtract!");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    Map.of(
                            "inventory", inventoryMapper.mapToInventoryResponse(inventory)
                    ),
                    "the product not enough for subtract!\""
            );
        }
        BigDecimal quantity= inventory.getQuantity();
        inventory.setQuantity(quantity.subtract(request.getQuantity()));

        inventoryRepository.save(inventory);
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "inventory", inventoryMapper.mapToInventoryResponse(inventory)
                ),
                "product with the code: "+request.getCodeProduct()+" subtract successfully!"
        );
    }

    @Override
    public boolean isInStock(String code) {
        return inventoryRepository.findByCodeProduct(code).isPresent();
    }
    @Override
    public Response all() {
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "inventories", inventoryRepository.findAll().stream()
                                .map(inventoryMapper::mapToInventoryResponse)
                                .toList()
                ),
                "all inventories getting successfully!"
        );
    }

    @Override
    public Response delete(String code) {
        Optional<Inventory> order= inventoryRepository.findByCodeProduct(code);
        if(order.isEmpty()){
            log.error("inventory doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "inventory doesn't exist on the database"
            );
        }
        inventoryRepository.deleteByCodeProduct(code);
        return generateResponse(
                HttpStatus.OK,
                null,
                null,
                "inventory with the code: "+code+" deleted successfully!"
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
