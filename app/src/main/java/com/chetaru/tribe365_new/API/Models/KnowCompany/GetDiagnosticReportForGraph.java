package com.chetaru.tribe365_new.API.Models.KnowCompany;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GetDiagnosticReportForGraph {

    @SerializedName("exception")
    private Object mException;
    @SerializedName("headers")
    private Headers mHeaders;
    @SerializedName("original")
    private Original mOriginal;

    public Object getException() {
        return mException;
    }

    public void setException(Object exception) {
        mException = exception;
    }

    public Headers getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Headers headers) {
        mHeaders = headers;
    }

    public Original getOriginal() {
        return mOriginal;
    }

    public void setOriginal(Original original) {
        mOriginal = original;
    }

}
