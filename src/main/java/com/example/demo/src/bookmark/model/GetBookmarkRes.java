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
    private List<Employment> employmentList;
}
