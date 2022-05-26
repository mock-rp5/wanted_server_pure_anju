package com.example.demo.src.bookmark;


import com.example.demo.src.bookmark.model.GetBookmarkRes;

import com.example.demo.src.bookmark.model.PostBookmarkRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;


@Repository
public class BookmarkDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //userIdx, employmentIdx로 status 확인
    public int checkStatusUserEmployment(Long userIdx, int employmentIdx){
        String checkUserEmploymentQuery = "select exists(select employmentBookmarkIdx from EmploymentBookmark where userIdx = ? and employmentIdx = " + employmentIdx + " and status = 'ACTIVE')";
        Long checkUserEmploymentParams = userIdx;

        return this.jdbcTemplate.queryForObject(checkUserEmploymentQuery,
                int.class,
                checkUserEmploymentParams);
    }

    //북마크 생성
    public int createBookmark(Long userIdx, int employmentIdx){

        String checkStatusSql = "select status from EmploymentBookmark where userIdx = ? and employmentIdx = " + employmentIdx + " and status = 'NOTACTIVE'";
        Long checkParam = userIdx;
        List<String> employmentBookmarkList = this.jdbcTemplate.query(checkStatusSql,
                (rs, rowNum) -> rs.getString("status"),
                checkParam);

        if(employmentBookmarkList.isEmpty()) {
            String createBookmarkQuery = "insert into EmploymentBookmark (employmentIdx, userIdx, status) values (?,?,'ACTIVE')";
            Object[] createBookmarkParams = new Object[]{employmentIdx, userIdx};
            this.jdbcTemplate.update(createBookmarkQuery, createBookmarkParams);

            String lastInsertQuery = "select last_insert_id()";
            return this.jdbcTemplate.queryForObject(lastInsertQuery, int.class);
        }
        else{
            String updateBookmarkQuery = "update EmploymentBookmark set status = 'ACTIVE' where userIdx = ? and employmentIdx = ?";
            Object[] updateBookmarkParams = new Object[]{userIdx, employmentIdx};

            this.jdbcTemplate.update(updateBookmarkQuery, updateBookmarkParams);

            return checkUserEmployment(userIdx, employmentIdx);
        }


    }


    //북마크 업데이트
    public PostBookmarkRes updateBookmark(Long userIdx, int employmentIdx, String result){
        String updateBookmarkQuery = "update EmploymentBookmark set status = 'ACTIVE' where userIdx = ? and employmentIdx = ?";
        Object[] updateBookmarkParams = new Object[]{userIdx, employmentIdx};

        this.jdbcTemplate.update(updateBookmarkQuery, updateBookmarkParams);

        String getBookmarkQuery = "select employmentBookmarkIdx from EmploymentBookmark where userIdx = " + userIdx + " and employmentIdx = ? and status = 'ACTIVE";
        int getBookmarkParams = employmentIdx;
        return this.jdbcTemplate.queryForObject(getBookmarkQuery,
                (rs, rowNum) -> new PostBookmarkRes(
                        rs.getLong("userIdx"),
                        rs.getInt("employmentBookmarkIdx"),
                        rs.getString(result)),
                getBookmarkParams);
    }

    //userIdx, employmentIdx로 확인
    public int checkUserEmployment(Long userIdx, int employmentIdx){
        String checkUserEmploymentQuery = "select exists(select employmentBookmarkIdx from EmploymentBookmark where userIdx = ? and employmentIdx = " + employmentIdx + ")";
        Long checkUserEmploymentParams = userIdx;

        return this.jdbcTemplate.queryForObject(checkUserEmploymentQuery,
                int.class,
                checkUserEmploymentParams);
    }


    //북마크 해제
    public int cancelBookmark(Long userIdx, int employmentIdx){
        String cancelBookmarkQuery = "update EmploymentBookmark set status = 'NOTACTIVE' where userIdx = ? and employmentIdx = ?";
        Object[] cancelBookmarkParams = new Object[]{userIdx, employmentIdx};

        return this.jdbcTemplate.update(cancelBookmarkQuery, cancelBookmarkParams);
    }

    //북마크 리스트 조회
    public List<GetBookmarkRes> getBookmarks(Long userIdx){
        String getBookmarksQuery = "select EB.employmentBookmarkIdx, EB.employmentIdx, CI.imgUrl, exists(select EB.employmentIdx ) as isBookmarked,\n" +
                "       E.title, C.companyName, C.country, E.applicantReward + E.recommenderReward as totalReward from EmploymentBookmark as EB\n" +
                "left join Employment E on EB.employmentIdx = E.employmentIdx\n" +
                "left join Company C on E.companyIdx = C.companyIdx\n" +
                "left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "where EB.userIdx = ? and EB.status = 'ACTIVE' and CI.isThumbnail = true";

        Long getBookmarksParams = userIdx;

        return this.jdbcTemplate.query(getBookmarksQuery,
                (rs, rowNum) -> new GetBookmarkRes(
                        rs.getInt("employmentBookmarkIdx"),
                        rs.getInt("employmentIdx"),
                        rs.getString("imgUrl"),
                        rs.getInt("isBookmarked"),
                        rs.getString("title"),
                        rs.getString("companyName"),
                        rs.getString("country"),
                        rs.getInt("totalReward")),
                getBookmarksParams);
    }
}


