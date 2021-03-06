package com.moje.przepisy.mojeprzepisy.data.model;

public class Step {
  private int id;
  private int recipeId;
  private int userId;
  private String photo;
  private int photoNumber;
  private int stepNumber;
  private String stepDescription;

  public Step(){

  }

  public Step(int stepNumber, String stepDescription) {
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(String photo, int stepNumber, String stepDescription){
    this.photo = photo;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(int recipeId, String photo, int stepNumber, String stepDescription) {
    this.recipeId = recipeId;
    this.photo = photo;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(int recipeId, int photoNumber, int stepNumber, String stepDescription) {
    this.recipeId = recipeId;
    this.photoNumber = photoNumber;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(int id, int recipeId, int userId, int photoNumber, int stepNumber,
              String stepDescription) {
    this.id = id;
    this.recipeId = recipeId;
    this.userId = userId;
    this.photoNumber = photoNumber;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(int photoNumber, int stepNumber, String stepDescription) {
    this.photoNumber = photoNumber;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public int getId() {
    return id;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public String getPhoto() {
    return photo;
  }

  public int getStepNumber() {
    return stepNumber;
  }

  public String getStepDescription() {
    return stepDescription;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public void setStepNumber(int stepNumber) {
    this.stepNumber = stepNumber;
  }

  public void setStepDescription(String stepDescription) {
    this.stepDescription = stepDescription;
  }

  public int getPhotoNumber() {
    return photoNumber;
  }

  public void setPhotoNumber(int photoNumber) {
    this.photoNumber = photoNumber;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String toString() {
    return String.format("Step(%d,  %d, '%s')",
        recipeId,
        stepNumber,
        stepDescription
    );
  }


}
