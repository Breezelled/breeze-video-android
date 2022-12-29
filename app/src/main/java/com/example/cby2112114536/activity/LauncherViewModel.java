package com.example.cby2112114536.activity;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.cby2112114536.DTO.S3DTO;
import com.example.cby2112114536.api.SplashApi;
import com.example.cby2112114536.utils.NetUtil;
import com.github.leonardoxh.livedatacalladapter.Resource;

import java.util.Objects;

/**
 * @author breeze
 */
public class LauncherViewModel extends ViewModel {

    public LiveData<S3DTO> getPresignedUrl(String objectName) {
        LiveData<S3DTO> map = Transformations.map(NetUtil.get(SplashApi.class)
                        .getPresignedUrl(objectName),
                Resource::getResource);
        Log.i("ads", "getPresignedUrl: " + map.getValue());
        return map;
    }

}