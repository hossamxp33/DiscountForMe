package com.headshot.discountforme.Main.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.headshot.discountforme.Main.Activities.Home.ViewModel.HomeViewModel;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.Model.HomeModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ItemCouponsBinding;

public class HomeAdapter extends PagedListAdapter<Datum, HomeAdapter.ViewHolder> {
    private Context context;
    private HomeViewModel homeViewModel;
    private String token;
    Boolean isLogin;
    Dialog dialogLogin;
    private static DiffUtil.ItemCallback<Datum> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<Datum>() {
        @Override
        public boolean areItemsTheSame(@NonNull Datum listBean,@NonNull Datum t1) {
            return listBean.getId() == t1.getId();
        }


        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Datum listBean,@NonNull Datum t1) {
            return listBean.equals(t1);
        }
    };

    public HomeAdapter(Context context,HomeViewModel homeViewModel,String token,Boolean isLogin,Dialog dialogLogin) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.homeViewModel = homeViewModel;
        this.token = token;
        this.dialogLogin = dialogLogin;
        this.isLogin = isLogin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        return new ViewHolder(ItemCouponsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Datum datum = getItem(position);
        if (datum.isUsedStatus() && datum.isRatedStatus()) {
            holder.binding.rrvHiddenCode.setVisibility(View.GONE);
            holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
            holder.binding.tvDescription.setVisibility(View.GONE);
            holder.binding.relativeInfoShownCode.setVisibility(View.GONE);
            holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
            holder.binding.relativeAfterRate.setVisibility(View.VISIBLE);
        } else if (datum.isUsedStatus() && !datum.isRatedStatus()) {
            holder.binding.rrvHiddenCode.setVisibility(View.GONE);
            holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
            holder.binding.tvDescription.setVisibility(View.GONE);
            holder.binding.relativeInfoShownCode.setVisibility(View.VISIBLE);
            holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
            holder.binding.relativeAfterRate.setVisibility(View.GONE);

        } else {
            holder.binding.rrvHiddenCode.setVisibility(View.VISIBLE);
            holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
            holder.binding.relativeInfoShownCode.setVisibility(View.GONE);
            holder.binding.relativeShownCode.setVisibility(View.GONE);
            holder.binding.relativeAfterRate.setVisibility(View.GONE);
        }
        ParentClass.LoadImageWithPicasso(datum.getImage(),context,holder.binding.ivCategory);
        holder.binding.rrvHiddenCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    homeViewModel.useCoupon(String.valueOf(datum.getId()),token).observe((LifecycleOwner) context,new Observer<GeneralResponse>() {
                        @Override
                        public void onChanged(GeneralResponse notificationsModel) {
                            if (notificationsModel != null) {
                                if (notificationsModel.getValue()) {
                                    datum.setUsedStatus(true);
                                    holder.binding.rrvHiddenCode.setVisibility(View.GONE);
                                    holder.binding.tvDescription.setVisibility(View.GONE);
                                    holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
                                    holder.binding.relativeInfoShownCode.setVisibility(View.VISIBLE);
                                    holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
                                    holder.binding.relativeAfterRate.setVisibility(View.GONE);
                                    ParentClass.makeSuccessToast(context,context.getString(R.string.copiedSuccessfully));
                                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("code",datum.getDiscountCode());
                                    clipboard.setPrimaryClip(clip);
                                } else {
                                    ParentClass.makeErrorToast(context,notificationsModel.getMsg());
                                }
                            } else {
                                ParentClass.makeErrorToast(context,context.getString(R.string.somethingWentWrong));
                            }
                        }
                    });

                } else {
                    holder.binding.rrvHiddenCode.setVisibility(View.GONE);
                    holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
                    ParentClass.makeSuccessToast(context,context.getString(R.string.copiedSuccessfully));
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("code",datum.getDiscountCode());
                    clipboard.setPrimaryClip(clip);

//                    dialogLogin.show();
                }

            }
        });
        holder.binding.tvCodeHidden.setText(datum.getDiscountCode());
        holder.binding.tvShownCode.setText(datum.getDiscountCode());
        holder.binding.tvDescription.setText(datum.getDescription());
        holder.binding.tvNumberOfDownloads.setText(context.getString(R.string.thisCouponWasUsed) + " " + datum.getUsedCount() + " " + context.getString(R.string.time));
        holder.binding.tvLastUsage.setText(context.getString(R.string.lastUsageWas) + " " + datum.getLastUsed());
        holder.binding.relativeYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeViewModel.couponReview(String.valueOf(datum.getId()),"good",token).observe((LifecycleOwner) context,new Observer<GeneralResponse>() {
                    @Override
                    public void onChanged(GeneralResponse notificationsModel) {
                        if (notificationsModel != null) {
                            if (notificationsModel.getValue()) {
                                datum.setRatedStatus(true);
                                holder.binding.rrvHiddenCode.setVisibility(View.GONE);
                                holder.binding.tvDescription.setVisibility(View.GONE);
                                holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
                                holder.binding.relativeInfoShownCode.setVisibility(View.GONE);
                                holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
                                holder.binding.relativeAfterRate.setVisibility(View.VISIBLE);
                            } else {
                                ParentClass.makeErrorToast(context,notificationsModel.getMsg());
                            }
                        } else {
                            ParentClass.makeErrorToast(context,context.getString(R.string.somethingWentWrong));
                        }
                    }
                });

            }
        });
        holder.binding.rlNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.couponReview(String.valueOf(datum.getId()),"good",token).observe((LifecycleOwner) context,new Observer<GeneralResponse>() {
                    @Override
                    public void onChanged(GeneralResponse notificationsModel) {
                        if (notificationsModel != null) {
                            if (notificationsModel.getValue()) {
                                datum.setRatedStatus(true);
                                holder.binding.rrvHiddenCode.setVisibility(View.GONE);
                                holder.binding.tvDescription.setVisibility(View.GONE);
                                holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
                                holder.binding.relativeInfoShownCode.setVisibility(View.GONE);
                                holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
                                holder.binding.relativeAfterRate.setVisibility(View.VISIBLE);
                            } else {
                                ParentClass.makeErrorToast(context,notificationsModel.getMsg());
                            }
                        } else {
                            ParentClass.makeErrorToast(context,context.getString(R.string.somethingWentWrong));
                        }
                    }
                });

            }
        });
        holder.binding.tvShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    ParentClass.showFlipDialog();
                    homeViewModel.useCoupon(String.valueOf(datum.getId()),token).observe((LifecycleOwner) context,new Observer<GeneralResponse>() {
                        @Override
                        public void onChanged(GeneralResponse notificationsModel) {
                            ParentClass.dismissFlipDialog();

                            if (notificationsModel != null) {
                                if (notificationsModel.getValue()) {
                                    datum.setUsedStatus(true);
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(datum.getUrl()));
                                    context.startActivity(browserIntent);
                                    if (datum.isUsedStatus() && datum.isRatedStatus()) {
                                        holder.binding.rrvHiddenCode.setVisibility(View.GONE);
                                        holder.binding.tvDescription.setVisibility(View.GONE);
                                        holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
                                        holder.binding.relativeInfoShownCode.setVisibility(View.GONE);
                                        holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
                                        holder.binding.relativeAfterRate.setVisibility(View.VISIBLE);
                                    } else if (datum.isUsedStatus() && !datum.isRatedStatus()) {
                                        holder.binding.rrvHiddenCode.setVisibility(View.GONE);
                                        holder.binding.tvDescription.setVisibility(View.GONE);
                                        holder.binding.relativeInfoHiddenCode.setVisibility(View.GONE);
                                        holder.binding.relativeInfoShownCode.setVisibility(View.VISIBLE);
                                        holder.binding.relativeShownCode.setVisibility(View.VISIBLE);
                                        holder.binding.relativeAfterRate.setVisibility(View.GONE);
                                    }


                                } else {
                                    ParentClass.makeErrorToast(context,notificationsModel.getMsg());
                                }
                            } else {
                                ParentClass.makeErrorToast(context,context.getString(R.string.somethingWentWrong));
                            }
                        }
                    });

                } else {
                    dialogLogin.show();
                }

            }
        });
        holder.binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,"إخصملي");
                    String shareMessage;
                    shareMessage = datum.getUrl();
                    shareIntent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent,"choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        if (datum.isFavStatus()) {
            holder.binding.shapeCircleRed.setVisibility(View.VISIBLE);
            holder.binding.shapeCircle.setVisibility(View.GONE);

        } else if (!datum.isFavStatus()) {
            holder.binding.shapeCircleRed.setVisibility(View.GONE);
            holder.binding.shapeCircle.setVisibility(View.VISIBLE);

        }
        holder.binding.rlFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    homeViewModel.favouriteCoupon(String.valueOf(datum.getId()),token).observe((LifecycleOwner) context,new Observer<GeneralResponse>() {
                        @Override
                        public void onChanged(GeneralResponse notificationsModel) {
                            if (notificationsModel != null) {
                                if (notificationsModel.getValue()) {
                                    if (datum.isFavStatus()) {
                                        datum.setFavStatus(false);
                                        holder.binding.shapeCircleRed.setVisibility(View.GONE);
                                        holder.binding.shapeCircle.setVisibility(View.VISIBLE);


                                    } else if (!datum.isFavStatus()) {
                                        datum.setFavStatus(true);
                                        holder.binding.shapeCircleRed.setVisibility(View.VISIBLE);
                                        holder.binding.shapeCircle.setVisibility(View.GONE);
                                    }
                                } else {
                                    ParentClass.makeErrorToast(context,notificationsModel.getMsg());
                                }
                            } else {
                                ParentClass.makeErrorToast(context,context.getString(R.string.somethingWentWrong));
                            }
                        }
                    });

                } else {
                    dialogLogin.show();
                }

            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCouponsBinding binding;

        public ViewHolder(ItemCouponsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
