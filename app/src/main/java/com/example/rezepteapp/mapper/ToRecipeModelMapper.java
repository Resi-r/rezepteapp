package com.example.rezepteapp.mapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.rezepteapp.daos.LabelDAO;
import com.example.rezepteapp.daos.LabelDAOImpl;
import com.example.rezepteapp.daos.LabelsRecipesDAO;
import com.example.rezepteapp.daos.LabelsRecipesDAOImpl;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.Status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ToRecipeModelMapper {

    private Context context;

    public ToRecipeModelMapper(Context context) {
        this.context = context;
    }

    public Recipe map(RecipeEntity entity) {
        Recipe recipe = new Recipe();
        recipe.setTitle(entity.getName());
        recipe.setImageTitle(getByteArrayAsBitmap(entity.getImage()));
        recipe.setLabels(getLabelsOfEntity(entity.getId()));
        recipe.setvTime(entity.getvTime());
        recipe.setkTime(entity.getkTime());
        recipe.setServings(entity.getServings());
        //recipe.setIngridients();
        recipe.setSteps(getListFromStringWithDemiliter(entity.getSteps()));
        recipe.setNotes(getListFromStringWithDemiliter(entity.getNotes()));
        recipe.setStatus(Status.getStatusById(entity.getStatusId()));
        return recipe;
    }

    private List<Label> getLabelsOfEntity(int entityId) {
        ToLabelModelMapper labelModelMapper = new ToLabelModelMapper();
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
}
