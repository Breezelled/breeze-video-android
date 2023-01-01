package com.example.cby2112114536.ui.movie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.activity.MainActivity;
import com.example.cby2112114536.adapter.VideoAdapter;
import com.example.cby2112114536.databinding.FragmentMovieBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

/**
 * @author breeze
 */
public class MovieFragment extends Fragment {

    private FragmentMovieBinding binding;
    private VideoAdapter videoAdapter;
    private MovieViewModel movieViewModel;
    private int pageNum = 1;
    private final int pageSize = 15;
    private List<InfoPageDTO.DataDTO.RecordsDTO> recordsDTOS = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        movieViewModel =
                new ViewModelProvider(this).get(MovieViewModel.class);

        binding = FragmentMovieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RefreshLayout refreshLayout = (RefreshLayout) binding.refreshLayout;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
                pageNum = 1;
                recordsDTOS.clear();
                getVideoList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                pageNum++;
                getVideoList();
            }
        });
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        RecyclerView recyclerView = binding.recyclerView;
        videoAdapter = new VideoAdapter(null);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = dx + dy;
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(VideoAdapter.TAG)
                            && (position < dx || position > lastVisibleItem)) {
                        if(GSYVideoManager.isFullState(getActivity())) {
                            return;
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos();
                        videoAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recordsDTOS.clear();
        pageNum = 1;
        getVideoList();
        videoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                InfoPageDTO.DataDTO.RecordsDTO recordsDTO = recordsDTOS.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", recordsDTO);
                bundle.putSerializable("records", (Serializable) recordsDTOS);
                GSYVideoManager.releaseAllVideos();
                Navigation.findNavController(view).
                        navigate(R.id.action_navigation_movie_to_movieDetailFragment, bundle);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getVideoList() {
        movieViewModel.getInfoPageListByType(pageNum, pageSize, "Animation")
                .observe(getViewLifecycleOwner(),
                infoPageDTO -> {
            recordsDTOS.addAll(infoPageDTO.getData().getRecords());
            videoAdapter.setList(recordsDTOS);
                });
    }

//    @Override
//    public void onBackPressed() {
//        if (GSYVideoManager.backFromWindowFull(this)) {
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}