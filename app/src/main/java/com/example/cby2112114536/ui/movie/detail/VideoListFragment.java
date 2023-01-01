package com.example.cby2112114536.ui.movie.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.adapter.VideoListAdapter;
import com.example.cby2112114536.databinding.FragmentVideoListBinding;

import java.util.List;

/**
 * @author breeze
 */
public class VideoListFragment extends Fragment {

    private FragmentVideoListBinding binding;
    private VideoListAdapter videoListAdapter;
    private int pageNum = 1;
    private final int pageSize = 15;
    private List<InfoPageDTO.DataDTO.RecordsDTO> recordsDTOs;
    private MovieDetailFragment movieDetailFragment;
    private MovieIntroFragment movieIntroFragment;

    public VideoListFragment(List<InfoPageDTO.DataDTO.RecordsDTO> recordsDTOs,
                             MovieDetailFragment movieDetailFragment,
                             MovieIntroFragment movieIntroFragment) {
        this.recordsDTOs = recordsDTOs;
        this.movieDetailFragment = movieDetailFragment;
        this.movieIntroFragment = movieIntroFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = binding.videoListRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListAdapter = new VideoListAdapter(recordsDTOs);
        recyclerView.setAdapter(videoListAdapter);
        videoListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view,
                                    int position) {
                movieDetailFragment.playListItem(recordsDTOs.get(position),
                        recordsDTOs, movieDetailFragment.getRecordsDTO());
                movieIntroFragment.changeIntro(recordsDTOs.get(position));
            }
        });

        return root;
    }

}