package com.example.demo.src.bookmark.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostBookmarkRes {
    private Long userIdx;
    private int employmentBookmarkIdx;
    private String result;
}
