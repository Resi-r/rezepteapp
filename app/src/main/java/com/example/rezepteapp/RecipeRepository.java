package com.example.rezepteapp;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.IngredientsRecipesDAO;
import com.example.rezepteapp.daos.IngredientsRecipesDAOImpl;
import com.example.rezepteapp.daos.LabelDAO;
import com.example.rezepteapp.daos.LabelDAOImpl;
import com.example.rezepteapp.daos.LabelsRecipesDAO;
import com.example.rezepteapp.daos.LabelsRecipesDAOImpl;
import com.example.rezepteapp.daos.RecipeDAO;
import com.example.rezepteapp.daos.RecipeDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAO;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.IngredientsRecipesEntity;
import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.mapper.toentitiy.FromIngredientModelToIngredientEntityMapper;
import com.example.rezepteapp.mapper.toentitiy.FromLabelModelToLabelEntityMapper;
import com.example.rezepteapp.mapper.toentitiy.FromRecipeModelToIngredientsRecipesEntityMapper;
import com.example.rezepteapp.mapper.toentitiy.FromRecipeModelToRecipeEntityMapper;
import com.example.rezepteapp.mapper.toentitiy.FromShoppinglistEntryModelToShoppinglistEntryEntityMapper;
import com.example.rezepteapp.mapper.tomodel.FromRecipeEntityToRecipeModelMapper;
import com.example.rezepteapp.mapper.tomodel.FromShoppinglistEntryEntityToShoppinglistEntryMapper;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.ShoppinglistEntry;
import com.example.rezepteapp.model.ShoppinglistEtryStatus;
import com.example.rezepteapp.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeRepository {

    private final Context context;
    private final IngredientDAO ingredientDAO;
    private final IngredientsRecipesDAO ingredientsRecipesDAO;
    private final LabelDAO labelDAO;
    private final LabelsRecipesDAO labelsRecipesDAO;
    private final RecipeDAO recipeDAO;
    private final ShoppinglistEntryDAO shoppinglistEntryDAO;
    private final FromRecipeEntityToRecipeModelMapper fromRecipeEntityToRecipeModelMapper;
    private final FromShoppinglistEntryEntityToShoppinglistEntryMapper fromShoppinglistEntryEntityToShoppinglistEntryMapper;
    private final FromRecipeModelToRecipeEntityMapper fromRecipeModelToRecipeEntityMapper;
    private final FromLabelModelToLabelEntityMapper fromLabelModelToLabelEntityMapper;
    private final FromIngredientModelToIngredientEntityMapper fromIngredientModelToIngredientEntityMapper;
    private final FromRecipeModelToIngredientsRecipesEntityMapper fromRecipeModelToIngredientsRecipesEntityMapper;


    public RecipeRepository(Context context) {
        this.context = context;
        this.ingredientDAO = new IngredientDAOImpl(context);
        this.ingredientsRecipesDAO = new IngredientsRecipesDAOImpl(context);
        this.labelDAO = new LabelDAOImpl(context);
        this.labelsRecipesDAO = new LabelsRecipesDAOImpl(context);
        this.recipeDAO = new RecipeDAOImpl(context);
        this.shoppinglistEntryDAO = new ShoppinglistEntryDAOImpl(context);

        this.fromShoppinglistEntryEntityToShoppinglistEntryMapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        this.fromRecipeEntityToRecipeModelMapper = new FromRecipeEntityToRecipeModelMapper(context);
        this.fromRecipeModelToRecipeEntityMapper = new FromRecipeModelToRecipeEntityMapper(context);
        this.fromLabelModelToLabelEntityMapper = new FromLabelModelToLabelEntityMapper(context);
        this.fromIngredientModelToIngredientEntityMapper = new FromIngredientModelToIngredientEntityMapper(context);
        this.fromRecipeModelToIngredientsRecipesEntityMapper = new FromRecipeModelToIngredientsRecipesEntityMapper(context);
    }

    // Recipes

    public void addRecipe(Recipe recipe) {
        List<Integer> labelIds = new ArrayList<>();
        if (recipe.getLabels() != null) {
            labelIds = addLabels(recipe.getLabels());
            System.out.println("Added Labels");
        }

        List<Integer> ingredientIds = addIngredients(recipe.getIngridients());
        System.out.println("Added Ingredients");

        long recipeId = recipeDAO.saveOrUpdate(fromRecipeModelToRecipeEntityMapper.map(recipe));
        System.out.println("Added Recipe 1");

        if (!labelIds.isEmpty()) {
            addLabelsRecipes((int) recipeId, labelIds);
        }
        addIngredientsRecipes(recipe, (int) recipeId);
        System.out.println("Added other things");

    }

    public List<Integer> addLabels(List<Label> labels) {
        List<Integer> labelIds = new ArrayList<>();
        List<LabelEntity> labelEntities = labels.stream()
                .map(label -> fromLabelModelToLabelEntityMapper.map(label))
                .collect(Collectors.toList());
        for (LabelEntity labelEntity : labelEntities) {
            labelIds.add((int) labelDAO.saveOrUpdate(labelEntity));
        }
        return labelIds;
    }

    public List<Integer> addIngredients(List<Ingredient> ingredients) {
        List<Integer> ingredintIds = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            ingredientDAO.saveOrUpdate(fromIngredientModelToIngredientEntityMapper.map(ingredient));
        }


        List<IngredientEntity> ingredientEntities = ingredients.stream()
                .map(ingredient -> fromIngredientModelToIngredientEntityMapper.map(ingredient))
                .collect(Collectors.toList());
        for (IngredientEntity ingredientEntity : ingredientEntities) {
            ingredintIds.add((int) ingredientDAO.saveOrUpdate(ingredientEntity));
        }
        return ingredintIds;
    }

    public void addLabelsRecipes(int recipeId, List<Integer> labelIds) {
        labelIds.forEach(labelId -> labelsRecipesDAO.saveOrUpdate(new LabelsRecipesEntity(recipeId, labelId)));
    }

    public void addIngredientsRecipes(Recipe recipe, int recipeId) {
        for (Ingredient ingredient : recipe.getIngridients()) {
            int ingredientId = ingredientDAO.getIngredientByName(ingredient.getName()).getId();
            ingredientsRecipesDAO.saveOrUpdate(new IngredientsRecipesEntity(ingredient.getAmount(), ingredientId, recipeId));
        }
    }

    public List<Recipe> getAllRecipes() {
        List<RecipeEntity> recipeEntities = recipeDAO.getAllRecipes();
        return recipeEntities.stream().map(entity -> fromRecipeEntityToRecipeModelMapper.map(entity)).collect(Collectors.toList());
    }

    public List<Recipe> getDailyRecipes() {
        List<RecipeEntity> dailyRecipesEntities = recipeDAO.getRecipesByStatusId(Status.LIVE.getId());
        return dailyRecipesEntities.stream().map(entity -> fromRecipeEntityToRecipeModelMapper.map(entity)).collect(Collectors.toList());
    }

    public List<Recipe> getArchivedRecipes() {
        List<RecipeEntity> archivedRecipesEntities = recipeDAO.getRecipesByStatusId(Status.IN_ACHIVE.getId());
        return archivedRecipesEntities.stream().map(entity -> fromRecipeEntityToRecipeModelMapper.map(entity)).collect(Collectors.toList());
    }

    public List<Recipe> getFilteredRecepieList(List<Label> labels) {
        List<Integer> labelIds = labels.stream().map(label -> labelDAO.getLabelByName(label.getName()).getId()).collect(Collectors.toList());

        List<RecipeEntity> recipeEntities = new ArrayList<>();

        for (int labelId : labelIds) {
            List<Integer> recipeIds = labelsRecipesDAO.getLabelsRecipesByLabelId(labelId).stream().map(LabelsRecipesEntity::getRecipeId).collect(Collectors.toList());
            List<RecipeEntity> recipes = recipeIds.stream().map(recipeId -> recipeDAO.getRecipeById(recipeId)).collect(Collectors.toList());
            recipeEntities.addAll(recipes);
        }
        return recipeEntities.stream().map(entity -> fromRecipeEntityToRecipeModelMapper.map(entity)).collect(Collectors.toList());
    }

    public List<Recipe> getFilteredRecepieList(String searchString) {
        List<RecipeEntity> recipeEntities = recipeDAO.getRecipesByName(searchString);
        return recipeEntities.stream().map(recipeEntity -> fromRecipeEntityToRecipeModelMapper.map(recipeEntity)).collect(Collectors.toList());
    }

    // Shoppinglist
    public List<ShoppinglistEntry> getAllShoppinglistEntries() {
        List<ShoppinglistEntryEntity> shoppinglistEntryEntities = shoppinglistEntryDAO.getAllShoppinglistEntries();
        List<Integer> ingredientIds =  shoppinglistEntryEntities.stream().map(ShoppinglistEntryEntity::getIngredientId).collect(Collectors.toList());
        List<IngredientEntity> ingredientEntities = new ArrayList<>();
        for (int ingredientId : ingredientIds) {
            ingredientEntities.add(ingredientDAO.getIngredientById(ingredientId));
        }
        return fromShoppinglistEntryEntityToShoppinglistEntryMapper.mapToList(shoppinglistEntryEntities, ingredientEntities);
    }

    public void addToShoppinglist(ShoppinglistEntry shoppinglistEntry) {
        long ingredientId = ingredientDAO.saveOrUpdate(new IngredientEntity(shoppinglistEntry.getIngredient()));
        shoppinglistEntryDAO.saveOrUpdate(fromShoppinglistEntryEntityToShoppinglistEntryMapper.mapToEntity(shoppinglistEntry, (int) ingredientId));
    }

    public List<ShoppinglistEntry> getToDoList() {
        List<ShoppinglistEntry> entries = new ArrayList<>();
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        List<ShoppinglistEntryEntity> entities = shoppinglistEntryDAO.getAllTodoEntries();
        List<IngredientEntity> ingredientEntities = ingredientDAO.getAllIngredients();
        for (ShoppinglistEntryEntity entity : entities) {
            entries.add(mapper.mapToEntry(entity, ingredientEntities));
        }
        return entries;
    }
    public List<ShoppinglistEntry> getDoneList() {
        List<ShoppinglistEntry> entries = new ArrayList<>();
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        List<ShoppinglistEntryEntity> entities = shoppinglistEntryDAO.getAllDoneEntries();
        List<IngredientEntity> ingredientEntities = ingredientDAO.getAllIngredients();
        for (ShoppinglistEntryEntity entity : entities) {
            entries.add(mapper.mapToEntry(entity, ingredientEntities));
        }
        return entries;
    }

    public ShoppinglistEntry addNewShoppinglistEntry(String ingredient, String quantity, String unit) {
        ShoppinglistEntryEntity entity = new ShoppinglistEntryEntity();
        String amount = String.join(" ", quantity, unit);
        entity.setAmount(amount);

        int ingredientId = ingredientDAO.getId(ingredient);
        if (ingredientId == 0) {
            ingredientDAO.insert(new IngredientEntity(ingredient));
            ingredientId = ingredientDAO.getId(ingredient);
        }
        entity.setIngredientId(ingredientId);
        entity.setStatus(ShoppinglistEtryStatus.TODO);

        shoppinglistEntryDAO.insert(entity);

        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        return mapper.mapToEntry(
                shoppinglistEntryDAO.getShoppinglistEntryById(shoppinglistEntryDAO.getId(amount, ingredientId)),
                new IngredientEntity(ingredientId, ingredient));
    }

    public void updateTodoList(ShoppinglistEntry entry) {
        FromShoppinglistEntryModelToShoppinglistEntryEntityMapper mapper = new FromShoppinglistEntryModelToShoppinglistEntryEntityMapper(context);
        ShoppinglistEntryEntity entity = mapper.map(entry);
        entity.setStatus(ShoppinglistEtryStatus.TODO);
        shoppinglistEntryDAO.saveOrUpdate(entity);
    }

    public void updateDoneList(ShoppinglistEntry entry) {
        FromShoppinglistEntryModelToShoppinglistEntryEntityMapper mapper = new FromShoppinglistEntryModelToShoppinglistEntryEntityMapper(context);
        ShoppinglistEntryEntity entity = mapper.map(entry);
        entity.setStatus(ShoppinglistEtryStatus.DONE);
        shoppinglistEntryDAO.saveOrUpdate(entity);
    }

    public void deleteRecipe(Recipe recipe) {
        FromRecipeModelToRecipeEntityMapper mapper = new FromRecipeModelToRecipeEntityMapper(context);
        RecipeEntity entity = mapper.map(recipe);
        recipeDAO.delete(entity);
    }

    public void revertArchivation(Recipe recipe) {
        FromRecipeModelToRecipeEntityMapper mapper = new FromRecipeModelToRecipeEntityMapper(context);
        RecipeEntity entity = mapper.map(recipe);
        entity.setStatusId(2);
        recipeDAO.saveOrUpdate(entity);
    }
    public void deleteShoppinglistEntry(ShoppinglistEntry entry) {
        FromShoppinglistEntryModelToShoppinglistEntryEntityMapper mapper = new FromShoppinglistEntryModelToShoppinglistEntryEntityMapper(context);
        ShoppinglistEntryEntity entity = mapper.map(entry);
        shoppinglistEntryDAO.delete(entity);
    }


}
