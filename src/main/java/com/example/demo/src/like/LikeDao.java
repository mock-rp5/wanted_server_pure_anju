package com.example.demo.src.like;


import com.example.demo.src.like.model.GetLikeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class LikeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 좋아요 생성
    public void createLike(int employmentIdx, Long userIdx) {
        String checkStatusSql = "select status from EmploymentLike where userIdx = ? and employmentIdx = " + employmentIdx + " and status = 'NOTACTIVE'";
        Long checkParam = userIdx;
        List<String> employmentLikeList = this.jdbcTemplate.query(checkStatusSql,
                (rs, rowNum) -> rs.getString("status"),
                checkParam);

        if (employmentLikeList.isEmpty()) {
            String createLikeQuery = "insert into EmploymentLike (employmentIdx, userIdx, status) values (?,?,'ACTIVE')";
            Object[] createLikeParams = new Object[]{employmentIdx, userIdx};
            this.jdbcTemplate.update(createLikeQuery, createLikeParams);

        } else {
            String updateLikeQuery = "update EmploymentLike set status = 'ACTIVE' where userIdx = ? and employmentIdx = ?";
            Object[] updateLikeParams = new Object[]{userIdx, employmentIdx};

            this.jdbcTemplate.update(updateLikeQuery, updateLikeParams);
        }

    }

    public int getLikeEmployment(int employmentIdx, Long userIdx) {
        String GetLikeProductQuery = "select count(employmentLikeIdx) from EmploymentLike " +
                "where userIdx = ? and employmentIdx = ? and status = 'ACTIVE'";
        Object[] GetLikeProductParams = new Object[]{userIdx, employmentIdx};
        return this.jdbcTemplate.queryForObject(GetLikeProductQuery, int.class,GetLikeProductParams);
    }

    public int cancelLike(int employmentIdx, Long userIdx) {
        String cancelLikeQuery = "update EmploymentLike set status = 'NOTACTIVE'" +
                "where userIdx= ? and employmentIdx = ? ";
        Object[] cancelLikeParams = new Object[]{userIdx, employmentIdx};

        return this.jdbcTemplate.update(cancelLikeQuery,cancelLikeParams);
    }

    public List<GetLikeRes> getLikes(Long userIdx){
        String getLikeListQuery = "select EM.employmentIdx, CIC.imgUrl, ELE.countEmploymentLike,\n" +
                "       EM.title, CIC.companyName, CIC.city as city, CIC.country as country, EM.applicantReward + EM.recommenderReward as totalReward\n" +
                "                from Employment as EM\n" +
                "                left join (select C.companyIdx, CI.imgUrl, C.companyName, C.country, C.city from Company as C\n" +
                "                left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "                where CI.isThumbnail = true\n" +
                "                order by C.responseRate desc) as CIC on CIC.companyIdx = EM.companyIdx\n" +
                "                left join EmploymentBookmark EB on EM.employmentIdx = EB.employmentIdx\n" +
                "                right join (select EL.employmentIdx from EmploymentLike as EL\n" +
                "                            where EL.userIdx = " + userIdx + " and EL.status = 'ACTIVE') ELL on ELL.employmentIdx = EM.employmentIdx\n" +
                "                left join (select EL.employmentIdx, count(EL.employmentLikeIdx) as countEmploymentLike from EmploymentLike as EL\n" +
                "                            group by EL.employmentIdx) ELE on ELE.employmentIdx = EM.employmentIdx\n" +
                "                order by EM.employmentIdx";

        return this.jdbcTemplate.query(getLikeListQuery,
                (rs, rowNum) -> new GetLikeRes(rs.getInt("employmentIdx"),
                        rs.getString("imgUrl"),
                        rs.getInt("countEmploymentLike"),
                        rs.getString("title"),
                        rs.getString("companyName"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getInt("totalReward")));
    }


}
