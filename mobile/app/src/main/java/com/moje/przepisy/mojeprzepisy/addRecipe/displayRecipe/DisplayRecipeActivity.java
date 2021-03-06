package com.moje.przepisy.mojeprzepisy.addRecipe.displayRecipe;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients.AddIngredientsActivity;
import com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo.AddRecipeActivity;
import com.moje.przepisy.mojeprzepisy.addRecipe.addSteps.AddStepsActivity;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

import java.util.List;

public class DisplayRecipeActivity extends AppCompatActivity implements
    DisplayRecipeContract.View{
  public static final int PLEASE_WAIT_DIALOG = 1;
  @BindView(R.id.saveRecipeImageView)ImageView saveRecipeImageView;
  @BindView(R.id.informationTextView)TextView informationTextView;
  @BindView(R.id.recipeEditImageView) ImageView recipeEditImageView;
  @BindView(R.id.ingredientsEditImageView) ImageView ingredientsEditImageView;
  @BindView(R.id.stepsEditImageView) ImageView stepsEditImageView;
  @BindView(R.id.addMainRecipeInfoRecyclerView) RecyclerView addMainRecipeInfoRecyclerView;
  @BindView(R.id.addIngredientsRecyclerView) RecyclerView addIngredientsRecyclerView;
  @BindView(R.id.addStepsRecyclerView) RecyclerView addStepsRecyclerView;
  @BindView(R.id.toolbar_whole_recipe) Toolbar toolbar;
  private DisplayRecipeContract.Presenter presenter;
  ProgressDialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_all_recipe_elements_view);
    ButterKnife.bind(this);

    presenter = new DisplayRecipePresenter(this, new RecipeRepository(this));

    presenter.setFirstScreen();
  }

  @Override
  public Dialog onCreateDialog(int dialogId) {
    switch (dialogId) {
      case PLEASE_WAIT_DIALOG:
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("Zapisywanie");
        dialog.setMessage("Proszę czekać....");
        dialog.setCancelable(false);
        return dialog;

      default:
        break;
    }
    return null;
  }

  @Override
  public void setProgressInDialog(int progress){
    dialog.setProgress(progress);
  }


  @Override
  public void setOnClickListeners() {
    saveRecipeImageView.setOnClickListener(view ->
            presenter.startBackgroundActions(this));
    recipeEditImageView.setOnClickListener(view ->
            presenter.setEditRecipeIconAction());
    ingredientsEditImageView.setOnClickListener(view ->
            presenter.setEditIngredientsIconAction());
    stepsEditImageView.setOnClickListener(view ->
            presenter.setEditStepsIconAction());
  }

  @Override
  public void setToolbar() {
    toolbar.setSubtitle("Krok 4: Sprawdź wprowdzone informacje");
    setSupportActionBar(toolbar);
  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public void setRecipeRecyclerView(List<Recipe> recipeList) {
    DisplayMainInfoAdapter adapter = new DisplayMainInfoAdapter(this, recipeList);
    addMainRecipeInfoRecyclerView.setAdapter(adapter);
    addMainRecipeInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setIngredientsRecyclerView(List<Ingredient> ingredientList) {
    DisplayIngredientsAdapter adapter = new DisplayIngredientsAdapter(this, ingredientList);
    addIngredientsRecyclerView.setAdapter(adapter);
    addIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setStepsRecyclerView(List<Step> stepList) {
    DisplayStepsAdapter adapter = new DisplayStepsAdapter(this, stepList);
    addStepsRecyclerView.setAdapter(adapter);
    addStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setInformationTextView(String message) {
    informationTextView.setText(message);
  }

  @Override
  public void navigateToMainCardsScreen() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
    DisplayRecipeActivity.this.finish();
  }

  @Override
  public void navigateToEditRecipeInformation() {
    Intent intent = new Intent(this, AddRecipeActivity.class);
    startActivity(intent);
    DisplayRecipeActivity.this.finish();
  }

  @Override
  public void navigateToEditIngredients() {
    Intent intent = new Intent(this, AddIngredientsActivity.class);
    startActivity(intent);
    DisplayRecipeActivity.this.finish();
  }

  @Override
  public void navigateToEditSteps() {
    Intent intent = new Intent(this, AddStepsActivity.class);
    startActivity(intent);
    DisplayRecipeActivity.this.finish();
  }
}
