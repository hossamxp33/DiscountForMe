
package com.headshot.discountforme.Model.NotificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("first")
    @Expose
    private String first;
    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("prev")
    @Expose
    private Object prev;
    @SerializedName("next")
    @Expose
    private String next;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public Links withFirst(String first) {
        this.first = first;
        return this;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Links withLast(String last) {
        this.last = last;
        return this;
    }

    public Object getPrev() {
        return prev;
    }

    public void setPrev(Object prev) {
        this.prev = prev;
    }

    public Links withPrev(Object prev) {
        this.prev = prev;
        return this;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Links withNext(String next) {
        this.next = next;
        return this;
    }

}
