package com.headshot.discountforme.Main.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.headshot.discountforme.Main.Activities.FilterAndSearch.View.FilterAndSearchActivity;
import com.headshot.discountforme.Model.CategoriesModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.ViewHolder> {
    ArrayList<Datum> homeList;
    Context context;
    LayoutInflater layoutInflater;
    int selectedPosition = -1;
    ShimmerRecyclerView rvOrders;
    String token;
    TextView tvFilter;
    RelativeLayout relativeHighestSale;
    TextView tvHighestSale;
    RelativeLayout rlAToZ;
    TextView tvAToZ;
    RelativeLayout rlZToA;
    TextView tvZToA;
    String type = "high_discount";
    //    high_discount - alpha_desc-alpha_asc
    String servicesId = "0";
    ArrayList<String> items = new ArrayList();


    public BrandsAdapter(Context context, String token,
                         TextView tvFilter, RelativeLayout relativeHighestSale,
                         TextView tvHighestSale,
                         RelativeLayout rlAToZ, TextView tvAToZ,
                         RelativeLayout rlZToA, TextView tvZToA) {
        this.homeList = new ArrayList<>();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.token = token;
        this.tvFilter = tvFilter;
        this.relativeHighestSale = relativeHighestSale;
        this.tvHighestSale = tvHighestSale;
        this.rlAToZ = rlAToZ;
        this.tvAToZ = tvAToZ;
        this.rlZToA = rlZToA;
        this.tvZToA = tvZToA;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BrandsAdapter.ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvCategory.setText(homeList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeList.get(position).isSelected()) {
                    holder.binding.rlBackground.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    holder.binding.tvCategory.setTextColor(Color.parseColor("#000000"));
                    homeList.get(position).setSelected(false);
                } else {
                    holder.binding.rlBackground.setBackgroundColor(Color.parseColor("#000000"));
                    holder.binding.tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                    homeList.get(position).setSelected(true);

                }
            }
        });


        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < homeList.size(); i++) {
                    if (homeList.get(i).isSelected()) {
                        items.add(String.valueOf(homeList.get(i).getId()));
                    }
                }
                servicesId = android.text.TextUtils.join(",", items);


                Intent intent = new Intent(context, FilterAndSearchActivity.class);
                intent.putExtra("servicesId", servicesId);
                intent.putExtra("type", type);
                intent.putExtra("type_go", "filter");
                context.startActivity(intent);
                Bungee.split(context);


            }
        });
//        //    high_discount - alpha_desc-alpha_asc
        relativeHighestSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!type.equals("high_discount")) {
                    type = "high_discount";
                    relativeHighestSale.setBackgroundResource(R.drawable.drawable_black_5_radious);
                    rlAToZ.setBackgroundResource(R.drawable.drawable_grey_5_stroke);
                    rlZToA.setBackgroundResource(R.drawable.drawable_grey_5_stroke);
                    tvHighestSale.setTextColor(Color.parseColor("#FFFFFF"));
                    tvAToZ.setTextColor(Color.parseColor("#000000"));
                    tvZToA.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        rlAToZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!type.equals("alpha_asc")) {
                    type = "alpha_asc";
                    relativeHighestSale.setBackgroundResource(R.drawable.drawable_grey_5_stroke);
                    rlAToZ.setBackgroundResource(R.drawable.drawable_black_5_radious);
                    rlZToA.setBackgroundResource(R.drawable.drawable_grey_5_stroke);
                    tvHighestSale.setTextColor(Color.parseColor("#000000"));
                    tvAToZ.setTextColor(Color.parseColor("#FFFFFF"));
                    tvZToA.setTextColor(Color.parseColor("#000000"));


                }
            }
        });
        rlZToA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!type.equals("alpha_desc")) {
                    type = "alpha_desc";
                    relativeHighestSale.setBackgroundResource(R.drawable.drawable_grey_5_stroke);
                    rlAToZ.setBackgroundResource(R.drawable.drawable_grey_5_stroke);
                    rlZToA.setBackgroundResource(R.drawable.drawable_black_5_radious);
                    tvHighestSale.setTextColor(Color.parseColor("#000000"));
                    tvAToZ.setTextColor(Color.parseColor("#000000"));
                    tvZToA.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeList.size();
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
        ItemCategoryBinding binding;

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
