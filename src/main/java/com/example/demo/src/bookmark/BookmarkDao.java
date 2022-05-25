package com.example.demo.src.bookmark;

import com.example.demo.src.bookmark.model.PostBookmarkRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
        String createBookmarkQuery = "insert into EmploymentBookmark (employmentIdx, userIdx, status) values (?,?,'ACTIVE')";
        Object[] createBookmarkParams = new Object[]{employmentIdx, userIdx};
        this.jdbcTemplate.update(createBookmarkQuery, createBookmarkParams);

        String lastInsertQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertQuery, int.class);
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
}
