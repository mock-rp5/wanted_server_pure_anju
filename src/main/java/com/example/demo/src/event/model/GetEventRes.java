package com.example.demo.src.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEventRes {

    private int eventIdx;
    private String eventImgUrl;
    private String isOnline;
    private String isArticle;
    private String isNetworking;
    private String isVod;
    private String title;
    private String linkUrl;
    private String startDate;
    private String startDay;
    private String endDate;
    private String endDay;
    private String eventTagList;

}
