package com.moataz.salah.propertymanagement.model.sign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("results")
    @Expose
    private RegisterModel results;

    public RegisterModel getResults() {
        return results;
    }

    public void setResults(RegisterModel results) {
        this.results = results;
    }

}
