package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.PriceItemBinding;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;
import java.util.List;

public class PropertyPriceAdapter extends RecyclerView.Adapter<PropertyPriceAdapter.ItemHolder> {
    Context context;
    List<PropertyPriceModel> list;
    onClick onClick ;
    onEditClick onEditClick ;
    onDeleteClick onDeleteClick ;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public void setOnClick(PropertyPriceAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public void setOnEditClick(PropertyPriceAdapter.onEditClick onEditClick) {
        this.onEditClick = onEditClick;
    }

    public void setOnDeleteClick(PropertyPriceAdapter.onDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public PropertyPriceAdapter(Context context, List<PropertyPriceModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PropertyPriceAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.price_item, viewGroup, false);

        return new PropertyPriceAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyPriceAdapter.ItemHolder ItemHolder, int i) {
        PropertyPriceModel propertyPriceModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(propertyPriceModel.getPropertyId()));
        viewBinderHelper.closeLayout(String.valueOf(propertyPriceModel.getPropertyId()));
        ItemHolder.bindData(propertyPriceModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        PriceItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = PriceItemBinding.bind(itemView);
        }
        public void bindData(final PropertyPriceModel propertyPriceModel) {
            binding.priceName.setText(propertyPriceModel.getPriceName());
            binding.setPrice(String.valueOf(propertyPriceModel.getPrice()));
            binding.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(propertyPriceModel);
                }
            });

            binding.txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onEditClickListener(propertyPriceModel);
                }
            });

            binding.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick.onDeleteClickListener(propertyPriceModel);
                }
            });
        }
    }

    public interface onClick{
        void onClickListener(PropertyPriceModel item);
    }

    public interface onEditClick{
        void onEditClickListener(PropertyPriceModel item);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(PropertyPriceModel item);
    }

}

