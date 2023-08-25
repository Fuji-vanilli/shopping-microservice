package com.shoppingms.orderservice.service;

import com.shoppingms.orderservice.dto.OrderMapper;
import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.dto.Product;
import com.shoppingms.orderservice.model.Order;
import com.shoppingms.orderservice.model.OrderLine;
import com.shoppingms.orderservice.repository.OrderLineRepository;
import com.shoppingms.orderservice.repository.OrderRepository;
import com.shoppingms.orderservice.utils.Response;
import com.shoppingms.orderservice.validator.OrderValidator;
import com.shoppingms.orderservice.webClient.WebClientProduct;
import jakarta.servlet.Servlet;
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
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderMapper orderMapper;
    private final WebClientProduct webClientProduct;

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

        if(orderRepository.existsByCode(request.getCode())){
            log.error("order already exist on the database!!!");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "order already exist on the database!!!"
            );
        }

        Order order= orderMapper.mapToOrder(request);
        final List<OrderLine> orderLines = orderLineRepository.findByCodeIn(order.getCodeOrderLines()).stream()
                .peek(orderLine -> {
                    Product product = webClientProduct.getProduct(orderLine.getCodeProduct());
                    orderLine.setProduct(product);
                })
                .toList();

        final BigDecimal totalPrice = orderLines.stream()
                .map(OrderLine::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setDate(Instant.now());
        order.setOrderLines(orderLines);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{code}")
                .buildAndExpand("api/order/get/"+order.getCode())
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "order", orderMapper.mapToOrderResponse(order)
                ),
                "new order created successfully!"
        );
    }

    @Override
    public Response get(String code) {
        final Order order = orderRepository.findByCode(code).orElse(null);
        if(Objects.isNull(order)){
            log.error("the order {} doesn't exist on the database", code);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the order :"+code+" doesn't exist on database...."
            );
        }

        List<OrderLine> orderLines= orderLineRepository.findByCodeIn(order.getCodeOrderLines());
        order.setOrderLines(orderLines);

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "order", orderMapper.mapToOrderResponse(order)
                ),
                "order getting successfully!!!"
        );
    }

    @Override
    public Response all() {
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "orders", orderRepository.findAll().stream()
                                .peek(order -> {
                                    final List<OrderLine> orderLines = orderLineRepository.findByCodeIn(order.getCodeOrderLines())
                                            .stream()
                                            .peek(orderLine -> {
                                                Product product= webClientProduct.getProduct(orderLine.getCodeProduct());
                                                orderLine.setProduct(product);
                                            })
                                            .toList();
                                    order.setOrderLines(orderLines);
                                })
                ),
                "all order lines getting successfully!!!"
        );
    }

    @Override
    public Response delete(String code) {
        final Order order = orderRepository.findByCode(code).orElse(null);
        if(Objects.isNull(order)){
            log.error("the order {} doesn't exist on the database", code);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the order :"+code+" doesn't exist on database...."
            );
        }
        orderRepository.deleteByCode(code);
        return generateResponse(
                HttpStatus.OK,
                null,
                null,
                "order deleted successfully!!!"
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
