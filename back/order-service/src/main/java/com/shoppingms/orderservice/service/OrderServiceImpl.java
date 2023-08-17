package com.shoppingms.orderservice.service;

import com.shoppingms.orderservice.dto.OrderMapper;
import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.model.Order;
import com.shoppingms.orderservice.model.OrderLineItem;
import com.shoppingms.orderservice.repository.OrderLineItemRepository;
import com.shoppingms.orderservice.repository.OrderRepository;
import com.shoppingms.orderservice.utils.Response;
import com.shoppingms.orderservice.validator.OrderValidator;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final OrderMapper orderMapper;
    @Override
    public Response add(OrderRequest request) {
        List<String> errors= OrderValidator.validate(request);
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
        if(orderRepository.existsByCodeOrder(request.getCodeOrder())){
            log.error("product already exist on the database!!!!");
            return generateResponse(
                    HttpStatus.CONFLICT,
                    null,
                    null,
                    "Product already exist...Please try again!"
            );
        }

        Order order= orderMapper.mapToOrder(request);

        final List<OrderLineItem> orderLineItems = orderLineItemRepository.findByCodeIn(request.getLineItemCode());

        final BigDecimal totalPrice = orderLineItems.stream()
                .map(OrderLineItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderLineItems(orderLineItems);
        order.setDate(Instant.now());
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{code}")
                .buildAndExpand("api/order/get/"+order.getCodeOrder())
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "order", orderMapper.mapToOrderResponse(order)
                ),
                "new order enregistry successfully!"
        );
    }

    @Override
    public Response get(String code) {
        Optional<Order> order= orderRepository.findByCodeOrder(code);
        if(order.isEmpty()){
            log.error("order doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "order doesn't exist on the database"
            );
        }

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "order", orderMapper.mapToOrderResponse(order.get())
                ),
                "order with the code: "+code+" getting successfully!"
        );
    }

    @Override
    public Response all() {

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "orders", orderRepository.findAll().stream()
                                .map(orderMapper::mapToOrderResponse)
                                .toList()
                ),
                "all orders getting successfully!"
        );
    }

    @Override
    public Response delete(String code) {

        Optional<Order> order= orderRepository.findByCodeOrder(code);
        if(order.isEmpty()){
            log.error("order doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "order doesn't exist on the database"
            );
        }
        orderRepository.deleteByCodeOrder(code);
        return generateResponse(
                HttpStatus.OK,
                null,
                null,
                "order with the code: "+code+" deleted successfully!"
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
