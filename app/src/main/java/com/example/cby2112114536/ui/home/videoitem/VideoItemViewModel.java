package com.example.cby2112114536.ui.home.videoitem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.cby2112114536.DTO.InfoDTO;
import com.example.cby2112114536.api.InfoApi;
import com.example.cby2112114536.utils.NetUtil;
import com.github.leonardoxh.livedatacalladapter.Resource;

/**
 * @author breeze
 */
public class VideoItemViewModel extends ViewModel {
    public LiveData<InfoDTO> getRandomList(Integer n) {
        return Transformations.map(NetUtil.get(InfoApi.class)
                        .getRandom(n),
                Resource::getResource);
    }
}