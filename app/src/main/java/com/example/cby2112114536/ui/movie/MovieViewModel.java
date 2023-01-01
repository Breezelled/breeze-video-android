package com.example.cby2112114536.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.api.InfoApi;
import com.example.cby2112114536.utils.NetUtil;
import com.github.leonardoxh.livedatacalladapter.Resource;

/**
 * @author breeze
 */
public class MovieViewModel extends ViewModel {

    public LiveData<InfoPageDTO> getInfoPageListByType(Integer pageNum, Integer pageSize, String type) {
        return Transformations.map(NetUtil.get(InfoApi.class)
                        .getpageByType(pageNum, pageSize, type),
                Resource::getResource);
    }

}