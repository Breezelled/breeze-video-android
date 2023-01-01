package com.example.cby2112114536.adapter;

import static com.example.cby2112114536.common.Constants.BASE_URL;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author breeze
 */
public class VideoListAdapter extends BaseQuickAdapter<InfoPageDTO.DataDTO.RecordsDTO, BaseViewHolder> {

    /**
     * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
     * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
     */
    public VideoListAdapter(List<InfoPageDTO.DataDTO.RecordsDTO> list) {
        super(R.layout.item_search, list);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull InfoPageDTO.DataDTO.RecordsDTO recordsDTO) {
        helper.setText(R.id.search_item_title, recordsDTO.getName());
        Glide.with(getContext())
                .load(BASE_URL + "/s3/poster/" + recordsDTO.getImdbId() + ".jpg")
                .error(R.drawable.pic_none)
                .into((ImageView) helper.getView(R.id.search_item_image));
    }

}
