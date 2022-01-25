package com.moataz.salah.propertymanagement.ui.printer;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.MultiFormatWriter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.PrinterAdapter;
import com.moataz.salah.propertymanagement.adapter.StaffAdapter;
import com.moataz.salah.propertymanagement.databinding.PrinterFragmentBinding;
import com.moataz.salah.propertymanagement.databinding.StaffFragmentBinding;
import com.moataz.salah.propertymanagement.model.printer.PrinterModel;
import com.moataz.salah.propertymanagement.model.printer.PrinterType;
import com.moataz.salah.propertymanagement.model.staff.GetPermissionResponse;
import com.moataz.salah.propertymanagement.model.staff.StaffModel;
import com.moataz.salah.propertymanagement.model.staff.StaffResponse;
import com.moataz.salah.propertymanagement.model.staff.UserPermissionModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClientLogin;
import com.moataz.salah.propertymanagement.ui.print.PrintFragment;
import com.moataz.salah.propertymanagement.ui.reserve.ReservationViewModel;
import com.moataz.salah.propertymanagement.util.Conts;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.PosPrinterDev;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrinterViewModel extends ViewModel implements PrinterAdapter.onClick{

    PrinterFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference;
    List<PrinterModel> list ;
    UserPermissionModel userPermissionModel ;
    String printer_type ;
    PrinterModel printerModel;
    String printer_name;
    String page_size;

    View dialogView3;
    private ListView lv_usb;
    private TextView tv_usb;
    String path = "";
    List<String> list2 = new ArrayList<>();
    List<String> usbList, usblist;
    private ArrayAdapter<String> adapter3;
    public static boolean ISCONNECT;
    public static String DISCONNECT = "com.posconsend.net.disconnetct";

    public static IMyBinder binder;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //Bind successfully
            binder = (IMyBinder) iBinder;
            Log.e("binder", "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("disbinder", "disconnected");
        }
    };

    public void intUi(PrinterFragmentBinding binding , Context context , NavController navController , String currentLang ,
                      List<PrinterModel> list , UserPreference preference , UserPermissionModel userPermissionModel){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.preference = preference;
        this.list = list ;
        this.userPermissionModel = userPermissionModel ;

        getData();
    }

    public void getData(){
        if (preference.readListPrinter() != null) {
            list = preference.readListPrinter();
        }else {
            list = new ArrayList<>();
        }
        PrinterAdapter adapter = new PrinterAdapter(context , list);
        binding.printerRecycler.setLayoutManager(new GridLayoutManager(context , 1));
        binding.printerRecycler.setAdapter(adapter);
        adapter.setOnClick(PrinterViewModel.this);
        adapter.notifyDataSetChanged();
    }

    public void addPrinter(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_printer_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialSpinner spinner = dialog.findViewById(R.id.administrator_spinner);

        list2.add("Ethernet");
        list2.add("Bluetooth");
        list2.add("USB");

        ArrayAdapter<String> aa = new ArrayAdapter<String>(context, R.layout.custom_spinner_layout, R.id.mmmm, list2);
        aa.setDropDownViewResource(R.layout.custom_spinner_layout);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener((view, position, id, item) -> {
            String name = (String) view.getItems().get(position);
            switch (name) {
                case "USB":
                    printer_type = "usb";
                    break;
                case "Bluetooth":
                    printer_type = "bluetooth";
                    break;
                case "Ethernet":
                    printer_type = "wifi";
                    break;
            }
        });

        RadioButton A4 = dialog.findViewById(R.id.a4_check);
        RadioButton M80 = dialog.findViewById(R.id.m80_check);

        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (A4.isChecked()){
                    M80.setChecked(false);
                    page_size = "A4" ;
                    A4.setChecked(true);
                }else {
                    A4.setChecked(false);
                    M80.setChecked(true);
                    page_size = "M80";
                }
            }
        });

        M80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (M80.isChecked()){
                    M80.setChecked(true);
                    page_size = "M80" ;
                    A4.setChecked(false);
                }else {
                    A4.setChecked(true);
                    M80.setChecked(false);
                    page_size = "A4";
                }
            }
        });

        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText name_input = dialog.findViewById(R.id.printer_name_input);

        Button search = dialog.findViewById(R.id.search_bt);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUSB();
            }
        });

        Button ok = dialog.findViewById(R.id.add_bt);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationInput(name_input)){
                    return;
                }else {
                    printer_name = name_input.getText().toString();
                }
                if (path.equals("")){
                    Toast.makeText(context, "select printer", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    printerModel = new PrinterModel(printer_name, printer_type, path, page_size);
                    list.add(printerModel);
                    preference.writeListPrinter(list);
                    list = preference.readListPrinter();
                    PrinterAdapter adapter = new PrinterAdapter(context, list);
                    binding.printerRecycler.setLayoutManager(new GridLayoutManager(context, 1));
                    binding.printerRecycler.setAdapter(adapter);
                    adapter.setOnClick(PrinterViewModel.this);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void setUSB() {
        LayoutInflater inflater=LayoutInflater.from(context);
        dialogView3=inflater.inflate(R.layout.usb_link,null);
        tv_usb= (TextView) dialogView3.findViewById(R.id.textView1);
        lv_usb= (ListView) dialogView3.findViewById(R.id.listView1);

        usbList= PosPrinterDev.GetUsbPathNames(context);
        if (usbList==null){
            usbList=new ArrayList<>();
        }
        usblist=usbList;
        tv_usb.setText("usb : "+usbList.size());
        adapter3=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,usbList);
        lv_usb.setAdapter(adapter3);

        AlertDialog dialog=new AlertDialog.Builder(context)
                .setView(dialogView3).create();
        dialog.show();

        setUsbLisener(dialog);
    }

    String usbDev="";
    public void setUsbLisener(final AlertDialog dialog) {

        lv_usb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usbDev=usbList.get(i);
                path = usbDev;
                connetUSB(usbDev);
                dialog.dismiss();
                Log.e("usbDev: ",usbDev);
            }
        });
    }

    private void connetUSB(String name) {
        if (name == null || name.equals("")) {
//            showSnackbar("select USB");
            Toast.makeText(context, "select USB", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("not null", "not null");
            binder.connectUsbPort(context.getApplicationContext(), name, new UiExecute() {
                @Override
                public void onsucess() {
                    ISCONNECT = true;
//                    showSnackbar("connected success");
                    Toast.makeText(context, "connected success", Toast.LENGTH_SHORT).show();
                    setPortType(PosPrinterDev.PortType.USB);
                }

                @Override
                public void onfailed() {
                    ISCONNECT = false;
//                    showSnackbar("failed");
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static PosPrinterDev.PortType portType;//connect type

    private void setPortType(PosPrinterDev.PortType portType) {
        portType = portType;
    }

    private Boolean validationInput(EditText editText){
        String text = editText.getText().toString().trim();
        if (text.isEmpty()){
            editText.setError("Field can't be empty");
            return false;
        }else {
            editText.setError(null);
            return true;
        }
    }

    @Override
    public void onClickListener(PrinterModel printerModel) {
        preference.savePrinter(printerModel);
        Toast.makeText(context, printerModel.getName(), Toast.LENGTH_SHORT).show();
    }
}