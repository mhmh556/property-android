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
import com.moataz.salah.propertymanagement.databinding.GeneralExpensesItemBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;
import java.util.ArrayList;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ItemHolder> implements Filterable {
    Context context;
    private List<ExpensesModel> list;
    private List<ExpensesModel> exampleListFull;
    onItemClick onItemClick;
    onDeleteClick onDeleteClick;
    onEditClick onEditClick;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ExpensesAdapter(Context context, List<ExpensesModel> list) {
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
    public ExpensesAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.general_expenses_item, viewGroup, false);

        return new ExpensesAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesAdapter.ItemHolder ItemHolder, int i) {
        ExpensesModel expensesModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(expensesModel.getExpenseId()));
        viewBinderHelper.closeLayout(String.valueOf(expensesModel.getExpenseId()));
        ItemHolder.bindData(expensesModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        GeneralExpensesItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = GeneralExpensesItemBinding.bind(itemView);
        }
        public void bindData(final ExpensesModel expensesModel) {
            binding.nameText.setText(expensesModel.getTitle());
            binding.dateText.setText(expensesModel.getCreated());
            binding.priceText.setText(String.valueOf(expensesModel.getPrice()));
            binding.phoneNumber.setText(String.valueOf(expensesModel.getCreatedUserId()));
            binding.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClickListener(expensesModel);
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
            List<ExpensesModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ExpensesModel item : exampleListFull) {
                    if (item.getExpenseId().toString().toLowerCase().contains(filterPattern) ||
                            item.getTitle().toLowerCase().contains(filterPattern) ||
                            item.getPrice().toString().toLowerCase().contains(filterPattern)) {
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
        void onClickListener(ExpensesModel item);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(ExpensesModel item);
    }

    public interface onEditClick{
        void onEditClickListener(ExpensesModel item);
    }

}

