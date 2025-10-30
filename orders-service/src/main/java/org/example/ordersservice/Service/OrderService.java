package org.example.ordersservice.Service;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.CircutBreaker.Resiliance;
import org.example.ordersservice.Dto.OrderRequestDto;
import org.example.ordersservice.Dto.OrderResponseDto;
import org.example.ordersservice.Dto.OrderDto;
import org.example.ordersservice.Dto.UserDto;
import org.example.ordersservice.Entity.Order;
import org.example.ordersservice.Repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final Resiliance userFeign;

    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
    }

    @Transactional
    public ResponseEntity<?> createOrder(OrderRequestDto orderDto) {
        ResponseEntity<?> userResponse = userFeign.fetchUserByIdOrThrow(orderDto.getUserId());

        if (!userResponse.getStatusCode().is2xxSuccessful()) {
            return userResponse;
        }

        UserDto user = (UserDto) userResponse.getBody();

        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setImeProdukta(orderDto.getImeProdukta());
        order.setQuantity(orderDto.getQuantity());
        order.setVremeKupovine(LocalDateTime.now());

        Order saved = orderRepository.saveAndFlush(order);

        OrderResponseDto responseDto = modelMapper.map(saved, OrderResponseDto.class);
        responseDto.setUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    public ResponseEntity<?> findById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Narudžbina sa ID " + id + " nije pronađena."
                ));

        ResponseEntity<?> userResponse = userFeign.fetchUserByIdOrThrow(order.getUserId());
        UserDto user = null;

        if (userResponse.getStatusCode().is2xxSuccessful()) {
            user = (UserDto) userResponse.getBody();
        }

        OrderResponseDto dto = modelMapper.map(order, OrderResponseDto.class);
        dto.setUser(user);

        return ResponseEntity.ok(dto);
    }


    @Transactional
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Narudžbina nije pronađena.");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public ResponseEntity<?> updateOrderQuantity(Integer id, Integer quantity) {
        if (quantity == null || quantity < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Quantity mora biti bar 1.");
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Narudžbina sa ID " + id + " nije pronađena."
                ));

        order.setQuantity(quantity);
        Order saved = orderRepository.save(order);


        OrderDto responseDto = modelMapper.map(saved, OrderDto.class);

        return ResponseEntity.ok(responseDto);
    }

}
