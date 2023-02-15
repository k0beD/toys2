package be.vdab.toys2.services;

import be.vdab.toys2.domain.Order;
import be.vdab.toys2.domain.OrderDetail;
import be.vdab.toys2.domain.Status;
import be.vdab.toys2.exceptions.OrderNietGevondenException;
import be.vdab.toys2.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllByStatusNotIn(List<Status> statusSet) {
        return orderRepository.findAllByStatusNotInOrderById(statusSet);
    }

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }


    public Order findAndLockById(long id) {
        return orderRepository.findAndLockById(id)
                .orElseThrow(OrderNietGevondenException::new);
    }

    @Transactional
    public void ship(long id) {
        orderRepository.findAndLockById(id)
                .orElseThrow(OrderNietGevondenException::new).ship();
    }

}
