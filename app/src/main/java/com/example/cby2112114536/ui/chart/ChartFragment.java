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
        boolean flags[] = new boolean[5];
        Bundle bundle = new Bundle();

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),
                        R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#E8906B"), R.drawable.icon_rating)
                .addSubMenu(Color.parseColor("#E8906B"), R.drawable.icon_type)
                .addSubMenu(Color.parseColor("#E8906B"), R.drawable.icon_dollar)
                .addSubMenu(Color.parseColor("#E8906B"), R.drawable.icon_others)
                .addSubMenu(Color.parseColor("#E8906B"), R.drawable.icon_prediction)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {


                    @Override
                    public void onMenuSelected(int index) {
                        bundle.putString("urlType", "chart");
                        flags[index] = true;
                        }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

                    @Override
                    public void onMenuOpened() {}

                    @Override
                    public void onMenuClosed() {
                        for (int i = 0; i < flags.length; i++) {
                            if (flags[i]) {
                                flags[i] = false;
                                switch (i){
                                    case 0:
                                        bundle.putString("chartUrl", CHART_URL + "dashboard/rating");
                                        Navigation.findNavController(getView())
                                                .navigate(R.id.action_navigation_chart_to_webFragment, bundle);
                                        break;
                                    case 1:
                                        bundle.putString("chartUrl", CHART_URL + "dashboard/analysis");
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
                        }

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