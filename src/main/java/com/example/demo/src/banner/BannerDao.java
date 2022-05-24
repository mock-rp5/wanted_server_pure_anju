package com.example.demo.src.banner;

import com.example.demo.src.banner.model.BannerDto;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BannerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<BannerDto> getBanner(String location) {
        String getBannerQuery = "select * from Banner WHERE status = 'ACTIVE' and location = ?";
        Object[] getBannerParams = new Object[]{location};

        List<Map<String, Object>> banners = this.jdbcTemplate.queryForList(getBannerQuery, getBannerParams);
        List<BannerDto> bannerDto = new ArrayList<>();
        for (Map<String, Object> banner : banners) {
            bannerDto.add(
                    BannerDto.builder()
                            .imageUrl((String) banner.get("imageUrl"))
                            .title((String) banner.get("title"))
                            .content((String) banner.get("content"))
                            .linkUrl((String) banner.get("linkUrl"))
                            .build()
            );
        }
        return bannerDto;
    }
}
