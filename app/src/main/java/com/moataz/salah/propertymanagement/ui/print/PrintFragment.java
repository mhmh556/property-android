package com.moataz.salah.propertymanagement.ui.print;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ReservedFlatAdapter;
import com.moataz.salah.propertymanagement.databinding.PrintFragmentBinding;
import com.moataz.salah.propertymanagement.model.printer.PrinterModel;
import com.moataz.salah.propertymanagement.model.reserve.ReservationModel;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterPos80;
import net.posprinter.utils.PosPrinterDev;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.BIND_AUTO_CREATE;

public class PrintFragment extends Fragment {

    public static String DISCONNECT="com.posconsend.net.disconnetct";
    //IMyBinder interface，All methods that can be invoked to connect and send data are encapsulated within this interface
    public static IMyBinder binder;
    //bindService connection
    ServiceConnection conn= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //Bind successfully
            binder = (IMyBinder) iBinder;
            Log.e("binder", "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("disbinder","disconnected");
        }
    };
    public static boolean ISCONNECT;
    private ListView lv_usb;
    private TextView tv_usb;
    Receiver netReciever;
    MultiFormatWriter multiFormatWriter;
    Bitmap pic;
    View dialogView3;
    private List<String> usbList , usblist;
    private ArrayAdapter<String> adapter3;//usb adapter
    String path = "";
    View view2;

    PrintFragmentModelFactory factory;
    PrintViewModel homeViewModel;
    PrintFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo , info_bt;
    TextView toolbar_title;
    Toolbar toolbar;
    List<ReservationModel> list ;
    String currentLang = Locale.getDefault().getLanguage();
    String token;
    UserPreference userPreference ;
    List<Integer> itemList ;
    String api_key ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = PrintFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        logo = getActivity().findViewById(R.id.logo);
        info_bt = getActivity().findViewById(R.id.info_bt);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new PrintFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(PrintViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        toolbar_title.setVisibility(View.GONE);
        info_bt.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        bottom_nav.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(null);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

        token = userPreference.getToken();
        api_key = userPreference.getApiKey() ;
        PrinterModel printerModel = userPreference.loadPrinter();
        if (printerModel != null) {
            if (printerModel.getPath().equals("")) {
                setUSB();
            } else {
                connetUSB(printerModel.getPath());
            }
        }else {

            //bind service，get ImyBinder object
            Intent intent = new Intent(getActivity(), PosprinterService.class);
            getActivity().bindService(intent, conn, BIND_AUTO_CREATE);

            netReciever = new Receiver();
            getActivity().registerReceiver(netReciever, new IntentFilter(DISCONNECT));
        }
        multiFormatWriter = new MultiFormatWriter();

        list = new ArrayList<>();

        itemList = new ArrayList<>();
        itemList.add(1);
        itemList.add(2);
        itemList.add(3);
        itemList.add(4);
        itemList.add(5);
        itemList.add(6);
        itemList.add(7);
        itemList.add(8);

        ReservedFlatAdapter adapter = new ReservedFlatAdapter(getContext() , list);
        binding.itemList.setLayoutManager(new GridLayoutManager(getContext() , 1));
        binding.itemList.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        binding.userName.setText(userPreference.getUser().getUserFullName());

        homeViewModel.intUi(binding , getContext() , navController , list , currentLang , token , api_key);

        binding.printBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic = loadBitmapFromView(binding.printView);
                b1 = convertGreyImg(pic);
                if (ISCONNECT) {
                    try {
                        Message message = new Message();
                        message.what = 1;
                        handler.handleMessage(message);
//                    compress the bitmap
                        Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
                        Tiny.getInstance().source(b1).asBitmap().withOptions(options).compress(new BitmapCallback() {
                            @Override
                            public void callback(boolean isSuccess, Bitmap bitmap) {
                                if (isSuccess) {
                                    b2 = bitmap;
                                    Message message = new Message();
                                    message.what = 2;
                                    handler.handleMessage(message);
                                }
                            }
                        });
                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.e("exception", e.toString());
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    setUSB();
//                    addPrinterDialog();
                }
            }
        });

        Tiny.getInstance().init(getActivity().getApplication());
    }

    public void fullScreen(){
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(getActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(getActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    String usbAdrresss;
    private void connetUSB(String name) {
//        usbAdrresss=printer_name;

        if (name == null || name.equals("")){
            Toast.makeText(getContext(), "select usb", Toast.LENGTH_SHORT).show();
            Log.e("usbAddress" , "name");
        }else {
            Log.e("not null", "not null");
            if (binder != null) {
                binder.connectUsbPort(getContext().getApplicationContext(), name, new UiExecute() {
                    @Override
                    public void onsucess() {
                        ISCONNECT = true;
                        binding.printBt.setEnabled(true);
                        Toast.makeText(getContext(), "connected success", Toast.LENGTH_SHORT).show();
                        setPortType(PosPrinterDev.PortType.USB);
                    }

                    @Override
                    public void onfailed() {
                        ISCONNECT = false;
                        binding.printBt.setEnabled(false);
                        connetUSB(name);
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(getContext(), "binder is null", Toast.LENGTH_SHORT).show();
                Log.e("binder null", "null");
            }
        }
    }

    private void setUSB(){
        LayoutInflater inflater=LayoutInflater.from(getContext());
        dialogView3=inflater.inflate(R.layout.usb_link,null);
        tv_usb= (TextView) dialogView3.findViewById(R.id.textView1);
        lv_usb= (ListView) dialogView3.findViewById(R.id.listView1);


        usbList= PosPrinterDev.GetUsbPathNames(getContext());
        if (usbList==null){
            usbList=new ArrayList<>();
        }
        usblist=usbList;
        tv_usb.setText("usb : "+usbList.size());
        adapter3=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,usbList);
        lv_usb.setAdapter(adapter3);

        AlertDialog dialog=new AlertDialog.Builder(getContext())
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
//                printer_path = usbDev;
                path = usbDev;
                connetUSB(usbDev);
//                PrinterItem item = new PrinterItem("printer" , usbDev , 1);
//                preferences.addPrinterItem(item);
//                preferences.addPrinter(usbDev);
                dialog.dismiss();
                Log.e("usbDev: ",usbDev);
            }
        });
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(getContext(), "message 1", Toast.LENGTH_SHORT).show();
//                    setUSB();
                    break;
                case 2:
                    //usb connection need special deal with
                    if (PosPrinterDev.PortType.USB!=portType){
                        b2=resizeImage(b2,576,false);
                        printUSBbitamp(b2);
//                        showSnackbar("no portType");
//                        printpicCode(b2);
                    }else {
                        Toast.makeText(getContext(), "bimap  "+b2.getWidth()+"  height: "+b2.getHeight(), Toast.LENGTH_SHORT).show();
                        b2=resizeImage(b2,576,false);
                        printUSBbitamp(b2);
                    }
                    break;
                case 3://disconnect
                    Toast.makeText(getContext(), "message 3", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getContext(), "message 4", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth() , v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private Bitmap b1;//grey-scale bitmap
    private  Bitmap b2;//compress bitmap
    private void printpicCode(final Bitmap printBmp){


        binder.writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {

            }

            @Override
            public void onfailed() {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                List<byte[]> list=new ArrayList<byte[]>();
                list.add(DataForSendToPrinterPos80.initializePrinter());
                list.add(DataForSendToPrinterPos80.printRasterBmp(
                        0,printBmp, BitmapToByteData.BmpType.Threshold, BitmapToByteData.AlignType.Left,576));
//                list.add(DataForSendToPrinterPos80.printAndFeedForward(3));
                list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66,1));
                return list;
            }
        });

    }

    /**
     convert grey image
     * @param img  bitmap
     * @return  data
     */
    public Bitmap convertGreyImg(Bitmap img) {

        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = new int[width * height];
        img.getPixels(pixels, 0, width, 0, 0, width, height);
        //The arithmetic average of a grayscale image; a threshold
        double redSum=0,greenSum=0,blueSun=0;
        double total=width*height;

        for(int i = 0; i < height; i++)  {
            for(int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                int red = ((grey  & 0x00FF0000 ) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                redSum+=red;
                greenSum+=green;
                blueSun+=blue;
            }
        }
        int m=(int) (redSum/total);
        //Conversion monochrome diagram
        for(int i = 0; i < height; i++)  {
            for(int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                int alpha1 = 0xFF << 24;
                int red = ((grey  & 0x00FF0000 ) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                if (red>=m) {
                    red=green=blue=255;
                }else{
                    red=green=blue=0;
                }
                grey = alpha1 | (red << 16) | (green << 8) | blue;
                pixels[width*i+j]=grey;
            }
        }
        Bitmap mBitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return mBitmap;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int w,boolean ischecked) {

        Bitmap BitmapOrg = bitmap;
        Bitmap resizedBitmap = null;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        if (width<=w) {
            return bitmap;
        }
        if (!ischecked) {
            int newWidth = w;
            int newHeight = height*w/width;

            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // if you want to rotate the Bitmap
            // matrix.postRotate(45);
            resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                    height, matrix, true);
        }else {
            resizedBitmap=Bitmap.createBitmap(BitmapOrg, 0, 0, w, height);
        }

        return resizedBitmap;
    }

    private void printUSBbitamp(final Bitmap printBmp){

        int height=printBmp.getHeight();
        // if height > 200 cut the bitmap
        if (height>200){

            binder.writeDataByYouself(new UiExecute() {
                @Override
                public void onsucess() {

                }

                @Override
                public void onfailed() {

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list=new ArrayList<byte[]>();
                    list.add(DataForSendToPrinterPos80.initializePrinter());
                    List<Bitmap> bitmaplist=new ArrayList<>();
                    bitmaplist=cutBitmap(200,printBmp);//cut bitmap
                    if(bitmaplist.size()!=0){
                        for (int i=0;i<bitmaplist.size();i++){
                            list.add(DataForSendToPrinterPos80.printRasterBmp(0,bitmaplist.get(i),
                                    BitmapToByteData.BmpType.Threshold,BitmapToByteData.AlignType.Center,576));
                        }
                    }
                    list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66,1));
                    return list;
                }
            });
        }else {
            binder.writeDataByYouself(new UiExecute() {
                @Override
                public void onsucess() {

                }

                @Override
                public void onfailed() {

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list=new ArrayList<byte[]>();
                    list.add(DataForSendToPrinterPos80.initializePrinter());
                    list.add(DataForSendToPrinterPos80.printRasterBmp(
                            0,printBmp, BitmapToByteData.BmpType.Threshold,
                            BitmapToByteData.AlignType.Center,576));
                    list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66,1));
                    return list;
                }
            });
        }

    }

    private List<Bitmap> cutBitmap(int h, Bitmap bitmap){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        boolean full=height%h==0;
        int n=height%h==0?height/h:(height/h)+1;
        Bitmap b;
        List<Bitmap> bitmaps=new ArrayList<>();
        for (int i=0;i<n;i++){
            if (full){
                b=Bitmap.createBitmap(bitmap,0,i*h,width,h);
            }else {
                if (i==n-1){
                    b=Bitmap.createBitmap(bitmap,0,i*h,width,height-i*h);
                }else {
                    b=Bitmap.createBitmap(bitmap,0,i*h,width,h);
                }
            }

            bitmaps.add(b);
        }

        return bitmaps;
    }

    public static PosPrinterDev.PortType portType;//connect type
    private void setPortType(PosPrinterDev.PortType portType){
        PrintFragment.portType =portType;
    }


    private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(DISCONNECT)){
                Message message=new Message();
                message.what=4;
                handler.handleMessage(message);
            }
        }
    }

    public void addPrinterDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView error = dialog.findViewById(R.id.message);
        error.setText("الرجاء اضافه الطابعة واحدة على الاقل");
        Button ok = dialog.findViewById(R.id.ok_bt);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SalePrint.this,PrinterActivity.class);
//                intent.putExtra("add_printer" , "sale");
//                startActivityForResult(intent,900);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}