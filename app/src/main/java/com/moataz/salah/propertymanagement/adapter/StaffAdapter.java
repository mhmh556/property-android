package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.StaffItemBinding;
import com.moataz.salah.propertymanagement.model.staff.StaffModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ItemHolder> implements Filterable {
    private Context context;
    private List<StaffModel> list;
    private List<StaffModel> exampleListFull;
    onItemClick onItemClick;
    onDeleteClick onDeleteClick;
    onEditClick onEditClick;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public StaffAdapter(Context context, List<StaffModel> list) {
        this.context = context;
        this.list = list;
        exampleListFull = new ArrayList<>(list);
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
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.staff_item, viewGroup, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder ItemHolder, int i) {
        StaffModel staffModel = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(staffModel.getUserId()));
        viewBinderHelper.closeLayout(String.valueOf(staffModel.getUserId()));
        ItemHolder.bindData(staffModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        StaffItemBinding binding ;
        UserPreference userPreference ;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = StaffItemBinding.bind(itemView);
            userPreference = new UserPreference(context);
        }

        public void bindData(final StaffModel staffModel) {
            String baseUrl = "https://login-system-users-bucket.s3.amazonaws.com/images";
            binding.name.setText(String.valueOf(staffModel.getUserName()));
            binding.jobName.setText(staffModel.getUserJob());
//            Log.e("pic" , staffModel.getUserImage());
            if (staffModel.getUserImage() == null || staffModel.getUserImage().equals("default") ||
                staffModel.getUserImage().equals("")){
                binding.itemImage.setImageResource(R.drawable.default_user_pic);
            }else {
                Picasso.get().load(baseUrl + staffModel.getUserImage() + "_S.png")
                        .into(binding.itemImage);
            }

            binding.itemCountainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClickListener(staffModel);
                }
            });

            binding.txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onEditClickListener(staffModel);
                }
            });

            binding.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick.onDeleteClickListener(staffModel);
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
            List<StaffModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StaffModel item : exampleListFull) {
                    if (item.getUserId().toString().toLowerCase().contains(filterPattern) ||
                        item.getUserName().toLowerCase().contains(filterPattern) ||
                        item.getUserFullName().toLowerCase().contains(filterPattern)) {
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

    public interface onItemClick{
        void onClickListener(StaffModel item);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(StaffModel item);
    }

    public interface onEditClick{
        void onEditClickListener(StaffModel item);
    }
}
