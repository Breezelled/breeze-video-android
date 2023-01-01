package com.example.cby2112114536.ui.home.videoitem;

import static com.example.cby2112114536.common.Constants.BASE_URL;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.cby2112114536.DTO.InfoDTO;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.activity.MainActivity;
import com.example.cby2112114536.databinding.FragmentVideoItemBinding;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * @author breeze
 */
public class VideoItemFragment extends Fragment {

    private FragmentVideoItemBinding binding;

    private StandardGSYVideoPlayer detailPlayer;

    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        VideoItemViewModel videoItemViewModel = new ViewModelProvider(this)
                .get(VideoItemViewModel.class);
        binding = FragmentVideoItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detailPlayer = binding.detailPlayer;

        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(getActivity(), detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl("")
                .setCacheWithPlay(false)
                .setVideoTitle("")
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


        if ("item".equals(getArguments().getString("itemType"))) {
            InfoPageDTO.DataDTO.RecordsDTO recordsDTO = (InfoPageDTO.DataDTO.RecordsDTO) getArguments().get("item");
            binding.videoItemTitle.setText(recordsDTO.getName());
            binding.videoItemRating.setText(recordsDTO.getRating());
            binding.videoItemRuntime.setText(recordsDTO.getRuntime());
            binding.videoItemType.setText(recordsDTO.getType());
            binding.videoItemLanguage.setText(recordsDTO.getLanguage());
            binding.videoItemTag.setText(recordsDTO.getTag());
            Glide.with(this)
                    .load(BASE_URL + "/s3/poster/" + recordsDTO.getImdbId() + ".jpg")
                    .error(R.drawable.pic_none)
                    .into(binding.videoItemImage);

            gsyVideoOption.setUrl(BASE_URL + "/s3/trailer/" + recordsDTO.getImdbId() + ".mp4")
                    .setVideoTitle(recordsDTO.getName())
                    .build(binding.detailPlayer);
            binding.detailPlayer.startPlayLogic();
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity!=null) {
                ActionBar actionBar = mainActivity.getSupportActionBar();
                if (actionBar!=null) {
                    actionBar.setTitle(recordsDTO.getName());
                }
            }
        } else if ("rating".equals(getArguments().getString("itemType"))) {
            InfoDTO.DataDTO recordsDTO = (InfoDTO.DataDTO) getArguments().get("topRatingItem");
            binding.videoItemTitle.setText(recordsDTO.getName());
            binding.videoItemRating.setText(recordsDTO.getRating());
            binding.videoItemRuntime.setText(recordsDTO.getRuntime());
            binding.videoItemType.setText(recordsDTO.getType());
            binding.videoItemLanguage.setText(recordsDTO.getLanguage());
            binding.videoItemTag.setText(recordsDTO.getTag());
            Glide.with(this)
                    .load(BASE_URL + "/s3/poster/" + recordsDTO.getImdbId() + ".jpg")
                    .error(R.drawable.pic_none)
                    .into(binding.videoItemImage);

            gsyVideoOption.setUrl(BASE_URL + "/s3/trailer/" + recordsDTO.getImdbId() + ".mp4")
                    .setVideoTitle(recordsDTO.getName())
                    .build(binding.detailPlayer);
            binding.detailPlayer.startPlayLogic();
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity!=null) {
                ActionBar actionBar = mainActivity.getSupportActionBar();
                if (actionBar!=null) {
                    actionBar.setTitle(recordsDTO.getName());
                }
            }
        }

        ImageButton button = binding.mapButton;
        button.setOnClickListener(this::click);
        return root;
    }

    private void click(View view) {
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
        Navigation.findNavController(view).navigate(R.id.action_videoItemFragment_to_mapFragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
    }
}