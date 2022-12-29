package com.example.cby2112114536.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.cby2112114536.DTO.InfoDTO;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.VO.InfoVO;
import com.example.cby2112114536.api.InfoApi;
import com.example.cby2112114536.api.SplashApi;
import com.example.cby2112114536.utils.NetUtil;
import com.github.leonardoxh.livedatacalladapter.Resource;

import java.util.List;
import java.util.Objects;

/**
 * @author breeze
 */
public class HomeViewModel extends ViewModel {

    public LiveData<InfoDTO> getRecommendList(Integer topN) {
        return Transformations.map(NetUtil.get(InfoApi.class)
                        .getRecommend(topN),
                Resource::getResource);
    }

    public LiveData<InfoPageDTO> getInfoPageList(Integer pageNum, Integer pageSize) {
        return Transformations.map(NetUtil.get(InfoApi.class)
                .getPage(pageNum, pageSize),
                Resource::getResource);
    }

    public LiveData<InfoDTO> getRandomList(Integer n) {
        return Transformations.map(NetUtil.get(InfoApi.class)
                        .getRandom(n),
                Resource::getResource);
    }

}