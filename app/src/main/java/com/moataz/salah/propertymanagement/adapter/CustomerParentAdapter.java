package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.CustomerParentItemBinding;
import com.moataz.salah.propertymanagement.model.customer.CustomerModel;
import java.util.ArrayList;
import java.util.List;

public class CustomerParentAdapter extends RecyclerView.Adapter<CustomerParentAdapter.ItemHolder> implements Filterable {
    private Context context;
    private List<CustomerModel> list;
    private List<CustomerModel> exampleListFull;
    onItemClick onItemClick;
    onDeleteClick onDeleteClick;
    onEditClick onEditClick;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public CustomerParentAdapter(Context context, List<CustomerModel> list) {
        this.context = context;
        this.list = list;
        exampleListFull = new ArrayList<>(list);
    }

    public void setOnItemClick(onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnDeleteClick(onDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public void setOnEditClick(onEditClick onEditClick) {
        this.onEditClick = onEditClick;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.customer_parent_item, viewGroup, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder ItemHolder, int i) {
        CustomerModel customerModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(customerModel.getCustomerId()));
        viewBinderHelper.closeLayout(String.valueOf(customerModel.getCustomerId()));
        ItemHolder.bindData(customerModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        CustomerParentItemBinding binding ;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomerParentItemBinding.bind(itemView);
        }

        public void bindData(final CustomerModel customerModel) {

            binding.name.setText(customerModel.getName());
            binding.address.setText(customerModel.getAddress());
            binding.created.setText(customerModel.getCreated());

            binding.itemCountainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClickListener(customerModel);
                }
            });

            binding.txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onEditClickListener(customerModel);
                }
            });

            binding.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick.onDeleteClickListener(customerModel);
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
            List<CustomerModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CustomerModel item : exampleListFull) {
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
        void onClickListener(CustomerModel item);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(CustomerModel item);
    }

    public interface onEditClick{
        void onEditClickListener(CustomerModel item);
    }
}
