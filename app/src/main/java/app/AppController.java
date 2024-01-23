package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.data.Meal;
import app.orders.OrderService;
import app.orders.Order;

import org.springframework.ui.Model;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


@Controller
public class AppController {

    private OrderService OrderService;
    private final List<Meal> Meals;

    @Autowired
    public AppController(OrderService OrderService, List<Meal> ImportedMeals) {
        this.OrderService = OrderService;
        this.Meals = ImportedMeals;

        
    }

    public List<Meal> RandomMeals(int number) {
        if (number >= Meals.size()) { // less meals than number
            return Meals;
        }
        List<Meal> copyOfMeals = new ArrayList<>(Meals);
        Collections.shuffle(copyOfMeals);

        List<Meal> selected = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            selected.add(copyOfMeals.get(i));
        }
        return selected;
    }


    // MAPPINGS
    
    @GetMapping("/")
    public String HomePage(Model model) {

        model.addAttribute("RandomMeals", RandomMeals(3));
        return "Home";
    }

    @GetMapping("/neworder")
    public String NewOrderPage(@RequestParam(name = "category", required = false) String category, @RequestParam(name = "meal", required = false) String mealid, Model model) {
        String NewOrderId = OrderService.CreateOrder(new Order());
        return "redirect:/order/" + NewOrderId + "/summary";
    }

}
