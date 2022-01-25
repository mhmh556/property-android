package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.FlatItemBinding;
import com.moataz.salah.propertymanagement.model.FlatModel;
import java.util.List;

public class FlatsAdapter extends RecyclerView.Adapter<FlatsAdapter.ItemHolder> {
    Context context;
    List<FlatModel> list;
    onClick onClick ;
    int x = 0 ;

    public void setOnClick(FlatsAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public FlatsAdapter(Context context, List<FlatModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FlatsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.flat_item, viewGroup, false);

        return new FlatsAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlatsAdapter.ItemHolder ItemHolder, int i) {
        FlatModel flatModel = list.get(i);

        if (flatModel.getActive()){
            ItemHolder.binding.radioButton.setChecked(false);
            ItemHolder.binding.flatName.setTextColor(context.getResources().getColor(R.color.gray));
        } else {
            ItemHolder.binding.radioButton.setChecked(true);
            ItemHolder.binding.flatName.setTextColor(context.getResources().getColor(R.color.ddd));
        }

        ItemHolder.bindData(flatModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        FlatItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = FlatItemBinding.bind(itemView);
        }
        public void bindData(final FlatModel flatModel) {
            binding.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flatModel.getActive()){
                        flatModel.setActive(false);
                        notifyDataSetChanged();
                    }else if (!flatModel.getActive()){
                        flatModel.setActive(true);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public interface onClick{
        void onClickListener();
    }

}

