package com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo.AddRecipeActivity;
import com.moje.przepisy.mojeprzepisy.addRecipe.addSteps.AddStepsActivity;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public class AddIngredientsActivity extends AppCompatActivity implements
        AddIngredientsContract.View {
  @BindView(R.id.addIngredientFab) FloatingActionButton addIngredientFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  @BindView(R.id.addIngredientsRecyclerView) RecyclerView addIngredientsRecyclerView;
  @BindView(R.id.toolbar_whole_recipe) Toolbar toolbarRecipe;
  private AddIngredientsContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddIngredientsPresenter(this);
    presenter.setFirstScreen();
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void setToolbar() {
    toolbarRecipe.setSubtitle(R.string.add_recipe_title_step_two);
    setSupportActionBar(toolbarRecipe);
  }

  @Override
  public void setRecyclerView(List<Ingredient> ingredientList) {
    AddIngredientsAdapter adapter = new AddIngredientsAdapter(this, ingredientList);
    addIngredientsRecyclerView.setAdapter(adapter);
    addIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setListeners() {
    addIngredientFab.setOnClickListener(v -> presenter.setNextIngredient());
    previousActionFab.setOnClickListener(v -> presenter.previousAction());
    nextActionFab.setOnClickListener(v -> presenter.nextAction());
  }

  @Override
  public void navigateToPreviousPage(){
    Intent intent = new Intent(this, AddRecipeActivity.class);
    startActivity(intent);
  }

  @Override
  public void navigateToNextPage(){
    Intent intent = new Intent(this, AddStepsActivity.class);
    startActivity(intent);
  }
}