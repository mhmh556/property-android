package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.PriceItemBinding;
import com.moataz.salah.propertymanagement.databinding.PrinterItemBinding;
import com.moataz.salah.propertymanagement.model.printer.PrinterModel;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;

import java.util.List;

public class PrinterAdapter extends RecyclerView.Adapter<PrinterAdapter.ItemHolder> {
    Context context;
    List<PrinterModel> list;
    onClick onClick ;

    public void setOnClick(PrinterAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public PrinterAdapter(Context context, List<PrinterModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PrinterAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.printer_item, viewGroup, false);

        return new PrinterAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrinterAdapter.ItemHolder ItemHolder, int i) {
        PrinterModel printerModel = list.get(i);
        ItemHolder.bindData(printerModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        PrinterItemBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = PrinterItemBinding.bind(itemView);
        }
        public void bindData(final PrinterModel printerModel) {
            binding.printerName.setText(printerModel.getName());

            if (printerModel.getType().equals("usb")){
                binding.printerType.setImageResource(R.drawable.ic_usb);
            }else if (printerModel.getType().equals("wifi")){
                binding.printerType.setImageResource(R.drawable.ic_wifi);
            }else if (printerModel.getType().equals("bluetooth")){
                binding.printerType.setImageResource(R.drawable.ic_bluetooth);
            }

            binding.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(printerModel);
                }
            });
        }
    }

    public interface onClick{
        void onClickListener(PrinterModel printerModel);
    }

}

