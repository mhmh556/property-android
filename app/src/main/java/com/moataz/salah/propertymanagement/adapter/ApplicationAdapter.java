package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.AppItemBinding;
import com.moataz.salah.propertymanagement.model.application.ApplicationModel;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ItemHolder> {
    Context context;
    List<ApplicationModel> list;
    private onSaleItemClick onSaleItemClick;
    private onInfoClick onInfoClick;

    public ApplicationAdapter(Context context, List<ApplicationModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnInfoClick(ApplicationAdapter.onInfoClick onInfoClick) {
        this.onInfoClick = onInfoClick;
    }

    public void setOnSaleItemClick(onSaleItemClick onSaleItemClick) {
        this.onSaleItemClick = onSaleItemClick;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.app_item, viewGroup, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder ItemHolder, int i) {
        ApplicationModel applicationModel = list.get(i);
        ItemHolder.bindData(applicationModel);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        AppItemBinding binding ;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = AppItemBinding.bind(itemView);
        }

        public void bindData(final ApplicationModel applicationModel) {

            binding.storeName.setText(applicationModel.getAppName());
            binding.storeLogo.setPadding(50,50,50,50);

            binding.storeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSaleItemClick.onClickListener(applicationModel);
                }
            });

            binding.infoBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onInfoClick.onInfoClickListener(applicationModel);
                }
            });
        }
    }

    public interface onSaleItemClick {
        void onClickListener(ApplicationModel item);
    }

    public interface onInfoClick{
        void onInfoClickListener(ApplicationModel item);
    }
}