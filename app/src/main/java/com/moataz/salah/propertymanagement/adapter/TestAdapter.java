package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.CounterItemBinding;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ItemHolder> {
    Context context;
    List<String> list;
    onClick onClick ;

    public void setOnClick(TestAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public TestAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TestAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.counter_item, viewGroup, false);

        return new TestAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ItemHolder ItemHolder, int i) {
        String billModel = list.get(i);
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
        public void bindData(final String billModel) {
        }
    }

    public interface onClick{
        void onClickListener(String item);
    }

}

