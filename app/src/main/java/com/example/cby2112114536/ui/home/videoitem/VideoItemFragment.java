package com.example.cby2112114536.ui.home.videoitem;

import static com.example.cby2112114536.common.Constants.BASE_URL;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.cby2112114536.DTO.InfoDTO;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.databinding.FragmentVideoItemBinding;

/**
 * @author breeze
 */
public class VideoItemFragment extends Fragment {

    private FragmentVideoItemBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        VideoItemViewModel videoItemViewModel = new ViewModelProvider(this)
                .get(VideoItemViewModel.class);
        binding = FragmentVideoItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.i("ITEMTYPE", getArguments().getString("itemType"));
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
        }

        return root;
    }

}