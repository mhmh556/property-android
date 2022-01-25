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
import com.moataz.salah.propertymanagement.model.reserve.ReservationModel;

import java.util.ArrayList;
import java.util.List;

public class ReservedApartmentsAdapter extends RecyclerView.Adapter<ReservedApartmentsAdapter.ItemHolder> implements Filterable {
    Context context;
    List<ReservationModel> list;
    private List<ReservationModel> exampleListFull;
    onClick onClick ;

    public void setOnClick(ReservedApartmentsAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public ReservedApartmentsAdapter(Context context, List<ReservationModel> list) {
        this.context = context;
        this.list = list;
        exampleListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ReservedApartmentsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.vacant_apartment_item, viewGroup, false);

        return new ReservedApartmentsAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservedApartmentsAdapter.ItemHolder ItemHolder, int i) {
        ReservationModel reservationModel = list.get(i);
        ItemHolder.bindData(reservationModel);
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
        public void bindData(final ReservationModel reservationModel) {
            binding.setFlatNumber(String.valueOf(reservationModel.getAptNum()));
            binding.statue.setText(R.string.reserved);
            binding.statue.setTextColor(context.getResources().getColor(R.color.red));
            binding.statue.setBackgroundResource(R.drawable.text_background_red);
            binding.apartmentPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(reservationModel);
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
            List<ReservationModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ReservationModel item : exampleListFull) {
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
        void onClickListener(ReservationModel reservationModel);
    }

}

