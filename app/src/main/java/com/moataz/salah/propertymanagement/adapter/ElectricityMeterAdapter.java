package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.ElectriticalMeterItemBinding;
import java.util.List;

public class ElectricityMeterAdapter extends RecyclerView.Adapter<ElectricityMeterAdapter.ItemHolder> {
    private Context context;
    private List<String> list;
    onClick onClick ;

    public void setOnClick(onClick onClick) {
        this.onClick = onClick;
    }

    public ElectricityMeterAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ElectricityMeterAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.electritical_meter_item, viewGroup, false);

        return new ElectricityMeterAdapter.ItemHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ElectricityMeterAdapter.ItemHolder ItemHolder, int i) {
        String electricalModel = list.get(i);
        ItemHolder.bindData(electricalModel);
        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        ElectriticalMeterItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = ElectriticalMeterItemBinding.bind(itemView);
        }
    public void bindData(final String item) {
            binding.nameText.setText(item);
            binding.setAccount("232323422342");
        binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickListener(item);
            }
        });
    }
}

public interface onClick{
    void onClickListener(String item);
}

}
