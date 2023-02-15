package be.vdab.toys2.repositories;

import be.vdab.toys2.domain.Order;
import be.vdab.toys2.domain.Status;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "customer")
    List<Order> findAllByStatusNotInOrderById(List<Status> statusSet);
    @EntityGraph(attributePaths = {"customer", "customer.country", "orderDetails.product"})
    Optional<Order> findById(long id);


    @Lock(LockModeType.OPTIMISTIC)
    @Query("select o from Order o where o.id=:id")
    Optional<Order> findAndLockById(long id);
}
