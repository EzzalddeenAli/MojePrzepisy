package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user;

import com.moje.przepisy.mojeprzepisy.data.model.User;

public interface UserRepositoryInterface {
  interface OnLoginFinishedListener {
    void onLoginAndPasswordError();
    void onSuccess();
  }

  void login(String login, String password, OnLoginFinishedListener listener);

  interface OnLogoutFinishedListener {
    void onLogoutError(String message);
    void onSuccess();
  }

  void logout(OnLogoutFinishedListener listener);

  interface OnRegisterFinishedListener {
    void onLoginError();
    boolean onPasswordOrEmailError();
    boolean onValidatePasswordError(String password);
    boolean onValidateEmailError(String email);
    void onSuccess();
    void onOtherError(String message);
  }

  void register(String name, String lastName, String login, String password, String email, OnRegisterFinishedListener listener);

  interface OnEditUserFinishedListener{
    void onEditAndSendDataError();
    void onSuccess();
    boolean onPasswordError();
    boolean onValidatePasswordError(String password);
    boolean onValidateEmailError(String email);
  }

  void editUser(String columnName, String columnValue, OnEditUserFinishedListener listener);

  interface OnEditPhotoUserFinishedListener{
    void onEditAndSendPhotoError();
    void onPhotoSuccess();
  }
  void editPhotoUser(String photoData, OnEditPhotoUserFinishedListener listener);

  interface OnGetUserFinishedListener{
    void onGetUserError();
    void setUserValue(User user);
  }

  void getUser(OnGetUserFinishedListener listener);
}
