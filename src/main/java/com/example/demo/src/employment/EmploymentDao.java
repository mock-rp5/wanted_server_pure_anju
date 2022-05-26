package com.example.demo.src.employment;

import com.example.demo.src.company.model.CompanyTag;
import com.example.demo.src.employment.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;


@Repository
public class EmploymentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetEmploymentRes getEmployments(Long userIdx, String country, String sort, int years1, int years2) {
        String getCompanyQuery = "select C.companyIdx, C.logo, CI.imgUrl, C.companyName, E.countPosition from Company as C\n" +
                "left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "left join (select companyIdx, count(*) as countPosition from Employment group by companyIdx) E on E.companyIdx = C.companyIdx\n" +

                "where CI.isThumbnail = true and C.isActivited = " + sort;









        List<Company> companyList = this.jdbcTemplate.query(getCompanyQuery,
                (rs, rowNum) -> new Company(rs.getInt("companyIdx"),
                        rs.getString("logo"),
                        rs.getString("imgUrl"),
                        rs.getString("companyName"),
                        rs.getInt("countPosition")));
        String getEmploymentQuery = "select EM.employmentIdx, CIC.imgUrl, exists(select EB.employmentIdx where userIdx = " + userIdx + ") as isBookmark,EM.title, CIC.companyName, CIC.responseRate, CIC.city as city, CIC.country as country, EM.applicantReward + EM.recommenderReward as totalReward\n" +
                "from Employment as EM\n" +
                "left join (select C.companyIdx, CI.imgUrl, C.companyName, C.country, C.city, case when C.responseRate > 95\n" +
                "        then '응답률 매우 높음'\n" +
                "        when C.responseRate > 90\n" +
                "        then '응답률 높음'\n" +
                "        when C.responseRate < 50\n" +
                "        then null\n" +
                "           end as responseRate from Company as C\n" +
                "left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "where CI.isThumbnail = true and C.country = ?\n" +
                "order by C.responseRate desc) as CIC on CIC.companyIdx = EM.companyIdx\n" +
                "left join EmploymentBookmark EB on EM.employmentIdx = EB.employmentIdx\n" +
                "where EM.years > " + years1 + " and EM.years < " + years2;
        String getEmploymentParams = country;
        List<Employment> employmentList = this.jdbcTemplate.query(getEmploymentQuery,
                (rs1, rowNum1) -> new Employment(rs1.getInt("employmentIdx"),
                        rs1.getString("imgUrl"),
                        rs1.getInt("isBookmark"),
                        rs1.getString("title"),
                        rs1.getString("companyName"),
                        rs1.getString("responseRate"),
                        rs1.getString("city"),
                        rs1.getString("country"),
                        rs1.getInt("totalReward")), getEmploymentParams);
        GetEmploymentRes getEmploymentRes = new GetEmploymentRes(companyList, employmentList);
        return getEmploymentRes;

    }

    public GetEmploymentDetailRes getEmployDetails(Long userIdx, int employmentIdx){
        String getEmployDetailQuery = "select EE.employmentIdx, CI.imgUrl, EE.title, C.companyName, case when C.responseRate > 95\n" +
                "        then '응답률 매우 높음'\n" +
                "        when C.responseRate > 90\n" +
                "        then '응답률 높음'\n" +
                "        when C.responseRate < 50\n" +
                "        then null\n" +
                "           end as responseRate, C.city, C.country, EE.recommenderReward, EE.applicantReward,\n" +
                "                                exists(select distinct EB.employmentIdx where EB.status = 'ACTIVE' and EB.userIdx = " + userIdx + ") as isBookmarked,\n" +
                "                                exists(select A.employmentIdx where A.userIdx = " + userIdx + ") as isApplied,\n" +
                "                                ELL.countLike,\n" +
                "                                       EE.introduction, EE.primaryTask, EE.qualification,\n" +
                "       EE.preferentialTreatment, EE.welfare, date_format(EE.deadLinedAt, '%Y.%m.%d') as deadLine, EE.workArea from Company as C\n" +
                "left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "right join\n" +
                "(select E.employmentIdx, E.companyIdx, E.title, E.introduction, E.primaryTask, E.qualification, E.preferentialTreatment, E.welfare,\n" +
                "        E.deadlinedAt, E.workArea, E.recommenderReward, E.applicantReward\n" +
                "    from Employment as E\n" +
                "where E.employmentIdx = " + employmentIdx + ") EE on EE.companyIdx = C.companyIdx\n" +
                "left join EmploymentBookmark EB on EE.employmentIdx = EB.employmentIdx\n" +
                "left join Apply A on A.employmentIdx = EE.employmentIdx\n" +
                "left join (select ifnull(EL.employmentIdx, " + employmentIdx + ") as employmentIdx, ifnull(count(*), 0) as countLike from EmploymentLike as EL\n" +
                "where EL.employmentIdx = " + employmentIdx + ") as ELL on ELL.employmentIdx = EE.employmentIdx\n" +
                "where CI.isThumbnail = true";

        String getCompanyTagQuery = "select CT.companyTagIdx, CT.name from CompanyTag as CT\n" +
                "inner join Company C on CT.companyIdx = C.companyIdx\n" +
                "inner join Employment E on C.companyIdx = E.companyIdx\n" +
                "where E.employmentIdx = " + employmentIdx;

        String getEmploymentSkillQuery = "select ES.employmentSkillIdx, ES.name from EmploymentSkill as ES\n" +
                "left join Employment E on ES.employmentIdx = E.employmentIdx\n" +
                "where E.employmentIdx = " + employmentIdx;

        return this.jdbcTemplate.queryForObject(getEmployDetailQuery,
                (rs, rowNum) -> new GetEmploymentDetailRes(rs.getInt("employmentIdx"),
                        rs.getString("imgUrl"),
                        rs.getString("title"),
                        rs.getString("companyName"),
                        rs.getString("responseRate"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getInt("recommenderReward"),
                        rs.getInt("applicantReward"),
                        rs.getInt("isBookmarked"),
                        rs.getInt("isApplied"),
                        rs.getInt("countLike"),
                        rs.getString("introduction"),
                        rs.getString("primaryTask"),
                        rs.getString("qualification"),
                        rs.getString("preferentialTreatment"),
                        rs.getString("welfare"),
                        rs.getString("deadLine"),
                        rs.getString("workArea"),
                        this.jdbcTemplate.query(getCompanyTagQuery,
                                (rs1, rowNum1) -> new CompanyTag(
                                        rs1.getInt("companyTagIdx"),
                                        rs1.getString("name")))
                        , this.jdbcTemplate.query(getEmploymentSkillQuery,
                        (rs2, rowNum2) -> new EmploymentSkill(
                                rs2.getInt("employmentSkillIdx"),
                                rs2.getString("name")))
                ));


    }
}

