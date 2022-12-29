package com.example.cby2112114536.api;

import androidx.lifecycle.LiveData;

import com.example.cby2112114536.DTO.S3DTO;
import com.github.leonardoxh.livedatacalladapter.Resource;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author breeze
 */
public interface SplashApi {
    /**
     *
     * @param objectName 对象名
     * @return
     */
    @GET("s3/url")
    LiveData<Resource<S3DTO>> getPresignedUrl(@Query("objectName") String objectName);
}
