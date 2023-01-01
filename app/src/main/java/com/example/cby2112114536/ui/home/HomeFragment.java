package com.example.cby2112114536.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.cby2112114536.DTO.InfoDTO;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.adapter.HeaderAdapter;
import com.example.cby2112114536.adapter.HomeAdapter;
import com.example.cby2112114536.adapter.ImageAdapter;
import com.example.cby2112114536.adapter.ImageTitleNumAdapter;
import com.example.cby2112114536.databinding.FragmentHomeBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author breeze
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private int pageNum = 1;
    private final int pageSize = 15;
    private final int topN = 10;
    private HomeAdapter homeAdapter;
    private HeaderAdapter headerAdapter;
    private ImageAdapter imageAdapter;
    private ImageTitleNumAdapter imageTitleNumAdapter;
    private Banner<?, BannerAdapter<?, ?>> banner;
    private List<InfoPageDTO.DataDTO.RecordsDTO> records = new ArrayList<>();
    private List<InfoDTO.DataDTO> rating = new ArrayList<>();
    private List<InfoDTO.DataDTO> ads = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RefreshLayout refreshLayout = binding.refreshLayout;
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        homeAdapter = new HomeAdapter(null);
        headerAdapter = new HeaderAdapter(null);
        imageTitleNumAdapter = new ImageTitleNumAdapter(null);
        recyclerView.setAdapter(homeAdapter);

        homeAdapter.setEmptyView(R.layout.empty_home);
        View headerView = inflater.inflate(R.layout.header_home, container, false);
        RecyclerView topRatingRecyclerView = headerView.getRootView().findViewById(R.id.topRatingRecyclerView);
        topRatingRecyclerView.setAdapter(headerAdapter);
        topRatingRecyclerView.setLayoutManager(linearLayoutManager);
        homeAdapter.addHeaderView(headerView);
        homeAdapter.setHeaderWithEmptyEnable(true);
        banner = headerView.getRootView().findViewById(R.id.banner);
        //添加生命周期观察者
        banner.addBannerLifecycleObserver(this)
                .setIndicator(new RoundLinesIndicator(getContext()))
                .setBannerGalleryMZ(20, 0.8f)
                .start();
        List<Integer> oriList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            oriList.add(R.drawable.pic_none);
        }
        imageAdapter = new ImageAdapter(oriList);
        banner.setAdapter(imageAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
                if (records.isEmpty()) {
                    getInfo(pageNum, pageSize);
                    getBanner();
                    pageNum++;
                } else {
                    homeAdapter.setList(records);
                }


            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                pageNum++;
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                getInfo(pageNum, pageSize);

            }
        });
        pageNum = 1;
        records.clear();
        getInfo(pageNum, pageSize);
        getBanner();
        getHeaderTopRating(topN);
        homeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter,
                                    @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", records.get(position));
                bundle.putString("itemType", "item");
                Navigation.findNavController(view)
                        .navigate(R.id.action_navigation_home_to_videoItemFragment, bundle);
            }
        });
        headerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("topRatingItem", rating.get(position));
                bundle.putString("itemType", "rating");
                Navigation.findNavController(view)
                        .navigate(R.id.action_navigation_home_to_videoItemFragment, bundle);
            }
        });
        imageTitleNumAdapter.setOnBannerListener(new OnBannerListener<InfoDTO.DataDTO>() {
            @Override
            public void OnBannerClick(InfoDTO.DataDTO data, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("urlType", "banner");
                bundle.putString("bannerUrl", data.getImdbId());
                Navigation.findNavController(getView())
                        .navigate(R.id.action_navigation_home_to_webFragment, bundle);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getInfo(Integer pageNum, Integer pageSize) {
        homeViewModel.getInfoPageList(pageNum, pageSize).observe(getViewLifecycleOwner(),
                infoDTO -> {
                    records.addAll(infoDTO.getData().getRecords());
                    homeAdapter.setList(records);
                });

    }

    private void getHeaderTopRating(Integer topN) {
        homeViewModel.getRecommendList(topN).observe(getViewLifecycleOwner(),
                infoDTO -> {
                    rating.addAll(infoDTO.getData());
                    headerAdapter.setList(rating);
                    for (InfoDTO.DataDTO dataDTO:
                         rating) {
                        Log.i("RATING", dataDTO.getName());
                    }
                });
    }

    private void getBanner() {
        homeViewModel.getRandomList(5).observe(getViewLifecycleOwner(),
                infoDTO -> {
                    if (ads.isEmpty()) {
                        ads.addAll(infoDTO.getData());
                    }
                    imageTitleNumAdapter.setDatas(ads);
                    banner.setAdapter(imageTitleNumAdapter);
                });

    }
}