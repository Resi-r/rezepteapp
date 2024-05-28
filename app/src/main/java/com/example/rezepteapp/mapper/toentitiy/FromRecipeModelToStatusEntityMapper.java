package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.StatusDAO;
import com.example.rezepteapp.daos.StatusDAOImpl;
import com.example.rezepteapp.entities.StatusEntity;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.Status;

public class FromRecipeModelToStatusEntityMapper {
    private Context context;

    public FromRecipeModelToStatusEntityMapper(Context context) {
        this.context = context;
    }

    public StatusEntity map(Recipe recipe) {
        StatusEntity entity = new StatusEntity();
        entity.setName(String.valueOf(recipe.getStatus()));
        entity.setId(getStatusId(recipe.getStatus()));
        return null;
    }

    private int getStatusId(Status status) {
        StatusDAO dao = new StatusDAOImpl(context);
        return dao.getId(String.valueOf(status));
    }
}
