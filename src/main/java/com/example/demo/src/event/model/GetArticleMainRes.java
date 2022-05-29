package com.example.demo.src.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetArticleMainRes {
    private int eventIdx;
    private String eventImgUrl;
    private String title;
    private String linkUrl;
    private String eventTagList;
}
