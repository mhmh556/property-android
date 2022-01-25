package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.VacantApartmentItemBinding;
import java.util.List;

public class VacantApartmentAdapter extends RecyclerView.Adapter<VacantApartmentAdapter.ItemHolder> {
    Context context;
    List<String> list;
    onClick onClick ;

    public void setOnClick(VacantApartmentAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public VacantApartmentAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VacantApartmentAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.vacant_apartment_item, viewGroup, false);

        return new VacantApartmentAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacantApartmentAdapter.ItemHolder ItemHolder, int i) {
        String commentModel = list.get(i);
        ItemHolder.bindData(commentModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        VacantApartmentItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = VacantApartmentItemBinding.bind(itemView);
        }
        public void bindData(final String num) {
            binding.apartmentPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener();
                }
            });
        }
    }

    public interface onClick{
        void onClickListener();
    }

}

