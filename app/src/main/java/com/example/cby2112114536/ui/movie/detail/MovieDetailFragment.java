package com.example.cby2112114536.ui.movie.detail;

import static com.example.cby2112114536.common.Constants.BASE_URL;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.activity.MainActivity;
import com.example.cby2112114536.databinding.FragmentMovieDetailBinding;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author breeze
 */
public class MovieDetailFragment extends Fragment {

    private FragmentMovieDetailBinding binding;

    private StandardGSYVideoPlayer detailPlayer;

    private boolean isPlay;
    private boolean isPause;

    private GSYVideoOptionBuilder gsyVideoOption;

    private OrientationUtils orientationUtils;

    private InfoPageDTO.DataDTO.RecordsDTO recordsDTO;

    private List<InfoPageDTO.DataDTO.RecordsDTO> recordsDTOs;

    public InfoPageDTO.DataDTO.RecordsDTO getRecordsDTO() {
        return recordsDTO;
    }

    public void setRecordsDTO(InfoPageDTO.DataDTO.RecordsDTO recordsDTO) {
        this.recordsDTO = recordsDTO;
    }

    public List<InfoPageDTO.DataDTO.RecordsDTO> getRecordsDTOs() {
        return recordsDTOs;
    }

    public void setRecordsDTOs(List<InfoPageDTO.DataDTO.RecordsDTO> recordsDTOs) {
        this.recordsDTOs = recordsDTOs;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detailPlayer = binding.detailPlayer;


        recordsDTO = null;
        if (getArguments() != null) {
            recordsDTO = (InfoPageDTO.DataDTO.RecordsDTO) getArguments().get("record");
        }

        recordsDTOs = new ArrayList<>();
        if (getArguments() != null) {
            recordsDTOs = (List<InfoPageDTO.DataDTO.RecordsDTO>) getArguments().get("records");
        }
        recordsDTOs.remove(recordsDTO);

        //增加封面
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(root)
                .load(BASE_URL + "/s3/poster/" + recordsDTO.getImdbId() + ".jpg")
                .into(imageView);

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity!=null) {
            ActionBar actionBar = mainActivity.getSupportActionBar();
            if (actionBar!=null) {
                actionBar.setTitle(recordsDTO.getName());
            }
        }
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(getActivity(), detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(BASE_URL + "/s3/trailer/" + recordsDTO.getImdbId() + ".mp4")
                .setCacheWithPlay(false)
                .setVideoTitle(recordsDTO.getName())
                ///不需要旋转
//                .setNeedOrientationUtils(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(detailPlayer.isRotateWithSystem());
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                        // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
//                        if (orientationUtils != null) {
//                            //配合下方的onConfigurationChanged
//                            orientationUtils.setEnable(!lock);
//                        }
                    }
                }).build(detailPlayer);

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                // orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(getActivity(), true, true);
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        MovieIntroFragment movieIntroFragment = new MovieIntroFragment(recordsDTO);
        fragments.add(movieIntroFragment);
        fragments.add(new VideoListFragment(recordsDTOs, this, movieIntroFragment));
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout, binding.viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.tab_layout_intro_text);
                    break;
                case 1:
                    tab.setText(R.string.tab_layout_more_text);
                default:
                    break;
            }
        });

        tabLayoutMediator.attach();

        return root;
    }

    @Override
    public void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
//        if (orientationUtils != null)
//            orientationUtils.releaseListener();
    }


    /**
     * orientationUtils 和  detailPlayer.onConfigurationChanged 方法是用于触发屏幕旋转的
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(getActivity(), newConfig, orientationUtils, true, true);
        }
    }

    public void playListItem(InfoPageDTO.DataDTO.RecordsDTO recordsDTO,
                             List<InfoPageDTO.DataDTO.RecordsDTO> recordsDTOs,
                             InfoPageDTO.DataDTO.RecordsDTO recordsDTO1) {
        this.recordsDTO = recordsDTO;
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity!=null) {
            ActionBar actionBar = mainActivity.getSupportActionBar();
            if (actionBar!=null) {
                actionBar.setTitle(recordsDTO.getName());
            }
        }
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getActivity())
                .load(BASE_URL + "/s3/poster/" + recordsDTO.getImdbId() + ".jpg")
                .into(imageView);
//        recordsDTOs.remove(recordsDTO);
//        recordsDTOs.add(recordsDTO1);
        gsyVideoOption.setUrl(BASE_URL + "/s3/trailer/" + recordsDTO.getImdbId() + ".mp4")
                .setThumbImageView(imageView)
                .setVideoTitle(recordsDTO.getName())
                .build(detailPlayer);
        detailPlayer.startPlayLogic();
    }
}