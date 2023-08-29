package com.shoppingms.orderservice.service;

import com.shoppingms.orderservice.dto.OrderLineMapper;
import com.shoppingms.orderservice.dto.OrderLineRequest;
import com.shoppingms.orderservice.dto.Product;
import com.shoppingms.orderservice.model.OrderLine;
import com.shoppingms.orderservice.repository.OrderLineRepository;
import com.shoppingms.orderservice.utils.Response;
import com.shoppingms.orderservice.validator.OrderLineValidator;
import com.shoppingms.orderservice.webClient.WebClientInventory;
import com.shoppingms.orderservice.webClient.WebClientProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderLineServiceImpl implements OrderLineService{
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;
    private final WebClientProduct webClientProduct;
    private final WebClientInventory webClientInventory;

    @Override
    public Response add(OrderLineRequest request) {
        List<String> errors= OrderLineValidator.validate(request);
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

        if(orderLineRepository.existsByCode(request.getCode())){
            log.error("order line already exist on the database!!!");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "order line already exist on the database!!!"
            );
        }
        if(!webClientInventory.isInStock(request.getCodeProduct())){
            log.error("the product {} is empty on the stock", request.getCodeProduct());
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the product :"+request.getCodeProduct()+" is empty on stock...."
            );
        }

        if(webClientInventory.getQuantity(request.getCodeProduct()).compareTo(request.getQuantity())< 0){
            log.error("the product {} is not enough on the stock", request.getCodeProduct());
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the product :"+request.getCodeProduct()+" is not enough on stock...."
            );
        }

        Boolean isSubtract= webClientInventory.subtract(request.getCodeProduct(), request.getQuantity());

        OrderLine orderLine= orderLineMapper.mapToOrderLine(request);
        Product product= webClientProduct.getProduct(request.getCodeProduct());

        orderLine.setProduct(product);
        orderLine.setPrice(product.getPrice().multiply(request.getQuantity()));

        orderLineRepository.save(orderLine);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{code}")
                .buildAndExpand("api/order-line/"+request.getCode())
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "orderLine", orderLineMapper.mapToOrderLineResponse(orderLine),
                        "isSubtract", isSubtract
                ),
                "order line created successfully!!!"
        );
    }

    @Override
    public Response get(String code) {
        final OrderLine orderLine = orderLineRepository.findByCode(code).orElse(null);
        if(Objects.isNull(orderLine)){
            log.error("the order line {} doesn't exist on the database", code);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the order line :"+code+" is not enough on stock...."
            );
        }
        Product product= webClientProduct.getProduct(orderLine.getCodeProduct());
        orderLine.setProduct(product);

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "orderLine", orderLineMapper.mapToOrderLineResponse(orderLine)
                ),
                "order line getting successfully!!!"
        );
    }

    @Override
    public Response all() {

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "orderLines", orderLineRepository.findAll().stream()
                                .peek(orderLine -> {
                                    Product product= webClientProduct.getProduct(orderLine.getCodeProduct());
                                    orderLine.setProduct(product);
                                })
                                .map(orderLineMapper::mapToOrderLineResponse)
                                .toList()
                ),
                "all order lines getting successfully!!!"
        );
    }

    @Override
    public Response delete(String code) {
        final OrderLine orderLine = orderLineRepository.findByCode(code).orElse(null);
        if(Objects.isNull(orderLine)){
            log.error("the order line {} doesn't exist on the database", code);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "the order line :"+code+" is not enough on stock...."
            );
        }

        orderLineRepository.deleteByCode(code);
        return generateResponse(
                HttpStatus.OK,
                null,
                null,
                "order line deleted successfully!!!"
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
