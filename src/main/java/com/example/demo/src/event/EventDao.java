package com.example.demo.src.event;

import com.example.demo.src.event.model.GetArticleMainRes;
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

    public List<GetArticleMainRes> getArticleMain(){
        String getArticleSql = "select E.eventIdx, E.eventImgUrl, E.title, E.linkUrl, group_concat(ET.name) as eventTagList  from Event as E\n" +
                "left join EventTag ET on E.eventIdx = ET.eventIdx\n" +
                "where E.isArticle = true\n" +
                "group by ET.eventIdx";

        return this.jdbcTemplate.query(getArticleSql,
                (rs, rowNum) -> new GetArticleMainRes(rs.getInt("eventIdx"),
                        rs.getString("eventImgUrl"),
                        rs.getString("title"),
                        rs.getString("linkUrl"),
                        rs.getString("eventTagList")));
    }
}

