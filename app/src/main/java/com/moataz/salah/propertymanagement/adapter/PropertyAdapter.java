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
import com.moataz.salah.propertymanagement.databinding.VacantApartmentItemBinding;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ItemHolder> implements Filterable {
    Context context;
    List<PropertyModel> list;
    private List<PropertyModel> exampleListFull;
    onClick onClick ;

    public void setOnClick(PropertyAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public PropertyAdapter(Context context, List<PropertyModel> list) {
        this.context = context;
        this.list = list;
        exampleListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public PropertyAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.vacant_apartment_item, viewGroup, false);

        return new PropertyAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyAdapter.ItemHolder ItemHolder, int i) {
        PropertyModel propertyModel = list.get(i);
        ItemHolder.bindData(propertyModel);
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
        public void bindData(final PropertyModel propertyModel) {
            String baseUrl = "https://login-system-users-bucket.s3.amazonaws.com/images";
            binding.n1.setText(String.valueOf(propertyModel.getNumBath()));
            binding.n2.setText(String.valueOf(propertyModel.getNumRooms()));
            binding.n3.setText(String.valueOf(propertyModel.getNumLivingRoom()));
            binding.setFlatNumber(propertyModel.getAptNum());
            if (propertyModel.getImage() == null || propertyModel.getImage().equals("default") ||
                    propertyModel.getImage().equals("")){
                binding.apartmentPic.setImageResource(R.drawable.ic_image);
            }else {
                Picasso.get().load(baseUrl + propertyModel.getImage() + "_S.png")
                        .into(binding.apartmentPic);
            }
            binding.apartmentPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(propertyModel);
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
            List<PropertyModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PropertyModel item : exampleListFull) {
                    if (item.getAptNum().toLowerCase().contains(filterPattern)) {
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

    public interface onClick{
        void onClickListener(PropertyModel propertyModel);
    }
}