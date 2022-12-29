package com.example.cby2112114536.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.example.cby2112114536.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cby2112114536.databinding.ActivityMainBinding;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author breeze
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @BindView(R.id.navigation_home) View homeView;
    @BindView(R.id.navigation_movie) View movieView;
    @BindView(R.id.navigation_chart) View chartView;
    @BindView(R.id.navigation_search) View searchView;
    @BindView(R.id.navigation_person) View personView;
    private int count;
    private final String NEXT_STRING = "next";
    private final String PREVIOUS_STRING = "previous";
    private final String SKIP_STRING = "skip";
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;
//        View homeView = findViewById(R.id.navigation_home);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_movie, R.id.navigation_chart,
                R.id.navigation_search, R.id.navigation_person)
                .build();

//        navView.getMenu().findItem(R.id.navigation_chart).setVisible(false);
        ButterKnife.bind(this);

        navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        Animation enterAnimation = new AlphaAnimation(0.3f, 1f);
        enterAnimation.setDuration(300);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0.3f);
        exitAnimation.setDuration(300);
        exitAnimation.setFillAfter(true);

        NewbieGuide.with(this).setLabel("home")
                .setOnPageChangedListener(new OnPageChangedListener() {
                    @Override
                    public void onPageChanged(int page) {
                        count = page;
                    }
                })
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                btnClick(view, R.id.homeNextPage, controller, NEXT_STRING);
                                btnClick(view, R.id.homeSkip, controller, SKIP_STRING);
                            }
                        })
                        .addHighLight(homeView)
                        .setLayoutRes(R.layout.home_guide)
                        .setEnterAnimation(enterAnimation)
                        .setExitAnimation(exitAnimation))
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                btnClick(view, R.id.movieNextPage, controller, NEXT_STRING);
                                btnClick(view, R.id.moviePreviousPage, controller, PREVIOUS_STRING);
                                btnClick(view, R.id.movieSkip, controller, SKIP_STRING);
                            }
                        })
                        .addHighLight(movieView)
                        .setLayoutRes(R.layout.movie_guide)
                        .setEnterAnimation(enterAnimation)
                        .setExitAnimation(exitAnimation))
//                .addGuidePage(GuidePage.newInstance()
//                        .setEverywhereCancelable(false)
//                        .addHighLight(chartView)
//                        .setLayoutRes(R.layout.home_guide)
//                        .setEnterAnimation(enterAnimation)
//                        .setExitAnimation(exitAnimation))
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                btnClick(view, R.id.searchNextPage, controller, NEXT_STRING);
                                btnClick(view, R.id.searchPreviousPage, controller, PREVIOUS_STRING);
                                btnClick(view, R.id.searchSkip, controller, SKIP_STRING);
                            }
                        })
                        .addHighLight(searchView)
                        .setLayoutRes(R.layout.search_guide)
                        .setEnterAnimation(enterAnimation)
                        .setExitAnimation(exitAnimation))
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                btnClick(view, R.id.personPreviousPage, controller, PREVIOUS_STRING);
                                btnClick(view, R.id.personSkip, controller, SKIP_STRING);
                            }
                        })
                        .addHighLight(personView)
                        .setLayoutRes(R.layout.person_guide)
                        .setEnterAnimation(enterAnimation)
                        .setExitAnimation(exitAnimation))
                .show();

    }

    protected void btnClick(View view, int resId, Controller controller, String btn){
        view.findViewById(resId).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (btn){
                            case NEXT_STRING:
                                controller.showPage(count + 1);
                                break;
                            case PREVIOUS_STRING:
                                if (count != 0){
                                    controller.showPage(count - 1);
                                }
                                break;
                            case SKIP_STRING:
                                controller.remove();
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }
}