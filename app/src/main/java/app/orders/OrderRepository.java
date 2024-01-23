package app.orders;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByStatus(Order.OrderStatus status);
    List<Order> findAll();
}