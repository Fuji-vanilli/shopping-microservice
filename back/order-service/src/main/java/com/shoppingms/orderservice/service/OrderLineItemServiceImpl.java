package com.shoppingms.orderservice.service;

import com.shoppingms.orderservice.dto.OrderLineItemMapper;
import com.shoppingms.orderservice.dto.OrderLineItemRequest;
import com.shoppingms.orderservice.dto.Product;
import com.shoppingms.orderservice.model.Order;
import com.shoppingms.orderservice.model.OrderLineItem;
import com.shoppingms.orderservice.repository.OrderLineItemRepository;
import com.shoppingms.orderservice.restClient.WebClientInventoryGetter;
import com.shoppingms.orderservice.restClient.WebClientProductGetter;
import com.shoppingms.orderservice.utils.Response;
import com.shoppingms.orderservice.validator.OrderLineItemValidator;
import com.shoppingms.orderservice.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
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
public class OrderLineItemServiceImpl implements OrderLineItemService{
    private final OrderLineItemRepository orderLineItemRepository;
    private final OrderLineItemMapper orderLineItemMapper;
    private final WebClientInventoryGetter webClientInventory;
    private final WebClientProductGetter webClientProduct;

    @Override
    public Response add(OrderLineItemRequest request) throws JSONException {
        List<String> errors= OrderLineItemValidator.validate(request);
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
        if(orderLineItemRepository.existsByCode(request.getCode())){
            log.error("order line item already exist on the database!!!!");
            return generateResponse(
                    HttpStatus.CONFLICT,
                    null,
                    null,
                    "order line item already exist...Please try again!"
            );
        }

        Product product= webClientProduct.getProduct(request.getCodeProduct());
        if(!webClientInventory.isInStock(product.getCode())){
            log.error("sorry the product {} is empty on stock", product.getCode());
            return generateResponse(
                    HttpStatus.CONFLICT,
                    null,
                    null,
                    "sorry the product: "+product.getCode()+" is empty on stock"
            );
        }else if(webClientInventory.quantity(product.getCode()).compareTo(request.getQuantity())< 0){
            log.error("sorry the product {} is not enough on stock", product.getCode());
            return generateResponse(
                    HttpStatus.CONFLICT,
                    null,
                    null,
                    "sorry the product: "+product.getCode()+" is not enough on stock"
            );
        }
        Boolean isSubtract= webClientInventory.subtractQuantity(request.getCodeProduct(), request.getQuantity());

        OrderLineItem orderLineItem= orderLineItemMapper.mapToOrderLineItem(request);
        orderLineItem.setPrice(product.getPrice().multiply(orderLineItem.getQuantity()));

        orderLineItemRepository.save(orderLineItem);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{code}")
                .buildAndExpand("api/order/get/"+orderLineItem.getCode())
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "orderLineItem", orderLineItemMapper.mapToOrderLineItemResponse(orderLineItem),
                        "product subtract", isSubtract
                ),
                "new order line item enregistry successfully!"
        );
    }

    @Override
    public Response get(String code) {
        Optional<OrderLineItem> orderLneItem= orderLineItemRepository.findByCode(code);
        if(orderLneItem.isEmpty()){
            log.error("order line item doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "order line item doesn't exist on the database"
            );
        }

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "orderLineItem", orderLineItemMapper.mapToOrderLineItemResponse(orderLneItem.get())
                ),
                "order line item with the code: "+code+" getting successfully!"
        );
    }

    @Override
    public Response all() {
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "orderLineItems", orderLineItemRepository.findAll().stream()
                                .map(orderLineItemMapper::mapToOrderLineItemResponse)
                                .toList()
                ),
                "all order line items getting successfully!"
        );
    }

    @Override
    public Response delete(String code) {
        Optional<OrderLineItem> order= orderLineItemRepository.findByCode(code);
        if(order.isEmpty()){
            log.error("order line item doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "order line item doesn't exist on the database"
            );
        }
        orderLineItemRepository.deleteByCode(code);
        return generateResponse(
                HttpStatus.OK,
                null,
                null,
                "order line item with the code: "+code+" deleted successfully!"
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
