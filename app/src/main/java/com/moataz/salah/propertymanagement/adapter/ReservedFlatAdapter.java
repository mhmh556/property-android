package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.ReservedFlatItemBinding;
import com.moataz.salah.propertymanagement.model.reserve.ReservationModel;

import java.util.List;

public class ReservedFlatAdapter extends RecyclerView.Adapter<ReservedFlatAdapter.ItemHolder>{
    Context context;
    private List<ReservationModel> list;
    onItemClick onItemClick;
    onDeleteClick onDeleteClick;
    onEditClick onEditClick;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ReservedFlatAdapter(Context context, List<ReservationModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClick(onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnDeleteClick(onDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public void setOnEditClick(onEditClick onEditClick) {
        this.onEditClick = onEditClick;
    }

    @NonNull
    @Override
    public ReservedFlatAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.reserved_flat_item, viewGroup, false);

        return new ReservedFlatAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservedFlatAdapter.ItemHolder ItemHolder, int i) {
        ReservationModel reservationModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(reservationModel.getReservationId()));
        viewBinderHelper.closeLayout(String.valueOf(reservationModel.getReservationId()));
        ItemHolder.bindData(reservationModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        ReservedFlatItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReservedFlatItemBinding.bind(itemView);
        }
        public void bindData(final ReservationModel reservationModel) {
            binding.setNumber(reservationModel.getAptNum());
            binding.priceText.setText(reservationModel.getCustomerName());
            binding.dateText.setText(reservationModel.getCheckOutDate().substring(0, 10));
            binding.phoneNumber.setText(reservationModel.getCheckInDate().substring(12, 19));
            binding.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClickListener(reservationModel);
                }
            });
        }
    }

    public interface onItemClick{
        void onClickListener(ReservationModel item);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(ReservationModel item);
    }

    public interface onEditClick{
        void onEditClickListener(ReservationModel item);
    }

}

