package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.CustomerItemBinding;
import com.moataz.salah.propertymanagement.model.taskJob.TaskJobModel;
import java.util.List;

public class TaskJobAdapter extends RecyclerView.Adapter<TaskJobAdapter.ItemHolder> {
    Context context;
    List<TaskJobModel> list;
    onClick onClick ;

    public void setOnClick(TaskJobAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public TaskJobAdapter(Context context, List<TaskJobModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TaskJobAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.customer_item, viewGroup, false);

        return new TaskJobAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskJobAdapter.ItemHolder ItemHolder, int i) {
        TaskJobModel taskJobModel = list.get(i);
        ItemHolder.bindData(taskJobModel);
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
        public void bindData(final TaskJobModel taskJobModel) {
            binding.textName.setText(taskJobModel.getTaskJobName());
            binding.textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(taskJobModel);
                }
            });
        }
    }

    public interface onClick{
        void onClickListener(TaskJobModel item);
    }

}

