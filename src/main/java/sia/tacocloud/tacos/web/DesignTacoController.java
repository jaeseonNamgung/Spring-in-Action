package sia.tacocloud.tacos.web;

import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sia.tacocloud.tacos.Ingredient;
import sia.tacocloud.tacos.Ingredient.Type;
import sia.tacocloud.tacos.Order;
import sia.tacocloud.tacos.Taco;
import sia.tacocloud.tacos.data.IngredientRepository;
import sia.tacocloud.tacos.data.JdbcIngredientRepository;
import sia.tacocloud.tacos.data.JdbcTacoRepository;
import sia.tacocloud.tacos.data.TacoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SessionAttributes("order")
@RequiredArgsConstructor
@Controller
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepository;

    @ModelAttribute(name="order")
    public Order order(){
        return new Order();
    }
    @ModelAttribute(name="taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping("/design")
    public String showDesignForm(Model model){

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);


        Type[] types = Ingredient.Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("taco", new Taco());
        return "design";
    }

    @PostMapping("/design")
    public String processDesign(@Valid Taco design, Errors errors,
                                @ModelAttribute Order order){
        if(errors.hasErrors()){
            return "design";
        }

        tacoRepository.save(design);
        order.addDesign(design);

        return "redirect:/orders/current";
    }
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x->x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
