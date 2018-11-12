package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page.AddRecipeActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps.AddStepsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;
import java.util.List;

public class DisplayAllRecipeElementsActivityView extends AppCompatActivity implements
    DisplayAllRecipeElementsContract.View, OnClickListener{
  public static final int PLEASE_WAIT_DIALOG = 1;
  @BindView(R.id.saveRecipeImageView)ImageView saveRecipeImageView;
  @BindView(R.id.informationTextView)TextView informationTextView;
  @BindView(R.id.recipeEditImageView) ImageView recipeEditImageView;
  @BindView(R.id.ingredientsEditImageView) ImageView ingredientsEditImageView;
  @BindView(R.id.stepsEditImageView) ImageView stepsEditImageView;
  private DisplayAllRecipeElementsContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_all_recipe_elements_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new DisplayAllRecipeElementsPresenter(this, new RecipeRepository(context));

    setToolbar();
    setOnClickListeners();
    presenter.setRecipeDetailsScreen();
    presenter.setIngredientsDetailScreen();
    presenter.setStepsDetailsScreen();
  }

  @Override
  public Dialog onCreateDialog(int dialogId) {
    switch (dialogId) {
      case PLEASE_WAIT_DIALOG:
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Zapisywanie");
        dialog.setMessage("Proszę czekać....");
        dialog.setCancelable(true);
        return dialog;

      default:
        break;
    }
    return null;
  }

  @Override
  public void setOnClickListeners() {
    saveRecipeImageView.setOnClickListener(this);
    recipeEditImageView.setOnClickListener(this);
    ingredientsEditImageView.setOnClickListener(this);
    stepsEditImageView.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.saveRecipeImageView){
      Log.i("onClick.saveRecipeImageView:", "Before startBackgroundActions()");
      presenter.startBackgroundActions(DisplayAllRecipeElementsActivityView.this);
      Log.i("onClick.saveRecipeImageView:", "After startBackgroundActions()");
    }else if(view.getId() == R.id.recipeEditImageView){
      presenter.setEditRecipeIconAction();
    }else if(view.getId() == R.id.ingredientsEditImageView){
      presenter.setEditIngredientsIconAction();
    }else if(view.getId() == R.id.stepsEditImageView){
      presenter.setEditStepsIconAction();
    }
  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_whole_recipe);
    toolbar.setSubtitle("Krok 4: Sprawdź wprowdzone informacje");
    setSupportActionBar(toolbar);
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void setRecipeRecyclerView(List<Recipe> recipeList) {
    MainRecipeInfoAdapter adapter = new MainRecipeInfoAdapter(this, recipeList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addMainRecipeInfoRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setIngredientsRecyclerView(List<Ingredient> ingredientList) {
    IngredientsDisplayAdapter adapter = new IngredientsDisplayAdapter(this, ingredientList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addIngredientsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setStepsRecyclerView(List<Step> stepList) {
    StepsDisplayAdapter adapter = new StepsDisplayAdapter (this, stepList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addStepsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public TextView getInformationTextView() {
    return informationTextView;
  }

  @Override
  public void navigateToMainCardsScreen() {
    Intent intent = new Intent(DisplayAllRecipeElementsActivityView.this, MainCardsActivityView.class);
    intent.putExtra("LOGGED",true);
    startActivity(intent);
  }

  @Override
  public void navigateToEditRecipeInformation() {
    Intent intent = new Intent(DisplayAllRecipeElementsActivityView.this, AddRecipeActivityView.class);
    startActivity(intent);
  }

  @Override
  public void navigateToEditIngredients() {
    Intent intent = new Intent(DisplayAllRecipeElementsActivityView.this, AddIngredientsActivityView.class);
    startActivity(intent);
  }

  @Override
  public void navigateToEditSteps() {
    Intent intent = new Intent(DisplayAllRecipeElementsActivityView.this, AddStepsActivityView.class);
    startActivity(intent);
  }
}
