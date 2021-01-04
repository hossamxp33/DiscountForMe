package com.headshot.discountforme.Main.Adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.headshot.discountforme.Model.FavouritesModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ItemFavouritesBinding;


public class FavouriteAdapter extends PagedListAdapter<Datum, FavouriteAdapter.ViewHolder> {
    private Context context;
    private String token;

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

    public FavouriteAdapter(Context context,String token) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        return new FavouriteAdapter.ViewHolder(ItemFavouritesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Datum datum = getItem(position);
        ParentClass.LoadImageWithPicasso(datum.getImage(),context,holder.binding.ivCategory);
        holder.binding.tvDescription.setText(datum.getDescription());
        holder.binding.tvNumberOfDownloads.setText(context.getString(R.string.thisCouponWasUsed) + " " + datum.getUsedCount() + " " + context.getString(R.string.time));
        holder.binding.tvLastUsage.setText(context.getString(R.string.lastUsageWas) + " " + datum.getLastUsed());
        holder.binding.relativeShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(datum.getUrl()));
                context.startActivity(browserIntent);
            }
        });
        holder.binding.rlCopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentClass.makeSuccessToast(context,context.getString(R.string.copiedSuccessfully));
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("code",datum.getDiscountCode());
                clipboard.setPrimaryClip(clip);
                holder.binding.rlCopyCode.setBackgroundResource(R.drawable.drawable_black_25_radios);
                holder.binding.tvCopyCode.setTextColor(Color.parseColor("#FFFFFF"));
                holder.binding.tvCopyCode.setText(context.getString(R.string.copied));
                holder.binding.rlCopyCode.setClickable(false);
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFavouritesBinding binding;

        public ViewHolder(ItemFavouritesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
