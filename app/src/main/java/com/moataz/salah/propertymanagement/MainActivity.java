package com.moataz.salah.propertymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.moataz.salah.propertymanagement.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private BottomNavigationView bottom_nav;
    TextView toolbar_title;
    ImageView logo;
    private DrawerLayout drawerLayout;
    private NavigationView nav;
    private ActionBarDrawerToggle mDrawerToggle;
    NavController navController  = null ;
    ActivityMainBinding binding;
    String admin = "";
    UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getBoolean(R.bool.only_landscape)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        binding =
                DataBindingUtil.setContentView(this,R.layout.activity_main);
        fullScreen();
//        String currentLang = Locale.getDefault().getLanguage();
//        Log.e("local" , currentLang);
        userPreference = new UserPreference(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        userPreference = new UserPreference(this);
//        admin = userPreference.getUser().getAdmin();
//        Log.e("admin" , admin);
        initSideMenu();
        itemMenu();



        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setVisibility(View.GONE);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        logo = findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.ic_icon_menu);

        bottom_nav = findViewById(R.id.bottom_nav);
        bottom_nav.setVisibility(View.VISIBLE);
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        navController.navigate(R.id.nav_home_fragment);
                        toolbar_title.setText(navController.getCurrentDestination().getLabel());
                        break;
                    case R.id.action_ticket:
                        navController.navigate(R.id.nav_general_expenses_fragment);
                        break;
//                    case R.id.action_offer:
//                        navController.navigate(R.id.nav_offer_fragment);
//                        break;
//                    case R.id.action_favorite:
//                        break;
//                    case R.id.action_cart:
//                        break;
                }
                return true;
            }
        });
    }

    private void initSideMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.app_name, R.string.app_name);

//        mDrawerToggle.setDrawerIndicatorEnabled(false);

        mDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //get header view
        View headerView = nav.getHeaderView(0);
        String baseUrl = "https://login-system-users-bucket.s3.amazonaws.com/images";
//        TextView textView_name = headerView.findViewById(R.id.textView_name);
//        textView_name.setText(userPreference.getUser().getUserName());
        CircleImageView profile_image = headerView.findViewById(R.id.productIV);
        if (userPreference.getUser() != null) {
            if (userPreference.getUser().getImage() == null || userPreference.getUser().getImage().equals("default") ||
                    userPreference.getUser().getImage().equals("")) {
                profile_image.setImageResource(R.drawable.default_user_pic);
            } else {
                Picasso.get().load(baseUrl + userPreference.getUser().getImage() + "_S.png")
                        .into(profile_image);
            }
        }

    }

    public void itemMenu() {
        nav = findViewById(R.id.nav_view);
//        nav.setupWithNavController(findNavController(R.id.nav_host_fragment))
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_counters:
                            navController.navigate(R.id.nav_counters_fragment);
                        break;
                    case R.id.nav_reserved_apartments :{
                        Bundle b = new Bundle();
                        b.putString("key" , "add");
                        navController.navigate(R.id.nav_reserved_apartments_fragment , b);
                        break; }
                    case R.id.nav_vacant_apartments :{
                            navController.navigate(R.id.nav_vacant_fragment);
                        break; }
                    case R.id.nav_printer:{
                            navController.navigate(R.id.nav_printer_fragment);
                        break;}
                    case R.id.nav_expenses_appointments:{
                            navController.navigate(R.id.nav_general_expenses_fragment);
                        break; }
                    case R.id.nav_employees_apartments :{
                        navController.navigate(R.id.nav_employees_fragment);
                        break; }
                    case R.id.nav_cleaning_appointments :{
                            navController.navigate(R.id.nav_customer_fragment);
                        break; }
                    case R.id.nav_about_us :{
                            navController.navigate(R.id.nav_products_fragment);
                        break; }
                    case R.id.nav_sign_out :{
                        navController.navigate(R.id.nav_login_fragment);
                        break; }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public void fullScreen(){
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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