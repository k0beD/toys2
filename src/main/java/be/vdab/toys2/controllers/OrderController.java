package be.vdab.toys2.controllers;

import be.vdab.toys2.domain.Order;
import be.vdab.toys2.domain.OrderDetail;
import be.vdab.toys2.domain.Status;
import be.vdab.toys2.exceptions.OrderDoorAndereGebruikerGewijzigdException;
import be.vdab.toys2.exceptions.OrderNietGevondenException;
import be.vdab.toys2.exceptions.ProductDoorAndereGebruikerGewijzigdException;
import be.vdab.toys2.services.OrderService;
import be.vdab.toys2.services.ProductService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final List<Status> statusList = Arrays.asList(Status.CANCELLED, Status.SHIPPED);

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("unshipped")
    Stream<OrderBeknopt> findAllByStatusNotCancelledOrShipped() {

        return orderService.findAllByStatusNotIn(statusList).stream().map(OrderBeknopt::new);
    }

    @GetMapping("{id}")
    OrderInDetail findById(@PathVariable long id) {
        return orderService.findById(id).map(order -> new OrderInDetail(order))
                .orElseThrow(OrderNietGevondenException::new);
    }

    @PostMapping("{orderNummer}/shippings")
    void ship(@PathVariable long orderNummer) {

        var orderDetails = orderService.findAndLockById(orderNummer).getOrderDetails();
        for (var orderDetail : orderDetails) {
            var aantal = orderDetail.getOrdered();
            var productId = orderDetail.getProduct().getId();

            try {
                productService.ship(productId, aantal);
            } catch (ObjectOptimisticLockingFailureException ex) {
                throw new ProductDoorAndereGebruikerGewijzigdException();
            }

        }
        try {
            orderService.ship(orderNummer);

        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new OrderDoorAndereGebruikerGewijzigdException();
        }
    }


        private record OrderInDetail(long id, LocalDate ordered, LocalDate required
                , String customerName, String countryName, BigDecimal value, Set<OrderDetailsBeknopt> orderDetails) {
            OrderInDetail(Order order) {
                this(order.getId(), order.getOrdered(), order.getRequired(),
                        order.getCustomer().getName(), order.getCustomer().getCountry().getName(),
                        order.getTotalValue(), order.getOrderDetails().stream().map(orderDetail -> new OrderDetailsBeknopt(orderDetail)).collect(Collectors.toSet()));
            }
        }

        private record OrderDetailsBeknopt(int ordered, BigDecimal priceEach, BigDecimal value, String productName) {
        OrderDetailsBeknopt(OrderDetail orderDetail) {
            this(orderDetail.getOrdered(), orderDetail.getPriceEach(), orderDetail.getValue(), orderDetail.getProduct().getName());
        }
        }

        private record OrderBeknopt(long id, LocalDate ordered, LocalDate required
                , String customerName, Status status) {
            OrderBeknopt(Order order) {
                this(order.getId(), order.getOrdered(), order.getRequired()
                        , order.getCustomer().getName(), order.getStatus());
            }
        }
    }
