package com.flpitu88.futbolinea.ui.login;

import androidx.annotation.Nullable;

public class SigninFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer repeatPasswordError;
    private boolean isDataValid;

    SigninFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,@Nullable Integer repeatPasswordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.repeatPasswordError = repeatPasswordError;
        this.isDataValid = false;
    }

    SigninFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.repeatPasswordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getRepeatPasswordError() {return repeatPasswordError;}

    boolean isDataValid() {
        return isDataValid;
    }
}
