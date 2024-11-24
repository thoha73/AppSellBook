package com.example.appsellbook.graphql;

public class GraphQLResponse<T> {
    private T data;
    private Object errors;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
