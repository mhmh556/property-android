package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.CustomerItemBinding;
import com.moataz.salah.propertymanagement.databinding.TaskItemBinding;
import com.moataz.salah.propertymanagement.databinding.TypeItemBinding;
import com.moataz.salah.propertymanagement.model.contactType.ContactTypeModel;
import com.moataz.salah.propertymanagement.model.task.TaskModel;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ItemHolder> {
    private Context context;
    private List<ContactTypeModel> list;
//    private List<UserPermissionModel> exampleListFull;
    onItemClick onItemClick;
    onDeleteClick onDeleteClick;
    onEditClick onEditClick;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public TypeAdapter(Context context, List<ContactTypeModel> list) {
        this.context = context;
        this.list = list;
//        exampleListFull = new ArrayList<>(list);
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
        View view = layoutInflater.inflate(R.layout.type_item, viewGroup, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder ItemHolder, int i) {
        ContactTypeModel item = list.get(i);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(ItemHolder.binding.swipelayout, String.valueOf(item.getContactTypeId()));
        viewBinderHelper.closeLayout(String.valueOf(item.getContactTypeId()));
        ItemHolder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TypeItemBinding binding ;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = TypeItemBinding.bind(itemView);
        }

        public void bindData(final ContactTypeModel item) {
            binding.textName.setText(item.getContactTypeName());

            binding.textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClickListener(item);
                }
            });

            binding.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick.onDeleteClickListener(item);
                }
            });

            binding.txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onEditClickListener(item);
                }
            });
        }
    }

//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }
//
//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<UserPermissionModel> filteredList = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(exampleListFull);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (UserPermissionModel item : exampleListFull) {
//                    if (item.getUserAppPermissionId().toString().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            list.clear();
//            list.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };

    public interface onItemClick{
        void onClickListener(ContactTypeModel item);
    }

    public interface onDeleteClick{
        void onDeleteClickListener(ContactTypeModel item);
    }

    public interface onEditClick{
        void onEditClickListener(ContactTypeModel item);
    }
}
