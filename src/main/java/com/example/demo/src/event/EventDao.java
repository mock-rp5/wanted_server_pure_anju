package com.example.demo.src.event;

import com.example.demo.src.event.model.GetEventMainRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EventDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetEventMainRes> getEventMain(){
        String getEventSql = "select E.eventIdx, E.eventImgUrl, case E.isOnline when true then '온라인' else null end as isOnline,\n" +
                "    case E.isArticle when true then '아티클' else null end as isArticle,\n" +
                "    case E.isNetworking when true then '네트워킹' else null end as isNetworking,\n" +
                "    case E.isVod when true then 'VOD' else null end as isVod,\n" +
                "    E.title, E.linkUrl from Event as E";

        return this.jdbcTemplate.query(getEventSql,
                (rs, rowNum) -> new GetEventMainRes(rs.getInt("eventIdx"),
                        rs.getString("eventImgUrl"),
                        rs.getString("isOnline"),
                        rs.getString("isArticle"),
                        rs.getString("isNetworking"),
                        rs.getString("isVod"),
                        rs.getString("title"),
                        rs.getString("linkUrl")));
    }
}

