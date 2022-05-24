package com.example.demo.src.employment;

import com.example.demo.src.employment.model.Company;
import com.example.demo.src.employment.model.Employment;
import com.example.demo.src.employment.model.GetEmploymentRes;
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
                "where CI.isThumbnail and C.isActivited = " + sort;





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
}
