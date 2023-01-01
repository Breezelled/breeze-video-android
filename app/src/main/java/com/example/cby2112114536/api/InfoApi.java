package com.example.cby2112114536.api;

import androidx.lifecycle.LiveData;

import com.example.cby2112114536.DTO.InfoDTO;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.VO.InfoVO;
import com.github.leonardoxh.livedatacalladapter.Resource;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InfoApi {
    @GET("info/page")
    LiveData<Resource<InfoPageDTO>> getPage(@Query("pageNum") Integer pageNum,
                                            @Query("pageSize") Integer pageSize);

    @GET("info/rating")
    LiveData<Resource<InfoDTO>> getRecommend(@Query("topN") Integer topN);

    @GET("info/random")
    LiveData<Resource<InfoDTO>> getRandom(@Query("n") Integer n);

    @GET("info/pageByContentI")
    LiveData<Resource<InfoPageDTO>> getpageByContentI(@Query("pageNum") Integer pageNum,
                                            @Query("pageSize") Integer pageSize,
                                            @Query("content") String content);

    @GET("info/pageByType")
    LiveData<Resource<InfoPageDTO>> getpageByType(@Query("pageNum") Integer pageNum,
                                                      @Query("pageSize") Integer pageSize,
                                                      @Query("type") String type);
}
