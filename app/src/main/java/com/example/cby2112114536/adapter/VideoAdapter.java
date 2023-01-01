package com.example.cby2112114536.adapter;

import static com.example.cby2112114536.common.Constants.BASE_URL;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.activity.MainActivity;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<InfoPageDTO.DataDTO.RecordsDTO, BaseViewHolder> {
    public final static String TAG = "RecyclerBaseAdapter";

    public VideoAdapter(@Nullable List<InfoPageDTO.DataDTO.RecordsDTO> data) {
        super(R.layout.item_popular, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InfoPageDTO.DataDTO.RecordsDTO recordsDTO) {
        helper.setText(R.id.popular_item_title, recordsDTO.getName());
        helper.setText(R.id.popular_item_rating, recordsDTO.getRating());
        helper.getView(R.id.popular_item_player);
        StandardGSYVideoPlayer player = helper.getView(R.id.popular_item_player);
        player.setUpLazy(BASE_URL + "/s3/trailer/" + recordsDTO.getImdbId() + ".mp4",
                true, null, null, recordsDTO.getName());
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setVisibility(ImageView.VISIBLE);
        Glide.with(getContext())
                .load(BASE_URL + "/s3/poster/" + recordsDTO.getImdbId() + ".jpg")
                .error(R.drawable.pic_none)
                .into(imageView);
        player.getTitleTextView().setText(recordsDTO.getName());
        player.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        player.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.startWindowFullscreen(getContext(), false, true);
            }
        });
        //防止错位设置
//        player.setPlayTag(TAG);
//        player.setPlayPosition(position);
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        player.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        player.setReleaseWhenLossAudio(false);
        //全屏动画
        player.setShowFullAnimation(true);
        //小屏时不触摸滑动
        player.setIsTouchWiget(false);
        player.setThumbImageView(imageView);
        player.getThumbImageViewLayout().setVisibility(View.VISIBLE);
    }
}
