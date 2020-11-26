
package com.headshot.discountforme.Model.CategoriesModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("value")
    @Expose
    private boolean value;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public CategoriesModel withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public CategoriesModel withValue(boolean value) {
        this.value = value;
        return this;
    }

}
