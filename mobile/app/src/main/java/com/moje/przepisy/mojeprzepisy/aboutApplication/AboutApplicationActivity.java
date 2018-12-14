package com.moje.przepisy.mojeprzepisy.aboutApplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.moje.przepisy.mojeprzepisy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutApplicationActivity extends AppCompatActivity implements
        AboutApplicationContract.View{
  @BindView(R.id.toolbar_app_description) Toolbar toolbar;
  @BindView(R.id.app_description_title_my_card_view_layout) CardView descriptionTitle;
  @BindView(R.id.app_author_title_my_card_view_layout) CardView authorTitle;
  @BindView(R.id.app_version_title_my_card_view_layout) CardView versionTitle;
  @BindView(R.id.app_error_title_my_card_view_layout) CardView errorTitle;
  @BindView(R.id.app_description_text_pl_my_card_view_layout) CardView descriptionTextPl;
  @BindView(R.id.app_author_text_pl_my_card_view_layout) CardView authorTextPl;
  @BindView(R.id.app_version_text_pl_my_card_view_layout) CardView versionTextPl;
  @BindView(R.id.app_error_text_pl_my_card_view_layout) CardView errorTextPl;
  @BindView(R.id.app_description_text_ang_my_card_view_layout) CardView descriptionTextAng;
  @BindView(R.id.app_author_text_ang_my_card_view_layout) CardView authorTextAng;
  @BindView(R.id.app_version_text_ang_my_card_view_layout) CardView versionTextAng;
  @BindView(R.id.app_error_text_ang_my_card_view_layout) CardView errorTextAng;
  @BindView(R.id.app_description_play_arrow) ImageView descriptionPlayArrow;
  @BindView(R.id.app_author_play_arrow) ImageView authorPlayArrow;
  @BindView(R.id.app_version_play_arrow) ImageView versionPlayArrow;
  @BindView(R.id.app_error_play_arrow) ImageView errorPlayArrow;
  AboutApplicationContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_app_view);
    ButterKnife.bind(this);
    context = getApplicationContext();

    presenter = new AboutApplicationPresenter(this);
    setDetailsOnClickListeners();
    setToolbar();
  }

  @Override
  public void setToolbar() {
    toolbar.setSubtitle(R.string.app_description);
    setSupportActionBar(toolbar);
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void openAppDescriptionCardView() {
    descriptionPlayArrow.setImageDrawable(getDownArrow());
    descriptionTextPl.setVisibility(View.VISIBLE);
    descriptionTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppDescriptionCardView() {
    descriptionPlayArrow.setImageDrawable(getRightArrow());
    descriptionTextPl.setVisibility(View.GONE);
    descriptionTextAng.setVisibility(View.GONE);
  }

  @Override
  public void openAppAuthorCardView() {
    authorPlayArrow.setImageDrawable(getDownArrow());
    authorTextPl.setVisibility(View.VISIBLE);
    authorTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppAuthorCardView() {
    authorPlayArrow.setImageDrawable(getRightArrow());
    authorTextPl.setVisibility(View.GONE);
    authorTextAng.setVisibility(View.GONE);
  }

  @Override
  public void openAppVersionCardView() {
    versionPlayArrow.setImageDrawable(getDownArrow());
    versionTextPl.setVisibility(View.VISIBLE);
    versionTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppVersionCardView() {
    versionPlayArrow.setImageDrawable(getRightArrow());
    versionTextPl.setVisibility(View.GONE);
    versionTextAng.setVisibility(View.GONE);
  }

  @Override
  public void openAppErrorCardView() {
    errorPlayArrow.setImageDrawable(getDownArrow());
    errorTextPl.setVisibility(View.VISIBLE);
    errorTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppErrorCardView() {
    errorPlayArrow.setImageDrawable(getRightArrow());
    errorTextPl.setVisibility(View.GONE);
    errorTextAng.setVisibility(View.GONE);
  }

  @Override
  public void setDetailsOnClickListeners() {
    descriptionTitle.setOnClickListener(v -> presenter.openOrCloseAppDescriptionCardView());
    authorTitle.setOnClickListener(v -> presenter.openOrCloseAppAuthorCardView());
    versionTitle.setOnClickListener(v -> presenter.openOrCloseAppVersionCardView());
    errorTitle.setOnClickListener(v -> presenter.openOrCloseAppErrorCardView());
  }

  @Override
  public Drawable getDownArrow() {
    return context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
  }

  @Override
  public Drawable getRightArrow() {
    return context.getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp);
  }
}
