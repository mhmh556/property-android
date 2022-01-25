package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.ProductItemBinding;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ItemHolder> implements Filterable {
    private Context context;
    private List<ProductsModel> list;
    private List<ProductsModel> exampleListFull;
    onItemClick onItemClick;
    onDeleteClick onDeleteClick;
    onEditClick onEditClick;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ProductsAdapter(Context context, List<ProductsModel> list) {
        this.context = context;
        this.list = list;
        exampleListFull = new ArrayList<>(list);
    }

    public void setOnItemClick(ProductsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnDeleteClick(ProductsAdapter.onDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public void setOnEditClick(ProductsAdapter.onEditClick onEditClick) {
        this.onEditClick = onEditClick;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.product_item, viewGroup, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder ItemHolder, int i) {
        ProductsModel productsModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(productsModel.getProductId()));
        viewBinderHelper.closeLayout(String.valueOf(productsModel.getProductId()));
        ItemHolder.bindData(productsModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        ProductItemBinding binding ;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProductItemBinding.bind(itemView);
        }

        public void bindData(final ProductsModel productsModel) {
            String baseUrl = "https://login-system-users-bucket.s3.amazonaws.com/images";

            binding.name.setText(productsModel.getName());
            binding.count.setText(String.valueOf(productsModel.getQty()));
            binding.price.setText(String.valueOf(productsModel.getSalePrice()));
            if (productsModel.getImage() == null || productsModel.getImage().equals("default") ||
                    productsModel.getImage().equals("")){
                binding.itemImage.setImageResource(R.drawable.default_product_pic);
            }else {
                Picasso.get().load(baseUrl + productsModel.getImage() + "_S.png")
                        .into(binding.itemImage);
            }

            binding.txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onEditClickListener(productsModel);
                }
            });

            binding.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick.onDeleteClickListener(productsModel);
                }
            });

            binding.itemCountainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",productsModel);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductsModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProductsModel item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) ||
                            item.getCreated().toLowerCase().contains(filterPattern) ||
                            item.getAppApiKey().toLowerCase().contains(filterPattern)) {

                        filteredList.add(item);

                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface onItemClick{
        void onClickListener(ProductsModel productsModel);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(ProductsModel productsModel);
    }

    public interface onEditClick{
        void onEditClickListener(ProductsModel productsModel);
    }


}
