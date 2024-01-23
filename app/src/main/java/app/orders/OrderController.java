package app.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.data.Extra;
import app.data.Meal;

import org.springframework.ui.Model;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Set;


@Controller
public class OrderController {

    private OrderService OrderService;
    
    private final List<Meal> Meals;
    private final List<Extra> Extras;

    @Autowired
    public OrderController(OrderService OrderService, List<Meal> ImportedMeals, List<Extra> ImportedExtras) {
        this.OrderService = OrderService;
        
        this.Meals = ImportedMeals;
        System.out.println("------------------------------ Imported meals ------------------------------");
        for (Meal m : ImportedMeals) {
            System.out.println(m.toString());
        }

        this.Extras = ImportedExtras;
        System.out.println(" ------------------------------Imported extras ------------------------------");
        for (Extra e: ImportedExtras) {
            System.out.println(e.toString());
        }
        
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

    public Extra GetExtraById(String extraid) {
        for (Extra extra : Extras) {
            if (extra.getId().equals(extraid)) {
                return extra;
            }
        }
        return null;
    }

    public OrderMeal GetOrderMealById(Order order, Integer mealid) {
        for (OrderMeal meal : order.getMeals()) {
            if (meal.getId().equals(mealid)) {
                return meal;
            }
        }
        return null;
    }

    public List<Extra> GetExtrasInMeal(String mealid) {
        List<Extra> ExtrasInMeal = new ArrayList<>();
        Meal meal = GetMealById(mealid);
        if (meal != null) {
            for (Extra extra : meal.getAvailableExtras()) {
                ExtrasInMeal.add(extra);
            }
        }
        return ExtrasInMeal;
    }
    


    // GET MAPPINGS
   
    @GetMapping("/order/{orderId}/addmeal")
    public String OrderAddMeal(@PathVariable String orderId, Model model) {
        Map<String, List<Meal>> MealsByCategory = GetMealsByCategory();
        Set<String> MealCategories = MealsByCategory.keySet();

        Order order = OrderService.FindOrder(orderId);
        if (order == null) { // order not found -> redirect to home
            return "redirect:/";
        }

        model.addAttribute("Order", order);
        model.addAttribute("MealsByCategory", MealsByCategory);
        model.addAttribute("MealCategories", MealCategories);

        return "AddMeal";
    }

    @GetMapping("/order/{orderId}/summary")
    public String OrderPage(@PathVariable String orderId, Model model) {
        Order order = OrderService.FindOrder(orderId);
        if (order == null) {
            return "redirect:/";
        }
        if (order.getStatus() == Order.OrderStatus.COMPLETED) {
            model.addAttribute("Order", order);
        }
        if (order.getStatus().equals(Order.OrderStatus.PENDING)) {
            model.addAttribute("OrderNumber", OrderService.GetOrderNumber(order));
        }
        model.addAttribute("Order", order);
        return "Order";
    }

    @GetMapping("/order/{orderId}/customize/{mealId}")
    public String CustomizeMealPage(@PathVariable String orderId, @PathVariable int mealId, Model model) {
        Order order = OrderService.FindOrder(orderId);
        if (order == null) {
            return "redirect:/";
        }
        OrderMeal meal = GetOrderMealById(order,mealId);

        model.addAttribute("Order", order);
        model.addAttribute("Meal", meal);
        return "CustomizeMeal";
    }


    // POST MAPPINGS
    
    @PostMapping("/addmeal")
    public String AddMeal(String orderId, String mealId) {

        Order order = OrderService.FindOrder(orderId);
            if (order == null) { // order not found -> redirect to home
                return "redirect:/";
            }

        OrderMeal newmeal = new OrderMeal(order.newMealId(),GetMealById(mealId), Collections.<Extra>emptyList());
        List<OrderMeal> orderedmeals = order.getMeals();
        orderedmeals.add(newmeal);
        order.setMeals(orderedmeals);
        OrderService.UpdateOrder(order);

        return "redirect:/order/"+orderId+"/summary";
    }

    @PostMapping("/deletemeal")
    public String DeleteMeal(String orderId, Integer mealId) {
        Order order = OrderService.FindOrder(orderId);
            if (order == null) {
                return "redirect:/";
            }
        
        OrderMeal meal = GetOrderMealById(order, mealId);
        List<OrderMeal> orderedmeals = order.getMeals();
        orderedmeals.remove(meal);
        order.setMeals(orderedmeals);
        OrderService.UpdateOrder(order);

        return "redirect:/order/"+orderId+"/summary";
    }


    @PostMapping("/payfororder")
    public String PayForOrder(String orderId) {
        Order order = OrderService.FindOrder(orderId);
            if (order == null) {
                return "redirect:/";
            }
        order.setStatus(Order.OrderStatus.PENDING);
        OrderService.UpdateOrder(order);
        return "redirect:/order/"+orderId+"/summary";
    }

    @PostMapping("/deleteorderclient")
    public String DeleteOrder(String orderId) {
        OrderService.DeleteOrder(orderId);
        return "redirect:/";
    }


    @PostMapping("/updateeatingoption")
    public String UpdateEatingOption(String orderId, String eatingOption) {
        Order order = OrderService.FindOrder(orderId);
            if (order == null) {
                return "redirect:/";
            }
        order.setEatingOption(eatingOption);
        OrderService.UpdateOrder(order);
        return "redirect:/order/"+orderId+"/summary";
    }


    @PostMapping("/updatemeal")
    public String UpdateMeal(String orderId, Integer mealId, String[] extras) {
        Order order = OrderService.FindOrder(orderId);
            if (order == null) {
                return "redirect:/";
            }

        OrderMeal meal = GetOrderMealById(order, mealId);
        List<Extra> mealextras = new ArrayList<>();
        
        if (extras != null) {
            for (String extraId : extras) {
             mealextras.add(GetExtraById(extraId));
             meal.setExtras(mealextras);
            }
        } else {
            meal.setExtras(new ArrayList<>());
        }
        
        
        OrderService.UpdateOrder(order);
        return "redirect:/order/"+orderId+"/summary";
    }

    

}
