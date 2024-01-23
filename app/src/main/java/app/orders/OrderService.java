package app.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository rep;

    @Autowired
    public OrderService(OrderRepository rep) {
        this.rep = rep;
        System.out.println("Connected to database");
    }

    public String CreateOrder(Order order) {
        return rep.save(order).getId();
    }

    public Order FindOrder(String id) {
        Optional<Order> order = rep.findById(id);
        return order.orElse(null);
    }

    public void UpdateOrder(Order order) {
        rep.save(order);
    }
    
    public void DeleteOrder(String orderId) {
        rep.deleteById(orderId);
    }

    public int GetOrderNumber(Order order) {
        List<Order> pendingOrders = rep.findByStatus(Order.OrderStatus.PENDING);
        int orderNumber = 0;

        for (int i = 0; i < pendingOrders.size(); i++) {
            if (order.getId().equals(pendingOrders.get(i).getId())) {
                orderNumber = i + 1;
                break;
            }
        }
        return orderNumber;
    }

    public List<Order> GetAllOrders() {
        return rep.findAll();
    }
}
