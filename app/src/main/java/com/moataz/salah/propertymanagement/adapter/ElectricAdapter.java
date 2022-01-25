package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.ExpensesItemBinding;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import java.util.ArrayList;
import java.util.List;

public class ElectricAdapter extends RecyclerView.Adapter<ElectricAdapter.ItemHolder> implements Filterable{
    private Context context;
    private List<ElectricalModel> list;
    private List<ElectricalModel> exampleListFull;

    onClick onClick ;

    public void setOnClick(onClick onClick) {
        this.onClick = onClick;
    }

    public ElectricAdapter(Context context, List<ElectricalModel> list) {
        this.context = context;
        this.list = list;
        this.exampleListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ElectricAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.expenses_item, viewGroup, false);

        return new ElectricAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElectricAdapter.ItemHolder ItemHolder, int i) {
        ElectricalModel electricalModel = list.get(i);
        ItemHolder.bindData(electricalModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ElectricalModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ElectricalModel item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) ||
                            item.getCreated().toLowerCase().contains(filterPattern) ||
                            item.getElectricalAccountId().toString().contains(filterPattern) ||
                            item.getType().toLowerCase().contains(filterPattern)) {
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

    public class ItemHolder extends RecyclerView.ViewHolder {

        ExpensesItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = ExpensesItemBinding.bind(itemView);
        }
        public void bindData(final ElectricalModel electricalModel) {
            binding.setCounterNumber(electricalModel.getName());
            binding.setFlatNumber(String.valueOf(electricalModel.getDeviceNum()));
            binding.counterCodeText.setText(String.valueOf(electricalModel.getAccountNum()));
            binding.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(electricalModel);
                }
            });
        }
    }

    public interface onClick{
        void onClickListener(ElectricalModel item);
    }

}

