package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.CustomerItemBinding;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import java.util.List;

public class AptTypeAdapter extends RecyclerView.Adapter<AptTypeAdapter.ItemHolder> {
    Context context;
    List<AptTypeModel> list;
    onClick onClick ;
    onEditClick onEditClick ;
    onDeleteClick onDeleteClick ;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public void setOnClick(AptTypeAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public void setOnEditClick(onEditClick onEditClick) {
        this.onEditClick = onEditClick;
    }

    public void setOnDeleteClick(onDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public AptTypeAdapter(Context context, List<AptTypeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AptTypeAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.customer_item, viewGroup, false);

        return new AptTypeAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AptTypeAdapter.ItemHolder ItemHolder, int i) {
        AptTypeModel aptTypeModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(aptTypeModel.getAptTypeId()));
        viewBinderHelper.closeLayout(String.valueOf(aptTypeModel.getAptTypeId()));
        ItemHolder.bindData(aptTypeModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        CustomerItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomerItemBinding.bind(itemView);
        }
        public void bindData(final AptTypeModel aptTypeModel) {
            binding.textName.setText(aptTypeModel.getAptTypeName());
            binding.textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(aptTypeModel);
                }
            });

            binding.txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onEditClickListener(aptTypeModel);
                }
            });

            binding.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick.onDeleteClickListener(aptTypeModel);
                }
            });
        }
    }

    public interface onClick{
        void onClickListener(AptTypeModel aptTypeModel);
    }

    public interface onEditClick{
        void onEditClickListener(AptTypeModel aptTypeModel);
    }

    public interface onDeleteClick{
        void onClickListener(AptTypeModel aptTypeModel);
        void onDeleteClickListener(AptTypeModel aptTypeModel);
    }

}

