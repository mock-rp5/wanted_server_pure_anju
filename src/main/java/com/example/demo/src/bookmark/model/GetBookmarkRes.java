package com.example.demo.src.bookmark.model;

import com.example.demo.src.employment.model.Employment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetBookmarkRes {

    private int employmentBookmarkIdx;
    private int employmentIdx;
    private String imgUrl;
    private int isBookmarked;
    private String title;
    private String companyName;
    private String country;
    private int totalReward;


}
