package com.myresearch.myresearch;

public class data_model_finshe {
    String subject;
    String url;
    String number;
    String casetask;

    public data_model_finshe() {
    }

    public data_model_finshe(String subject, String url, String number, String casetask) {
        this.subject = subject;
        this.url = url;
        this.number = number;
        this.casetask = casetask;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCasetask() {
        return casetask;
    }

    public void setCasetask(String casetask) {
        this.casetask = casetask;
    }
}