package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.RecipeDAO;
import com.example.rezepteapp.daos.RecipeDAOImpl;
import com.example.rezepteapp.entities.IngredientsRecipesEntity;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FromIngredientsRecipeRecipeUnitModelToIngredientsRecipesEntityMapper {
    private Context context;

    public FromIngredientsRecipeRecipeUnitModelToIngredientsRecipesEntityMapper(Context context) {
        this.context = context;
    }

    public List<IngredientsRecipesEntity> map(Recipe recipe) {
        List<IngredientsRecipesEntity> entity = new ArrayList<>();
        int recipeId = getRecipeId(recipe.getTitle());
        for (Ingredient ingredient : recipe.getIngridients()) {
            entity.add(
                    new IngredientsRecipesEntity(ingredient.getAmount(), getIngredientId(ingredient.getName()), recipeId)
            );
        }

        return entity;
    }

    private int getRecipeId(String name) {
        RecipeDAO dao = new RecipeDAOImpl(context);
        return dao.getId(name);
    }

    private int getIngredientId(String name) {
        IngredientDAO dao = new IngredientDAOImpl(context);
        return dao.getId(name);
    }
}
