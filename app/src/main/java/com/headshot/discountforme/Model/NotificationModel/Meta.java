
package com.headshot.discountforme.Model.NotificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("from")
    @Expose
    private int from;
    @SerializedName("last_page")
    @Expose
    private int lastPage;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("per_page")
    @Expose
    private int perPage;
    @SerializedName("to")
    @Expose
    private int to;
    @SerializedName("total")
    @Expose
    private int total;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Meta withCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public Meta withFrom(int from) {
        this.from = from;
        return this;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public Meta withLastPage(int lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Meta withPath(String path) {
        this.path = path;
        return this;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public Meta withPerPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Meta withTo(int to) {
        this.to = to;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Meta withTotal(int total) {
        this.total = total;
        return this;
    }

}
