package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.model.Recipe;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class FromRecipeModelToRecipeEntityMapper {

    private final Context context;

    public FromRecipeModelToRecipeEntityMapper(Context context) {
        this.context = context;
    }

    public RecipeEntity map(Recipe recipe) {
        RecipeEntity entity = new RecipeEntity();
        if (recipe.getId() != 0) {
            entity.setId(recipe.getId());
        }
        entity.setName(recipe.getTitle());
        if (recipe.getImageTitle() != null) {
            entity.setImage(getBitmapAsByteArray(recipe.getImageTitle()));
        }
        entity.setkTime(recipe.getkTime());
        entity.setvTime(recipe.getvTime());
        entity.setNotes(mapListToString(recipe.getNotes()));
        entity.setSteps(mapListToString(recipe.getSteps()));
        entity.setStatusId(recipe.getStatus().getId());
        return entity;
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    private String mapListToString(List<String> stringList) {
        if (stringList == null) {
            return "";
        }
        // Entferne null Einträge aus der Liste
        stringList.removeIf(java.util.Objects::isNull);
        return String.join("; ", stringList);
    }

}
