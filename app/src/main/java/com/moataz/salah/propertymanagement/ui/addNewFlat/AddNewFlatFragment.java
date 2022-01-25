package com.moataz.salah.propertymanagement.ui.addNewFlat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.MediaLoader;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.AddNewFlatFragmentBinding;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class AddNewFlatFragment extends Fragment {

    private String TAG = "HomeFragment";

    AddNewFlatModelFactory factory;
    AddNewFlatViewModel homeViewModel;
    AddNewFlatFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    List<AptTypeModel> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    Dialog dialog2 ;
    UserPreference userPreference;
    String token ;
    String api_key ;
    String key ;
    String type ;
    PropertyModel propertyModel ;
    String image_body = "" ;
    String path;
    String file_name ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddNewFlatFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        logo = getActivity().findViewById(R.id.logo);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new AddNewFlatModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(AddNewFlatViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        bottom_nav.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        toolbar_title.setLayoutParams(params);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_back);
        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(null);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.ddd));
        api_key = userPreference.getApiKey();
        token = userPreference.getToken();
        Log.e("token" , token);

        Bundle b  = getArguments();
        if (b == null){
            return;
        }else {
            key = (String) b.get("key");
            if (key.equals("new")){
                type = b.getString("type");
                binding.photoId.setImageResource(R.drawable.ic_image);
                binding.photoId.setScaleType(ImageView.ScaleType.CENTER);
                binding.spinner.setText("");
                binding.apartmentNumberInput.setText("");
                binding.apartmentAreaInput.setText("");
                binding.addBt.setText(R.string.add);
            }else if (key.equals("update")){
                String baseUrl = "https://login-system-users-bucket.s3.amazonaws.com/images";
                type = "" ;
                propertyModel = (PropertyModel) b.getSerializable("data");
                binding.photoId.setImageResource(R.drawable.pic);
                binding.photoId.setScaleType(ImageView.ScaleType.FIT_XY);
                binding.spinner.setText("شقة");
                binding.floorNumberInput.setText(String.valueOf(propertyModel.getNumFloor()));
                binding.kitchenNumberInput.setText(String.valueOf(propertyModel.getNumLivingRoom()));
                binding.roomsNumberInput.setText(String.valueOf(propertyModel.getNumRooms()));
                binding.bathNumberInput.setText(String.valueOf(propertyModel.getNumBath()));
                binding.apartmentNumberInput.setText(String.valueOf(propertyModel.getAptNum()));
                binding.apartmentAreaInput.setText(propertyModel.getAptArea());
                binding.addBt.setText(R.string.update);
                if (propertyModel.getImage() == null || propertyModel.getImage().equals("default") ||
                        propertyModel.getImage().equals("")){
                    binding.photoId.setImageResource(R.drawable.ic_image);
                }else {
                    Picasso.get().load(baseUrl + propertyModel.getImage() + "_S.png")
                            .into(binding.photoId);
                }
            }
        }

        binding.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getAptType();
            }
        });

        binding.spinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getCounter();
            }
        });

        binding.photoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key.equals("new")) {
                    homeViewModel.initImage();
                }else if (key.equals("update")){
                    homeViewModel.updateImage(propertyModel);
                }
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,list,currentLang , token , api_key);
        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key.equals("new")){
                    homeViewModel.addNewFlat(type);
                }else if (key.equals("update")){
                    homeViewModel.updateFlat(propertyModel.getPropertyId());
                }
            }
        });
    }

    public void showAddDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button add = dialog.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showAddNewReserveTypeDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.new_reserve_type_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button add = dialog.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDeleteDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button delete = dialog.findViewById(R.id.delete_bt);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button cancel = dialog.findViewById(R.id.cancel_bt);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
            getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.ddd));
        }
    }

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

    public void initImage() {
        Album.initialize(AlbumConfig.newBuilder(getContext())
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.image(getContext()) // Image selection.
                .singleChoice()
                .camera(true)
                .columnCount(3)
                .onResult(result -> {
                    path = result.get(0).getPath();
                    File file = new File(path);
                    file_name = file.getName();
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    image_body = Base64.encodeToString(b , Base64.NO_WRAP);
                    Log.e("image" , "data:image/png;base64," + image_body);
                    Log.e("path" , path);
                    Log.e("file_name" , file_name);
                    onLoadImageFromUrl(binding.photoId, path, getContext());
                })
                .onCancel(result -> {
                })
                .start();
    }

    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
    }
}