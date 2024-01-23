package app.orders;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;
import app.data.Extra;

import java.util.Set;
import java.util.HashSet;

import java.time.LocalDateTime;

@Document(collection = "orders")
public class Order {
    
    public enum OrderStatus {
        IN_PROGRESS,
        PENDING,
        COMPLETED,
        CANCELLED
    }
    
    @Id
    private String id;
    private OrderStatus status = OrderStatus.IN_PROGRESS;
    private List<OrderMeal> meals = new ArrayList<>();
    private String eatingOption  = "EatIn";
    private LocalDateTime orderDate = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<OrderMeal> meals) {
        this.meals = meals;
    }

    public String getEatingOption() {
        return eatingOption;
    }

    public void setEatingOption(String eatingOption) {
        this.eatingOption = eatingOption;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double CalculateTotalPrice() {
        double total = 0.0;

        if (meals != null) {
            for (OrderMeal meal : meals) {
                total += meal.getMeal().getPrice();

                if (meal.getExtras() != null) {
                    for (Extra extra : meal.getExtras()) {
                        total += extra.getPrice();
                    }
                }
            }
        }

        return Math.round(total * 100.0) / 100.0;
    }

    public int newMealId() {
        Set<Integer> usedIds = new HashSet<>();
        for (OrderMeal meal : meals) {
            usedIds.add(meal.getId());
        }
        int newId = 1;
        while (usedIds.contains(newId)) {
            newId++;
        }
        return newId;
    }
}