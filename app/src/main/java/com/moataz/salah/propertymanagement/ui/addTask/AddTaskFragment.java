package com.moataz.salah.propertymanagement.ui.addTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.AddNewFlatFragmentBinding;
import com.moataz.salah.propertymanagement.databinding.AddTaskFragmentBinding;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.task.TaskModel;
import com.moataz.salah.propertymanagement.model.taskJob.TaskJobModel;

import java.util.List;
import java.util.Locale;

public class AddTaskFragment extends Fragment {

    private String TAG = "HomeFragment";

    AddTaskModelFactory factory;
    AddTaskViewModel homeViewModel;
    AddTaskFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    List<TaskJobModel> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    Dialog dialog2 ;
    UserPreference userPreference;
    String token ;
    String api_key ;
    String key ;
    PropertyModel propertyModel ;
    TaskModel taskModel ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddTaskFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        logo = getActivity().findViewById(R.id.logo);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new AddTaskModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(AddTaskViewModel.class);
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
        token = userPreference.getUser().getAccessToken();
        Log.e("token" , token);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_task_fragment);
            }
        });

        Bundle b  = getArguments();
        if (b == null){
            return;
        }else {
            key = (String) b.get("action");
            if (key.equals("add")){
                toolbar_title.setText(R.string.add_task);
                binding.addBt.setText(R.string.add);
            }else if (key.equals("update")){
                toolbar_title.setText(R.string.update_task);
                binding.addBt.setText(R.string.update);
                taskModel = (TaskModel) b.getSerializable("data");
                if (taskModel != null){
                    binding.administratorSpinner.setText(taskModel.getUserFullName());
                    binding.taskSpinner.setText(taskModel.getTaskJobName());
                    binding.durationInput.setText(taskModel.getDuration());
                    binding.notesInput.setText(taskModel.getNote()); }
            }
        }

        binding.administratorSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.showUserDialog(taskModel);
            }
        });

        binding.taskSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getTasks();
            }
        });

        binding.dateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    pickDate(binding.dateInput);
                }else {
                    binding.dateInput.setFocusable(true);
                }
            }
        });

        binding.timeInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    pickTime(binding.timeInput);
                }else {
                    binding.timeInput.setFocusable(true);
                }
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,list,currentLang , userPreference);
        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key.equals("add")){
                    homeViewModel.addNewTask();
                }else if (key.equals("update")){
                    homeViewModel.updateTask(propertyModel.getPropertyId());
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

    public void pickDate(EditText date_text){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.calender_dialog);
        dialog.setCancelable(true);
        CalendarView calendarView = dialog.findViewById(R.id.calander_view);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String day = String.valueOf(dayOfMonth);
            String month_text = String.valueOf(month+1);
            String year_text = String.valueOf(year);
            date_text.setText(year_text +"-"+ month_text +"-"+ day);
            dialog.dismiss();
            date_text.setFocusable(true);
        });
        dialog.show();
    }


    public void pickTime(EditText time_text){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.time_dialog);
        dialog.setCancelable(true);
        String hour = "00" ;
        String mint = "00" ;
        TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            }
        });

        Button okBt = dialog.findViewById(R.id.ok_bt);
        okBt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                time_text.setText(timePicker.getHour() + ":" + timePicker.getMinute());
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
//        timePicker.setOnTimeChangedListener((view, year, month, dayOfMonth) -> {
//            String day = String.valueOf(dayOfMonth);
//            String month_text = String.valueOf(month+1);
//            String year_text = String.valueOf(year);
//            date_text.setText(year_text +"-"+ month_text +"-"+ day);
//            dialog.dismiss();
//            date_text.setFocusable(true);
//        });
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
}