
package com.headshot.discountforme.Model.FavouritesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("discount_code")
    @Expose
    private String discountCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("fav_status")
    @Expose
    private boolean favStatus;
    @SerializedName("rated_status")
    @Expose
    private boolean ratedStatus;
    @SerializedName("used_status")
    @Expose
    private boolean usedStatus;
    @SerializedName("discount_percent")
    @Expose
    private String discountPercent;
    @SerializedName("used_count")
    @Expose
    private int usedCount;
    @SerializedName("last_used")
    @Expose
    private String lastUsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Datum withId(int id) {
        this.id = id;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Datum withImage(String image) {
        this.image = image;
        return this;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Datum withDiscountCode(String discountCode) {
        this.discountCode = discountCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Datum withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Datum withUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isFavStatus() {
        return favStatus;
    }

    public void setFavStatus(boolean favStatus) {
        this.favStatus = favStatus;
    }

    public Datum withFavStatus(boolean favStatus) {
        this.favStatus = favStatus;
        return this;
    }

    public boolean isRatedStatus() {
        return ratedStatus;
    }

    public void setRatedStatus(boolean ratedStatus) {
        this.ratedStatus = ratedStatus;
    }

    public Datum withRatedStatus(boolean ratedStatus) {
        this.ratedStatus = ratedStatus;
        return this;
    }

    public boolean isUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(boolean usedStatus) {
        this.usedStatus = usedStatus;
    }

    public Datum withUsedStatus(boolean usedStatus) {
        this.usedStatus = usedStatus;
        return this;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Datum withDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public Datum withUsedCount(int usedCount) {
        this.usedCount = usedCount;
        return this;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(String lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Datum withLastUsed(String lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

}
