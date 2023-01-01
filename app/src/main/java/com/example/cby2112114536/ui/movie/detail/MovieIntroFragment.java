package com.example.cby2112114536.ui.movie.detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.databinding.FragmentMovieIntroBinding;

/**
 * @author breeze
 */
public class MovieIntroFragment extends Fragment {

    private FragmentMovieIntroBinding binding;
    private InfoPageDTO.DataDTO.RecordsDTO recordsDTO;

    public MovieIntroFragment(InfoPageDTO.DataDTO.RecordsDTO recordsDTO) {
        this.recordsDTO = recordsDTO;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieIntroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        changeIntro(recordsDTO);
        return root;
    }

    public void changeIntro(InfoPageDTO.DataDTO.RecordsDTO recordsDTO) {
        binding.movieIntroRuntime.setText(recordsDTO.getRuntime());
        binding.movieIntroReleaseDate.setText(recordsDTO.getReleaseDate());
        binding.movieIntroType.setText(recordsDTO.getType());
        binding.movieIntroLanguage.setText(recordsDTO.getLanguage());
        binding.movieIntroRating.setText(recordsDTO.getRating());
        binding.movieIntroIntro.setText(recordsDTO.getIntro());
    }
}