package com.cpp.shareremind;

public class ApiResonse<T> {
    private int statusCode;
    private String message;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResonse success() {
        ApiResonse apiResonse = new ApiResonse();
        apiResonse.statusCode = 0;
        apiResonse.data = "ok";
        return apiResonse;
    }
}
