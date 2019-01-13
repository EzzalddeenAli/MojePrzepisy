package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;

import java.sql.Timestamp;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CardAPI {

  @GET("/cards/default")
  Single<List<OneRecipeCard>> getCards();

  @GET("/cards/alphabetically")
  Single<List<OneRecipeCard>> getCardsSortedAlphabetically();

  @GET("/cards/lastAdded")
  Single<List<OneRecipeCard>> getCardsSortedByLastAdded();

  @GET("/cards/highestRated")
  Single<List<OneRecipeCard>> getCardsSortedByHighestRated();

  @GET("/cards/favorite")
  Single<List<OneRecipeCard>> getCardsSortedByFavorite();

  @GET("cards/searched")
  Single<List<OneRecipeCard>> getCardsSortedBySearchedQuery(@Body OneRecipeCard oneRecipeCard);

  @GET("cards/category")
  Single<List<OneRecipeCard>> getCardsSortedByCategoryQuery(@Body OneRecipeCard oneRecipeCard);

  @GET("/cards/user")
  Single<List<OneRecipeCard>> getUserCards();

  @GET("/cards/{id}")
  Single<List<OneRecipeCard>> getUpdatedCard(@Path("id") int recipeId);

  @GET("/cards/new/{maxDate}")
  Single<Message> getNewCards(@Path("maxDate") int maxDate);
}
