package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.CustomerItemBinding;
import com.moataz.salah.propertymanagement.model.customer.CustomerModel;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ItemHolder> {
    Context context;
    List<CustomerModel> list;
    onClick onClick ;

    public void setOnClick(CustomerAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public CustomerAdapter(Context context, List<CustomerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomerAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.customer_item, viewGroup, false);

        return new CustomerAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ItemHolder ItemHolder, int i) {
        CustomerModel customerModel = list.get(i);
        ItemHolder.bindData(customerModel);
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
        public void bindData(final CustomerModel customerModel) {
            binding.textName.setText(customerModel.getName());
            binding.textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(customerModel);
                }
            });
        }
    }

    public interface onClick{
        void onClickListener(CustomerModel item);
    }

}

