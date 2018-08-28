package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import java.util.List;

public class DisplayAllRecipeElementsActivityView extends AppCompatActivity implements
    DisplayAllRecipeElementsContract.View{
  @BindView(R.id.saveRecipeImageView)ImageView saveRecipeImageView;
  @BindView(R.id.informationTextView)TextView informationTextView;
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

    saveRecipeImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.addRecipeToServer();
        presenter.addIngredientsToServer();
        presenter.addStepsToServer();
        presenter.saved();

      }
    });

    presenter.setRecipeDetailsScreen();
    presenter.setIngredientsDetailScreen();
    presenter.setStepsDetailsScreen();
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


}
