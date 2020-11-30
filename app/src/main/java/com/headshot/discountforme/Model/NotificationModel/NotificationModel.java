
package com.headshot.discountforme.Model.NotificationModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("value")
    @Expose
    private boolean value;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public NotificationModel withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public NotificationModel withLinks(Links links) {
        this.links = links;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public NotificationModel withMeta(Meta meta) {
        this.meta = meta;
        return this;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public NotificationModel withValue(boolean value) {
        this.value = value;
        return this;
    }

}
