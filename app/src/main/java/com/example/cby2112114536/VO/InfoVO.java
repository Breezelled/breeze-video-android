package com.example.cby2112114536.VO;

import java.io.Serializable;

/**
 * <p>
 * 电影信息
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
public class InfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "InfoVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", runtime='" + runtime + '\'' +
                ", type='" + type + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", intro='" + intro + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", star='" + star + '\'' +
                ", budget='" + budget + '\'' +
                ", revenue='" + revenue + '\'' +
                ", language='" + language + '\'' +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", rating='" + rating + '\'' +
                ", ratingNum='" + ratingNum + '\'' +
                ", tag='" + tag + '\'' +
                ", imdbId='" + imdbId + '\'' +
                '}';
    }

    private String runtime;

    private String type;

    private String releaseDate;

    private String intro;

    private String director;

    private String writer;

    private String star;

    private String budget;

    private String revenue;

    private String language;

    private String company;

    private String country;

    private String rating;

    private String ratingNum;

    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(String ratingNum) {
        this.ratingNum = ratingNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    private String imdbId;


}
