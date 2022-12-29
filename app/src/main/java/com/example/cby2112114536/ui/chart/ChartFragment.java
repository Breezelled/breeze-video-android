package com.example.cby2112114536.ui.chart;

import static com.example.cby2112114536.common.Constants.BASE_URL;
import static com.example.cby2112114536.common.Constants.CHART_URL;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cby2112114536.R;
import com.example.cby2112114536.databinding.FragmentChartBinding;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

/**
 * @author breeze
 */
public class ChartFragment extends Fragment {

    private FragmentChartBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChartViewModel chartViewModel =
                new ViewModelProvider(this).get(ChartViewModel.class);

        binding = FragmentChartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CircleMenu circleMenu = binding.circleMenu;

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),
                        R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.icon_type)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.icon_rating)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.icon_dollar)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.icon_others)
                .addSubMenu(Color.parseColor("#FF6A00"), R.drawable.icon_prediction)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        Bundle bundle = new Bundle();
                        bundle.putString("urlType", "chart");
                        switch (index){
                            case 0:

                                bundle.putString("chartUrl", CHART_URL + "dashboard/analysis");
                                Navigation.findNavController(getView())
                                        .navigate(R.id.action_navigation_chart_to_webFragment, bundle);
                                break;
                            case 1:
                                bundle.putString("chartUrl", CHART_URL + "dashboard/rating");
                                Navigation.findNavController(getView())
                                        .navigate(R.id.action_navigation_chart_to_webFragment, bundle);
                                break;
                            case 2:
                                bundle.putString("chartUrl", CHART_URL + "dashboard/budgetRevenue");
                                Navigation.findNavController(getView())
                                        .navigate(R.id.action_navigation_chart_to_webFragment, bundle);
                                break;
                            case 3:
                                bundle.putString("chartUrl", CHART_URL + "dashboard/other");
                                Navigation.findNavController(getView())
                                        .navigate(R.id.action_navigation_chart_to_webFragment, bundle);
                                break;
                            case 4:
                                bundle.putString("chartUrl", CHART_URL + "prediction/revenuePrediction");
                                Navigation.findNavController(getView())
                                        .navigate(R.id.action_navigation_chart_to_webFragment, bundle);
                                break;
                            default:
                                break;
                        }
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

                    @Override
                    public void onMenuOpened() {}

                    @Override
                    public void onMenuClosed() {
                    }

                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}