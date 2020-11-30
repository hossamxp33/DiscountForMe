package com.headshot.discountforme.Main.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.headshot.discountforme.Model.NotificationModel.Datum;
import com.headshot.discountforme.databinding.ItemNotificationsBinding;


public class NotificationsAdapter extends PagedListAdapter<Datum, NotificationsAdapter.ViewHolder> {
    private Context context;

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

    public NotificationsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        return new ViewHolder(ItemNotificationsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Datum datum = getItem(position);
        holder.binding.tvNotification.setText(datum.getBody());
        holder.binding.tvNotificationTime.setText(datum.getDate());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationsBinding binding;

        public ViewHolder(ItemNotificationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
