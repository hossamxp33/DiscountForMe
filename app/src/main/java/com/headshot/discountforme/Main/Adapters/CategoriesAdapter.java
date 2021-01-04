package com.headshot.discountforme.Main.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.headshot.discountforme.Main.Activities.Home.ViewModel.HomeViewModel;
import com.headshot.discountforme.Model.CategoriesModel.Datum;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ItemCategoriesBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    ArrayList<Datum> homeList;
    Context context;
    LayoutInflater layoutInflater;
    int selectedPosition = -1;
    ShimmerRecyclerView rvOrders;
    HomeViewModel homeViewModel;
    String token;
    RelativeLayout relativeAll;
    ShimmerRecyclerView rvCoupons;
    HomeAdapter homeAdapter;
    Boolean isLogin;

    public CategoriesAdapter(Context context,ShimmerRecyclerView rvSubjects,
                             HomeViewModel homeViewModel,String token,
                             RelativeLayout relativeAll,ShimmerRecyclerView rvCoupons,
                             HomeAdapter homeAdapter,Boolean isLogin) {
        this.homeList = new ArrayList<>();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rvOrders = rvSubjects;
        this.homeViewModel = homeViewModel;
        this.token = token;
        this.relativeAll = relativeAll;
        this.rvCoupons = rvCoupons;
        this.homeAdapter = homeAdapter;
        this.isLogin = isLogin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        return new CategoriesAdapter.ViewHolder(ItemCategoriesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        holder.binding.tvCategory.setText(homeList.get(position).getName());
        ParentClass.LoadImageWithPicasso(homeList.get(position).getImage(),context,holder.binding.ivCategory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(selectedPosition == position)) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                }
            }
        });
        relativeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getHome("0",token,isLogin);
                homeViewModel.itemPagedList.observe((LifecycleOwner) context,new Observer<PagedList<com.headshot.discountforme.Model.HomeModel.Datum>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                        rvCoupons.hideShimmerAdapter();
                        homeAdapter.submitList(listBeans);
                        Log.v("size",listBeans.size() + "GOOD");
                        selectedPosition = -1;
                        notifyDataSetChanged();

                    }
                });
            }
        });

        if (selectedPosition == position) {
            holder.binding.cvCategory.setBorderColor(Color.parseColor("#f01313"));
            homeViewModel.getHome(String.valueOf(homeList.get(position).getId()),token,isLogin);
            homeViewModel.itemPagedList.observe((LifecycleOwner) context,new Observer<PagedList<com.headshot.discountforme.Model.HomeModel.Datum>>() {
                @Override
                public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                    rvCoupons.hideShimmerAdapter();
                    homeAdapter.submitList(listBeans);
                    Log.v("size",listBeans.size() + "GOOD");
                }
            });

        } else {
            holder.binding.cvCategory.setBorderColor(Color.parseColor("#FFFFFF"));

        }

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
        ItemCategoriesBinding binding;

        public ViewHolder(ItemCategoriesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
