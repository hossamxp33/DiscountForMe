package com.headshot.discountforme.Main.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.headshot.discountforme.Model.SliderModel.Datum;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ItemSliderBinding;

import java.util.ArrayList;
import java.util.List;


public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    ArrayList<Datum> homeList;
    Context context;
    LayoutInflater layoutInflater;
    ShimmerRecyclerView rvOrders;

    public SliderAdapter(Context context, ShimmerRecyclerView rvSubjects) {
        this.homeList = new ArrayList<>();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rvOrders = rvSubjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderAdapter.ViewHolder(ItemSliderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParentClass.LoadImageWithPicasso(homeList.get(position).getImage(), context, holder.binding.ivImage);
    }

    @Override
    public int getItemCount() {
        if (homeList.size() == 0) {
            rvOrders.showShimmerAdapter();
            return 10;
        } else {
            return homeList.size();
        }
    }


    public int getItemViewType(int position) {
        return position;
    }

    public void addAll(List<Datum> data) {
        homeList.clear();
        homeList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSliderBinding binding;

        public ViewHolder(ItemSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
