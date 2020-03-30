package com.flpitu88.futbolinea.ui.login;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flpitu88.futbolinea.R;
import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends ViewModel {

    private MutableLiveData<SigninFormState> signinFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public MutableLiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(MutableLiveData<LoginResult> loginResult) {
        this.loginResult = loginResult;
    }

    public MutableLiveData<SigninFormState> getSigninFormState() {
        return signinFormState;
    }

    public void setSigninFormState(MutableLiveData<SigninFormState> signinFormState) {
        this.signinFormState = signinFormState;
    }

    public void signin(FirebaseUser user, Boolean success) {
        if (success) {
            loginResult.setValue(new LoginResult(new LoggedInUserView(user.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void signinDataChanged(String username, String password, String repeatPassword) {
        if (!isUserNameValid(username)) {
            signinFormState.setValue(new SigninFormState(R.string.invalid_username, null,null));
        } else if (!isPasswordValid(password)) {
            signinFormState.setValue(new SigninFormState(null, R.string.invalid_password,R.string.invalid_password));
        } else {
            signinFormState.setValue(new SigninFormState(true));
        }
    }


    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
