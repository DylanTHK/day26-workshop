package com.workshop.day26.model;

import java.security.Timestamp;

public class Game {
    private Integer id;
    private String name;
    private Integer rank;
    private Float averageRating;
    private Integer usersRated;
    private String url;
    private String img;
    private Timestamp timeStamp;

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
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public Float getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }
    public Integer getUsersRated() {
        return usersRated;
    }
    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", name=" + name + ", rank=" + rank + ", averageRating=" + averageRating
                + ", usersRated=" + usersRated + ", url=" + url + ", img=" + img + ", timeStamp=" + timeStamp + "]";
    }

}
