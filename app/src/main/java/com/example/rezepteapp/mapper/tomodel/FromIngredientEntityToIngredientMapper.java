package com.example.rezepteapp.mapper.tomodel;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientsRecipesDAO;
import com.example.rezepteapp.daos.IngredientsRecipesDAOImpl;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.RecipeUnit;

public class FromIngredientEntityToIngredientMapper {
    private Context context;

    public FromIngredientEntityToIngredientMapper(Context context) {
        this.context = context;
    }

    public Ingredient map(IngredientEntity entity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(entity.getId());
        String[] amountAndUnit = divideAmountAndUnit(getAmount(entity.getId()));
        ingredient.setAmount(amountAndUnit[0]);
        ingredient.setName(entity.getName());
        ingredient.setUnit(getUnit(amountAndUnit[1]));
        return ingredient;
    }

    private String getAmount(int id) {
        IngredientsRecipesDAO dao = new IngredientsRecipesDAOImpl(context);
        return dao.getAmount(id);
    }

    private RecipeUnit getUnit(String unit) {
        return RecipeUnit.valueOf(unit);
    }

    private String[] divideAmountAndUnit(String input) {
        return input.split(" ");
    }
}
