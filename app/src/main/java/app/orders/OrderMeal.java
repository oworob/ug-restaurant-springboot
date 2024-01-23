package app.orders;

import java.util.List;
import app.data.Meal;
import app.data.Extra;

public class OrderMeal {

    private int id;
    private Meal meal;
    private List<Extra> extras;

    public OrderMeal(int id, Meal meal, List<Extra> extras) {
        this.id = id;
        this.meal = meal;
        this.extras = extras;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    public boolean isExtraAdded(String extraId) {
        for (Extra extra : extras) {
            if (extra.getId().equals(extraId)) {
                return true;
            }
        }
        return false;
    }
}
