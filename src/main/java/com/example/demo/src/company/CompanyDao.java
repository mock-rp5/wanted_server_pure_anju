package com.example.demo.src.company;

import com.example.demo.src.company.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CompanyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetCompanyDetailsRes getCompanyDetails(Long userIdx, int companyIdx){
        String getCompanyDetailsQuery = "select C.companyIdx, C.logo, C.companyName, exists(select CF.companyFollowIdx ) as isFollowed, case when C.responseRate > 95\n" +
                "                then '응답률 매우 높음'\n" +
                "                when C.responseRate > 90\n" +
                "                then '응답률 높음'\n" +
                "                when C.responseRate < 50\n" +
                "                then null\n" +
                "                end as responseRate, C.introduction, C.homePageUrl, C.newAverageSalary, C.totalAverageSalay,\n" +
                "                C.employeeNumber from Company C\n" +
                "left join CompanyFollow CF on C.companyIdx = CF.companyIdx\n" +
                "where userIdx = " + userIdx + " and C.companyIdx = " + companyIdx;



        String getCompanyPositionQuery = "select E.employmentIdx, E.title, E.applicantReward + E.recommenderReward as totalReward,\n" +
                "                       date_format(E.deadLinedAt, '%Y.%m.%d 까지') as deadLine, exists(select EB.employmentIdx where EB.status = 'ACTIVE') as isBookmarked\n" +
                "                from Employment as E\n" +
                "            left join EmploymentBookmark EB on E.employmentIdx = EB.employmentIdx\n" +
                "                where E.companyIdx = " + companyIdx + " and EB.userIdx = " + userIdx;


        String getCompanyImagesQuery = "select CI.companyImgageIdx, CI.imgUrl from CompanyImage as CI\n" +
                "where CI.companyIdx = " + companyIdx;

        String getCompanyTagsQuery = "select CT.companyTagIdx, CT.name from CompanyTag as CT\n" +
                "where CT.companyIdx = " + companyIdx;

        String getCompanyNewsQuery = "select CN.newsIdx, CN.newsUrl from CompanyNews as CN\n" +
                "where CN.companyIdx = " + companyIdx;

        return this.jdbcTemplate.queryForObject(getCompanyDetailsQuery,
                (rs, rowNum) -> new GetCompanyDetailsRes(rs.getInt("companyIdx"),
                        rs.getString("logo"),
                        rs.getString("companyName"),
                        rs.getInt("isFollowed"),
                        rs.getString("responseRate"),
                        rs.getString("introduction"),
                        rs.getString("homePageUrl"),
                        rs.getInt("newAverageSalary"),
                        rs.getInt("totalAverageSalay"),
                        rs.getInt("employeeNumber"),
                        this.jdbcTemplate.query(getCompanyPositionQuery,
                                (rs1, rowNum1) -> new CompanyPosition(
                                        rs1.getInt("employmentIdx"),
                                        rs1.getString("title"),
                                        rs1.getInt("totalReward"),
                                        rs1.getString("deadLine"),
                                        rs1.getInt("isBookmarked"))),
                        this.jdbcTemplate.query(getCompanyImagesQuery,
                                (rs2, rowNum2) -> new CompanyImage(
                                        rs2.getInt("companyImgageIdx"),
                                        rs2.getString("imgUrl"))),
                        this.jdbcTemplate.query(getCompanyTagsQuery,
                                (rs3, rowNum3) -> new CompanyTag(
                                        rs3.getInt("companyTagIdx"),
                                        rs3.getString("name"))),
                        this.jdbcTemplate.query(getCompanyNewsQuery,
                                (rs4, rowNum4) -> new CompanyNews(
                                        rs4.getInt("newsIdx"),
                                        rs4.getString("newsUrl")
                                ))
                ));



    }



    public void createCompany(PostCompanyReq postCompanyReq){
        StringBuffer br = new StringBuffer();
        br.append("insert into Company ");
        br.append("(");
        br.append("companyName, introduction, country, city, address, industry, ");
        br.append("establishYear, email, phoneNumber, isAgreed, isActivited");
        br.append(") ");
        br.append("VALUES (?,?,?,?,?,?,?,?,?,?,false)");
        String sql = br.toString();

        Object[] params = new Object[]{
                postCompanyReq.getCompanyName(), postCompanyReq.getIntroduction(), postCompanyReq.getCountry(), postCompanyReq.getCity(),
                postCompanyReq.getAddress(), postCompanyReq.getIndustry(), postCompanyReq.getEstablishYear(), postCompanyReq.getEmail(),
                postCompanyReq.getPhoneNumber(), postCompanyReq.getIsAgreed()
        };
        this.jdbcTemplate.update(sql, params);
    }




    public boolean getCompanyName(String companyName){
        String sql = "select companyName from Company where companyName = ? and status = 'ACTIVE'";
        String param = companyName;
        List<String> companyNameList = this.jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getString("companyName"),
                param);
        if(companyNameList.isEmpty()) {
            return true;
        }
        return false;
    }

    public void modifyCompany(PatchCompanyReq patchCompanyReq, int companyIdx){
        StringBuffer br = new StringBuffer();
        br.append("update Company set ");
        br.append("companyName = ?, introduction = ?, country = ?, city = ?, address = ?, industry = ?, ");
        br.append("establishYear = ?, email = ?, phoneNumber = ?, isAgreed = ?, isActivited = false, status = 'ACTIVE' ");
        br.append("where companyIdx = ?");
        String sql = br.toString();

        Object[] params = new Object[]{
                patchCompanyReq.getCompanyName(), patchCompanyReq.getIntroduction(), patchCompanyReq.getCountry(), patchCompanyReq.getCity(),
                patchCompanyReq.getAddress(), patchCompanyReq.getIndustry(), patchCompanyReq.getEstablishYear(), patchCompanyReq.getEmail(),
                patchCompanyReq.getPhoneNumber(), patchCompanyReq.getIsAgreed(), companyIdx
        };
        this.jdbcTemplate.update(sql, params);
    }

    public void deleteCompany(int companyIdx){
        StringBuffer br = new StringBuffer();
        br.append("update Company set ");
        br.append("status = 'NOTACTIVE' ");
        br.append("where companyIdx = ?");
        String sql = br.toString();

        Object[] params = new Object[]{companyIdx};
        this.jdbcTemplate.update(sql, params);
    }


}
