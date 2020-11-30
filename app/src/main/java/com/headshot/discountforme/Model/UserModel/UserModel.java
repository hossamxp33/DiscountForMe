
package com.headshot.discountforme.Model.UserModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("value")
    @Expose
    private boolean value;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("msg")
    @Expose
    private String msg;

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
