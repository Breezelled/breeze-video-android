package com.example.cby2112114536.VO;

/**
 * @author breeze
 */
public class S3VO {
    private String bucketName;

    private String objectName;

    private Integer movieId;

    @Override
    public String toString() {
        return "S3VO{" +
                "bucketName='" + bucketName + '\'' +
                ", objectName='" + objectName + '\'' +
                ", movieId=" + movieId +
                '}';
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }



}
