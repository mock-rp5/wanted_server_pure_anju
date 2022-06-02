package com.example.demo.src.apply;

import com.example.demo.src.apply.model.GetApplyRes;
import com.example.demo.src.resume.model.GetResumeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ApplyDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 지원 현황 조회
    public GetApplyRes retrieveApply(Long userIdx) {
        GetApplyRes getApplyRes = new GetApplyRes();

        // 지원 카운트 조회
        String retrieveApplyCountQuery = "select count(*)                                 as total,\n" +
                "       count(case when A.status = 'complete' then 1 end) as applicationCompleted,\n" +
                "       count(case when A.status = 'pass' then 1 end) as documentPass,\n" +
                "       count(case when A.status = 'accept' then 1 end) as finalPass,\n" +
                "       count(case when A.status = 'fail' then 1 end) as fail\n" +
                "from Apply A\n" +
                "where userIdx = ?\n" +
                "group by A.status\n";
        Object[] retrieveApplyCountParams = new Object[]{userIdx};
        List<Map<String, Object>> retrieveApplyCountList = this.jdbcTemplate.queryForList(retrieveApplyCountQuery, retrieveApplyCountParams);


        if (!retrieveApplyCountList.isEmpty()) {
            getApplyRes = GetApplyRes.builder()
                    .total((Long) retrieveApplyCountList.get(0).get("total"))
                    .applicationCompleted((Long) retrieveApplyCountList.get(0).get("applicationCompleted"))
                    .documentPass((Long) retrieveApplyCountList.get(0).get("documentPass"))
                    .finalPass((Long) retrieveApplyCountList.get(0).get("finalPass"))
                    .fail((Long) retrieveApplyCountList.get(0).get("fail"))
                    .companyList(new ArrayList<>())
                    .build();
        }

        // 지원 회사 조회
        String retrieveApplyCompanyQuery = "select logo,\n" +
                "       companyName,\n" +
                "       title,\n" +
                "       A.createdAt,\n" +
                "       case\n" +
                "           when A.status = 'complete' then '지원완료'\n" +
                "           when A.status = 'pass' then '서류통과'\n" +
                "           when A.status = 'accept' then '최종합격'\n" +
                "           when A.status = 'fail' then '불합격' end as status\n" +
                "from Apply A\n" +
                "         join Employment E on A.employmentIdx = E.employmentIdx\n" +
                "         join Company C on E.companyIdx = C.companyIdx\n" +
                "where UserIdx = ?\n" +
                "order by  A.updatedAt desc";
        Object[] retrieveApplyCompanyParams = new Object[]{userIdx};
        List<Map<String, Object>> retrieveApplyCompanyList = this.jdbcTemplate.queryForList(retrieveApplyCompanyQuery, retrieveApplyCompanyParams);
        for (Map<String, Object> retrieveApplyCompany : retrieveApplyCompanyList) {
            getApplyRes.getCompanyList().add(
                    GetApplyRes.Company.builder()
                            .logo((String) retrieveApplyCompany.get("logo"))
                            .name((String) retrieveApplyCompany.get("companyName"))
                            .position((String) retrieveApplyCompany.get("title"))
                            .writingTime(LocalDate.parse(Objects.toString(retrieveApplyCompany.get("createdAt")).substring(0, 10)))
                            .status((String) retrieveApplyCompany.get("status"))
                            .recommendStatus("앖음")
                            .compensationApplication("없음")
                            .build()
            );
        }
        return getApplyRes;
    }

    public List<GetApplyRes.GetApplyWritingRes> retrieveApplyWriting(Long userIdx) {
        String retrieveApplyWritingQuery = "select logo,\n" +
                "       companyName,\n" +
                "       title,\n" +
                "       A.createdAt,\n" +
                "       case\n" +
                "           when A.status = 'complete' then '지원완료'\n" +
                "           when A.status = 'pass' then '서류통과'\n" +
                "           when A.status = 'accept' then '최종합격'\n" +
                "           when A.status = 'fail' then '불합격' end as status\n" +
                "from Apply A\n" +
                "         join Employment E on A.employmentIdx = E.employmentIdx\n" +
                "         join Company C on E.companyIdx = C.companyIdx\n" +
                "where UserIdx = ?\n" +
                "order by  A.updatedAt desc";
        Object[] retrieveApplyWritingParams = new Object[]{userIdx};
        List<Map<String, Object>> retrieveApplyWritingList = this.jdbcTemplate.queryForList(retrieveApplyWritingQuery, retrieveApplyWritingParams);
        List<GetApplyRes.GetApplyWritingRes> getApplyWritingRes = new ArrayList<>();
        System.out.println(retrieveApplyWritingList.toString());
        for (Map<String, Object> retrieveApplyWriting : retrieveApplyWritingList) {
            getApplyWritingRes.add(
                    GetApplyRes.GetApplyWritingRes.builder()
                            .logo((String) retrieveApplyWriting.get("logo"))
                            .name((String) retrieveApplyWriting.get("companyName"))
                            .writingTime(LocalDate.parse(Objects.toString(retrieveApplyWriting.get("createdAt")).substring(0, 10)))
                            .status((String) retrieveApplyWriting.get("status"))
                            .recommendStatus("없음")
                            .build()
            );
        }
        return getApplyWritingRes;
    }

}
