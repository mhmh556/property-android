package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.ReservedItemBinding;
import com.moataz.salah.propertymanagement.model.property.FutureReservation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ItemHolder> {
    Context context;
    List<FutureReservation> list;
    onClick onClick ;

    public void setOnClick(DetailsAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public DetailsAdapter(Context context, List<FutureReservation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DetailsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.reserved_item, viewGroup, false);

        return new DetailsAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.ItemHolder ItemHolder, int i) {
        FutureReservation futureReservation = list.get(i);
        ItemHolder.bindData(futureReservation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        ReservedItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReservedItemBinding.bind(itemView);
        }
        public void bindData(final FutureReservation item) {
            binding.fromText.setText(item.getCheckInDate().substring(0, 10));
            binding.toText.setText(item.getCheckOutDate().substring(0, 10));
            try {
                Date date1;
                Date date2;
                SimpleDateFormat dates = new SimpleDateFormat("yyyy-mm-dd");
                date1 = dates.parse(item.getCheckInDate().substring(0, 10));
                date2 = dates.parse(item.getCheckOutDate().substring(0, 10));
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);
                String dayDifference = Long.toString(differenceDates);
                binding.daysText.setText(dayDifference);
            } catch (Exception exception) {
                Toast.makeText(context, "Unable to find difference", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface onClick{
        void onClickListener();
    }

}

