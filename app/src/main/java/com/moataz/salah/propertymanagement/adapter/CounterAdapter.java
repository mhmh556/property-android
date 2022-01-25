package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.CounterItemBinding;
import com.moataz.salah.propertymanagement.model.bill.BillModel;
import java.util.List;

public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.ItemHolder> {
    Context context;
    List<BillModel> list;
    onClick onClick ;
    onDeleteClick onDeleteClick;
    onEditClick onEditClick;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public void setOnClick(onClick onClick) {
        this.onClick = onClick;
    }

    public void setOnDeleteClick(onDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public void setOnEditClick(onEditClick onEditClick) {
        this.onEditClick = onEditClick;
    }

    public CounterAdapter(Context context, List<BillModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CounterAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.counter_item, viewGroup, false);

        return new CounterAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CounterAdapter.ItemHolder ItemHolder, int i) {
        BillModel billModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(billModel.getElecticalBillId()));
        viewBinderHelper.closeLayout(String.valueOf(billModel.getElecticalBillId()));
        ItemHolder.bindData(billModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        CounterItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = CounterItemBinding.bind(itemView);
        }
        public void bindData(final BillModel billModel) {
            binding.accountText.setText(String.valueOf(billModel.getElecticalBillId()));
            binding.coastText.setText(String.valueOf(billModel.getTotalPrice()));
            binding.flatText.setText(String.valueOf(billModel.getElectricalAccountId()));
            binding.counterText.setText(String.valueOf(billModel.getConsumption()));

            binding.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick.onDeleteClickListener(billModel);
                }
            });

            binding.txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onEditClickListener(billModel);
                }
            });
        }
    }

    public interface onClick{
        void onClickListener(BillModel item);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(BillModel item);
    }

    public interface onEditClick{
        void onEditClickListener(BillModel item);
    }


}

