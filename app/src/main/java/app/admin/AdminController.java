package app.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.data.Extra;
import app.data.Meal;
import app.orders.OrderService;
import app.orders.Order;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Collections;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.time.LocalDate;



@Controller
public class AdminController {

    private OrderService OrderService;
    private final List<Meal> Meals;
    private final List<Extra> Extras;

    String AdminUsername = "admin";
    String AdminPassword = "admin";
    boolean AdminLogged = false;
    

    @Autowired
    public AdminController(OrderService OrderService, List<Meal> ImportedMeals, List<Extra> ImportedExtras) {
        this.OrderService = OrderService;
        this.Meals = ImportedMeals;
        this.Extras = ImportedExtras;
    }


    public Map<String, List<Meal>> GetMealsByCategory() {
        return Meals.stream().collect(Collectors.groupingBy(Meal::getCategory));
    }

    public Meal GetMealById(String mealid) {
        for (Meal meal : Meals) {
            if (meal.getId().equals(mealid)) {
                return meal;
            }
        }
        return null;
    }

    private Map<LocalDate, Map<String, String>> GetMonthlyStatistics() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Order> orders = OrderService.GetAllOrders();

        List<Order> filteredOrders = orders.stream()
                .filter(order -> order.getOrderDate().getMonth() == currentDate.getMonth() &&
                        order.getOrderDate().getYear() == currentDate.getYear() &&
                        order.getStatus().toString() == "COMPLETED")
                .collect(Collectors.toList());

        Map<LocalDate, List<Order>> groupedOrders = filteredOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getOrderDate().toLocalDate()));

        Map<LocalDate, Map<String, String>> result = new HashMap<>();

        for (Map.Entry<LocalDate, List<Order>> entry : groupedOrders.entrySet()) {
            LocalDate date = entry.getKey();
            List<Order> dailyOrders = entry.getValue();

            int orderCount = dailyOrders.size();
            double totalProfit = dailyOrders.stream().mapToDouble(Order::CalculateTotalPrice).sum();

            Map<String, String> statistics = new HashMap<>();
            statistics.put("count", String.valueOf(orderCount));
            statistics.put("income", String.valueOf(Math.round(totalProfit * 100.0) / 100.0));

            result.put(date, statistics);
        }

        return result;
    }


    // GET MAPPINGS
    
    @GetMapping("/login")
    public String LoginPage(Model model) {
        return "Login";
    }

    @GetMapping("/admin")
    public String AdminPage(Model model) {
        if (AdminLogged) {
            List<Order> orders = OrderService.GetAllOrders();
            Collections.reverse(orders);

           

            double income = 0.0;
            for (Order order : orders) {
                if (order.getStatus().toString() != "CANCELLED") {
                    income += order.CalculateTotalPrice();
                }
                
            }
            income = Math.round(income * 100.0) / 100.0;

            model.addAttribute("Orders", orders);
            model.addAttribute("MonthlyStatistics", GetMonthlyStatistics());
            model.addAttribute("TotalIncome", income);
            model.addAttribute("MealsByCategory", GetMealsByCategory());
            model.addAttribute("Extras", Extras);

            return "Admin";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/managemeal/{mealId}")
    public String ManageMeal(Model model, @PathVariable String mealId) {
        if (AdminLogged) {
            Meal meal = GetMealById(mealId);
            model.addAttribute("Meal", meal);
            model.addAttribute("validator", new ManageMealValidator(mealId, meal.getName(), meal.getPrice(), meal.getCategory()));

            return "AdminManageMeal";
        }
        return "redirect:/login";
    }


    // POST MAPPINGS

    @PostMapping("/login")
    public String Login(Model model, String username, String password) {
        if (username.equals(AdminUsername) && password.equals(AdminPassword)) {
            AdminLogged = true;
            return "redirect:/admin";
        }
        return "Login";
    }

    @PostMapping("/logout")
    public String Logout() {
        AdminLogged = false;
        return "redirect:/login";
    }
    
    @PostMapping("/completeorder")
    public String CompleteOrder(String orderId) {
        Order order = OrderService.FindOrder(orderId);
        if (order == null) {
            return "redirect:/admin";
        }
        order.setStatus(Order.OrderStatus.COMPLETED);
        OrderService.UpdateOrder(order);
        return "redirect:/admin";
    }

    @PostMapping("/cancelorder")
    public String CancelOrder(String orderId) {
        Order order = OrderService.FindOrder(orderId);
        if (order == null) {
            return "redirect:/admin";
        }
        order.setStatus(Order.OrderStatus.CANCELLED);
        OrderService.UpdateOrder(order);
        return "redirect:/admin";
    }

    @PostMapping("/deleteorder")
    public String DeleteOrder(String orderId) {
        OrderService.DeleteOrder(orderId);
        return "redirect:/admin";
    }

    @PostMapping("/adminupdatemeal")
    public String AdminUpdateMeal(@ModelAttribute("validator") @Valid ManageMealValidator validator, Errors errors, Model model) {
        if (errors.hasErrors()) {
            Meal meal = GetMealById(validator.getMealId());
            model.addAttribute("Meal", meal);
            model.addAttribute("validator", validator);
            return "AdminManageMeal";
        }
        Meal meal = GetMealById(validator.getMealId());
        meal.setName(validator.getMealName());
        meal.setPrice(validator.getMealPrice());
        meal.setCategory(validator.getMealCategory());
        return "redirect:/admin";
    }

}
