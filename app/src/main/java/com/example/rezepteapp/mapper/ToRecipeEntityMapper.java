package com.example.rezepteapp.mapper;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.rezepteapp.daos.StatusDAO;
import com.example.rezepteapp.daos.StatusDAOImpl;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.Status;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ToRecipeEntityMapper {

    private final Context context;

    public ToRecipeEntityMapper(Context context) {
        this.context = context;
    }

    public RecipeEntity map(Recipe recipe) {
        RecipeEntity entity = new RecipeEntity();
        entity.setName(recipe.getTitle());
        entity.setImage(getBitmapAsByteArray(recipe.getImageTitle()));
        entity.setkTime(recipe.getkTime());
        entity.setvTime(recipe.getvTime());
        entity.setNotes(mapListToString(recipe.getNotes()));
        entity.setSteps(mapListToString(recipe.getSteps()));
        entity.setStatusId(getStatusId(recipe.getStatus()));
        return entity;
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    private String mapListToString(List<String> stringList) {
        return String.join("; ", stringList);
    }

    private int getStatusId(Status status) {
        StatusDAO statusDAO = new StatusDAOImpl(context);
        return statusDAO.getStatusByName(status.name()).getId();
    }
}