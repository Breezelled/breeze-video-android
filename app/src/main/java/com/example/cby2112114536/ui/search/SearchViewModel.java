package com.example.cby2112114536.ui.search;

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
public class SearchViewModel extends ViewModel {

    public LiveData<InfoPageDTO> getInfoPageListByContent(Integer pageNum,
                                                          Integer pageSize,
                                                          String content) {
        return Transformations.map(NetUtil.get(InfoApi.class)
                        .getpageByContentI(pageNum, pageSize, content),
                Resource::getResource);
    }
}