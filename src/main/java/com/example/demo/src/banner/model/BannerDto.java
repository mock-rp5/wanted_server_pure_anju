package com.example.demo.src.banner.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerDto {
    private String imageUrl;
    private String title;
    private String content;
    private String linkUrl;
}
