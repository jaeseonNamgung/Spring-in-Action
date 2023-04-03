package sia.tacocloud.tacos.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sia.tacocloud.tacos.Ingredient;
import sia.tacocloud.tacos.data.IngredientRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class IngredientByidConverter implements Converter<String, Ingredient> {

    public final IngredientRepository ingredientRepo;

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
        return optionalIngredient.isPresent()?optionalIngredient.get():null;
    }
}
