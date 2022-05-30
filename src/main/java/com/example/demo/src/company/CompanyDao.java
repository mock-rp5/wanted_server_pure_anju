package com.example.demo.src.company;

import com.example.demo.src.company.model.*;

import com.example.demo.src.employment.model.EmploymentBySearch;
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

    public GetCompanyBySearchRes getCompanyBySearch(Long userIdx, String condition){
        String getCompanySql = "select count(distinct C.companyIdx) as countOfCompany, C.companyIdx, C.logo, C.companyName, C.industry,\n" +
                "       exists(select CF.companyFollowIdx where CF.status = 'ACTIVE') as isFollowed,\n" +
                "       count(E.employmentIdx) as countOfEmployment from Company C\n" +
                "       left join CompanyFollow CF on C.companyIdx = CF.companyIdx\n" +
                "       right join Employment E on C.companyIdx = E.companyIdx\n" +
                "       where userIdx = " + userIdx + " and C.companyName = " + condition;



        String getEmploymentListSql = "select EM.employmentIdx, CIC.imgUrl, exists(select EB.employmentBookmarkIdx where EB.userIdx = " + userIdx + " and EB.status = 'ACTIVE') as isBookmark,EM.title, CIC.companyName,\n" +
                "       CIC.city as city, CIC.country as country, EM.applicantReward + EM.recommenderReward as totalReward\n" +
                "                from Employment as EM\n" +
                "                left join (select C.companyIdx, CI.imgUrl, C.companyName, C.country, C.city from Company as C\n" +
                "                left join CompanyImage CI on C.companyIdx = CI.companyIdx\n" +
                "                where CI.isThumbnail = true\n" +
                "                order by C.responseRate desc) as CIC on CIC.companyIdx = EM.companyIdx\n" +
                "                left join EmploymentBookmark EB on EM.employmentIdx = EB.employmentIdx\n" +
                "                where CIC.companyName = " + condition;


        return this.jdbcTemplate.queryForObject(getCompanySql,
                (rs, rowNum) -> new GetCompanyBySearchRes(rs.getInt("countOfCompany"),
                        rs.getInt("companyIdx"),
                        rs.getString("logo"),
                        rs.getString("companyName"),
                        rs.getString("industry"),
                        rs.getInt("isFollowed"),
                                        rs.getInt("countOfEmployment"),
                                        this.jdbcTemplate.query(getEmploymentListSql,
                                        (rs2, rowNum2) -> new EmploymentBySearch(rs2.getInt("employmentIdx"),
                                                rs2.getString("imgUrl"),
                                                rs2.getInt("isBookmark"),
                                                rs2.getString("title"),
                                                rs2.getString("companyName"),
                                                rs2.getString("city"),
                                                rs2.getString("country"),
                                                rs2.getInt("totalReward"))
                                        )));

    }

    public List<GetCompanyByTagRes> getCompanyByTag(Long userIdx, String tag){
        String getCompanyByTagSql = "select C.companyIdx, C.logo, C.companyName, case CFF.isFollowed when 1 then 1\n" +
                "    else 0 end as isFollowed, CL.companyTagList from Company as C\n" +
                "left join\n" +
                "    (select CF.companyIdx, exists(select CF.companyFollowIdx and userIdx = " + userIdx + " and status = 'ACTIVE') as isFollowed from CompanyFollow as CF) CFF on CFF.companyIdx = C.companyIdx\n" +
                "right join\n" +
                "    (select CT.companyIdx from CompanyTag as CT\n" +
                "                          where CT.name like " + tag + "\n" +
                "group by CT.companyIdx) CTT on CTT.companyIdx = C.companyIdx\n" +
                "left join (select CompanyTag.companyIdx, group_concat(CompanyTag.name) as companyTagList from CompanyTag\n" +
                "                                                                       group by companyIdx) CL on CL.companyIdx = C.companyIdx";

        return this.jdbcTemplate.query(getCompanyByTagSql,
                (rs, rowNum) -> new GetCompanyByTagRes(
                        rs.getInt("companyIdx"),
                        rs.getString("logo"),
                        rs.getString("companyName"),
                        rs.getInt("isFollowed"),
                        rs.getString("companyTagList")
                ));
    }


}

