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

    public GetEmploymentRes getEmployments(int userIdx, String country, String sort, int years1, int years2) {
        String getCompanyQuery = "select C.companyIdx, C.logo, CI.imgUrl, C.companyName, E.countPositon from Company as C\n" +
                "left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "left join (select companyIdx, count(*) as countPositon from Employment group by companyIdx) E on E.companyIdx = C.companyIdx\n" +
                "where CI.isThumbnail and C.isActivited = ?";

        String getCompanyParams = sort;

        String getEmploymentQuery = "select EM.employmentIdx, CIC.imgUrl, exists(select EB.employmentIdx where userIdx = " + userIdx + ") as isBookmark,EM.title, CIC.companyName, CIC.responsRate, CIC.city as city, CIC.country as country, EM.applicantReward + EM.recommenderReward as totalReward\n" +
                "from Employment as EM\n" +
                "left join (select C.companyIdx, CI.imgUrl, C.companyName, C.country, C.city, case when C.responseRate > 95\n" +
                "        then '응답률 매우 높음'\n" +
                "        when C.responseRate > 90\n" +
                "        then '응답률 높음'\n" +
                "        when C.responseRate < 50\n" +
                "        then null\n" +
                "           end as responsRate, C.isActivited from Company as C\n" +
                "left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "where CI.isThumbnail = true and C.country = ?\n" +
                "order by C.responseRate desc) as CIC on CIC.companyIdx = EM.companyIdx\n" +
                "left join EmploymentBookmark EB on EM.employmentIdx = EB.employmentIdx\n" +
                "where EM.years > " + years1 + " and EM.years < " + years2;

        String getEmploymentParams = country;

        List<Company> companyList = this.jdbcTemplate.query(getCompanyQuery,
                (rs, rowNum) -> new Company(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)), getCompanyParams);

        List<Employment> employmentList = this.jdbcTemplate.query(getEmploymentQuery,
                (rs1, rowNum1) -> new Employment(rs1.getInt(1),
                        rs1.getString(2),
                        rs1.getInt(3),
                        rs1.getString(4),
                        rs1.getString(5),
                        rs1.getString(6),
                        rs1.getString(7),
                        rs1.getString(8),
                        rs1.getInt(9)), getEmploymentParams);

        GetEmploymentRes getEmploymentRes = new GetEmploymentRes(companyList, employmentList);
        return getEmploymentRes;

    }
}
