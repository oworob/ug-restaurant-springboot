package app.admin;

import lombok.Data;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Data
public class ManageMealValidator {

    private String mealId;

    @NotNull(message = "Name is required")
    @Size(min = 2, message = "Name must contain at least two characters")
    private String mealName;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private double mealPrice;

    @NotNull(message = "Category is required")
    @Size(min = 2, message = "Name must contain at least two characters")
    @Pattern(regexp = "[A-Z].*", message = "Category name must start with an uppercase letter")
    private String mealCategory;

    // default values
    public ManageMealValidator(String mealId, String mealName, double mealPrice, String mealCategory) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealCategory = mealCategory;
    }

}
