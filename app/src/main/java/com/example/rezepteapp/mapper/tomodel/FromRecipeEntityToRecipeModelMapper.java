package com.example.rezepteapp.mapper.tomodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.IngredientsRecipesDAO;
import com.example.rezepteapp.daos.IngredientsRecipesDAOImpl;
import com.example.rezepteapp.daos.LabelDAO;
import com.example.rezepteapp.daos.LabelDAOImpl;
import com.example.rezepteapp.daos.LabelsRecipesDAO;
import com.example.rezepteapp.daos.LabelsRecipesDAOImpl;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.IngredientsRecipesEntity;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.RecipeUnit;
import com.example.rezepteapp.model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FromRecipeEntityToRecipeModelMapper {

    private Context context;

    public FromRecipeEntityToRecipeModelMapper(Context context) {
        this.context = context;
    }

    public Recipe map(RecipeEntity entity) {
        Recipe recipe = new Recipe();
        recipe.setId(entity.getId());
        recipe.setTitle(entity.getName());
        recipe.setLabels(getLabelsOfEntity(entity.getId()));
        recipe.setvTime(entity.getvTime());
        recipe.setkTime(entity.getkTime());
        recipe.setServings(entity.getServings());
        recipe.setIngridients(getIngredientsOfEntity(entity.getId()));
        recipe.setSteps(getListFromStringWithDemiliter(entity.getSteps()));
        recipe.setNotes(getListFromStringWithDemiliter(entity.getNotes()));
        if (entity.getStatusId() != -1) {
            recipe.setStatus(Status.getStatusById(entity.getStatusId()));
        }
        return recipe;
    }

    private List<Ingredient> getIngredientsOfEntity(int entityId) {
        List<Ingredient> ingredients = new ArrayList<>();

        IngredientsRecipesDAO ingredientsRecipesDAO = new IngredientsRecipesDAOImpl(context);
        List<IngredientsRecipesEntity> ingredientsRecipesEntities = ingredientsRecipesDAO.getIngredientsRecipesByRecipesId(entityId);
        if (ingredientsRecipesEntities.isEmpty()) {
            return ingredients;
        }
        for (IngredientsRecipesEntity ingredientsRecipesEntity : ingredientsRecipesEntities) {
            IngredientDAO ingredientDAO = new IngredientDAOImpl(context);
            String ingredientName = ingredientDAO.getIngredientById(ingredientsRecipesEntity.getIngredientId()).getName();
            if (ingredientsRecipesEntity.getAmount() != null) {
                List<String> amountAndUnit = splitAmount(ingredientsRecipesEntity.getAmount());
                ingredients.add(new Ingredient(amountAndUnit.get(0), RecipeUnit.valueOf(amountAndUnit.get(1)), ingredientName));
            }
        }
        return ingredients;
    }

    private List<Label> getLabelsOfEntity(int entityId) {
        FromLabelEntityToLabelModelMapper labelModelMapper = new FromLabelEntityToLabelModelMapper();
        LabelsRecipesDAO labelsRecipesDAO = new LabelsRecipesDAOImpl(context);
        LabelDAO labelDAO = new LabelDAOImpl(context);

        List<Integer> labelIds = labelsRecipesDAO.getRecipesLabelsByRecipeId(entityId).stream()
                .map(LabelsRecipesEntity::getLabelId)
                .collect(Collectors.toList());
        return labelIds.stream().map(id ->
                labelModelMapper.map(labelDAO.getLabelById(id))).collect(Collectors.toList());
    }

    private Bitmap getByteArrayAsBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private List<String> getListFromStringWithDemiliter(String string) {
        return Arrays.asList(string.split("; "));
    }

    private List<String> splitAmount(String string) {
        return Arrays.asList(string.split(" "));
    }
}
