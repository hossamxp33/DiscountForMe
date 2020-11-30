package com.headshot.discountforme.Main.Adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.headshot.discountforme.Main.Activities.DealDetails.View.DealDetailsActivity;
import com.headshot.discountforme.Model.HomeModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ItemDealsBinding;


public class DealAdapter extends PagedListAdapter<Datum, DealAdapter.ViewHolder> {
    private Context context;
    private String token;

    private static DiffUtil.ItemCallback<Datum> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<Datum>() {
        @Override
        public boolean areItemsTheSame(@NonNull Datum listBean, @NonNull Datum t1) {
            return listBean.getId() == t1.getId();
        }


        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Datum listBean, @NonNull Datum t1) {
            return listBean.equals(t1);
        }
    };

    public DealAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DealAdapter.ViewHolder(ItemDealsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum datum = getItem(position);
        ParentClass.LoadImageWithPicasso(datum.getImage(), context, holder.binding.ivCategory);
        holder.binding.tvDeal.setText(datum.getDescription());
        holder.binding.tvDealTime.setText(datum.getLastUsed());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentClass.makeSuccessToast(context, context.getString(R.string.copiedSuccessfully));
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("code", datum.getDiscountCode());
                clipboard.setPrimaryClip(clip);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDealsBinding binding;

        public ViewHolder(ItemDealsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
