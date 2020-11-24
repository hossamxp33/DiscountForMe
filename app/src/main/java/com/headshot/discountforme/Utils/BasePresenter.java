package com.headshot.discountforme.Utils;

import android.content.Context;
import android.widget.ImageView;

import static com.headshot.discountforme.Utils.ParentClass.LoadImageWithPicasso;
import static com.headshot.discountforme.Utils.ParentClass.dismissFlipDialog;
import static com.headshot.discountforme.Utils.ParentClass.handleException;
import static com.headshot.discountforme.Utils.ParentClass.makeErrorToast;
import static com.headshot.discountforme.Utils.ParentClass.makeSuccessToast;
import static com.headshot.discountforme.Utils.ParentClass.showFlipDialog;



public class BasePresenter {


    public void startLoading() {
        showFlipDialog();
    }

    public void stopLoading() {
        dismissFlipDialog();
    }

    public void errorToast(Context context, String msg) {
        makeErrorToast(context, msg);
    }

    public void successToast(Context context, String msg) {
        makeSuccessToast(context, msg);
    }

    public void handleApiException(Context context, Throwable t) {
        handleException(context, t);
    }

    public void loadImage(String url, Context context, ImageView imageView) {
        LoadImageWithPicasso(url, context, imageView);
    }
}
