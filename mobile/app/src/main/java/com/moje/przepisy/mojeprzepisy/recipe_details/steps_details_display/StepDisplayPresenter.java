package com.moje.przepisy.mojeprzepisy.recipe_details.steps_details_display;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.util.List;

public class StepDisplayPresenter implements StepDisplayContract.Presenter,
    RecipeRepository.OnStepsDetailsDisplayListener {
  private RecipeRepository recipeRepository;
  private StepDisplayContract.View stepDisplayView;

  public StepDisplayPresenter(StepDisplayContract.View stepDisplayView,
      RecipeRepository recipeRepository){
    this.stepDisplayView = stepDisplayView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void setWholeRecipeElements() {
    recipeRepository.getSteps(stepDisplayView.getRecipeId(), this);
  }

  @Override
  public void setSteps(List<Step> stepList) {
    if(stepDisplayView != null){
      int userId = PreferenceManager
          .getDefaultSharedPreferences(stepDisplayView.getContext()).getInt(Constant.PREF_USER_ID, 0);
      if(userId != 0){
        if(userId == stepList.get(0).getUserId()){
          stepDisplayView.getEditAndDeleteRecipeRelativeLayout().setVisibility(View.VISIBLE);
        }else {
          stepDisplayView.getEditAndDeleteRecipeRelativeLayout().setVisibility(View.GONE);
        }
      }
      stepDisplayView.setStepsRecyclerView(stepList);
    }
  }

  @Override
  public void onStepsError() {
    Toast.makeText(stepDisplayView.getContext(), "Błąd podczas pobierania kroków!",
        Toast.LENGTH_SHORT).show();
  }
}
