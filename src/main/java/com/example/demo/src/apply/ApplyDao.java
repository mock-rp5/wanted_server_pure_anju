package com.example.demo.src.apply;

import com.example.demo.src.apply.model.GetApplyRes;
import com.example.demo.src.apply.model.PatchApplyReq;
import com.example.demo.src.apply.model.PostApplyReq;
import com.example.demo.src.bookmark.model.PostBookmarkRes;
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
                "       count(case when A.status = 0 then 1 end) as applicationCompleted,\n" +
                "       count(case when A.status = 1 then 1 end) as documentPass,\n" +
                "       count(case when A.status = 2 then 1 end) as finalPass,\n" +
                "       count(case when A.status = 3 then 1 end) as fail\n" +
                "from Apply A\n" +
                "where userIdx = ?\n" +
                "group by A.status\n";
        Object[] retrieveApplyCountParams = new Object[]{userIdx};
        List<Map<String, Object>> retrieveApplyCountList = this.jdbcTemplate.queryForList(retrieveApplyCountQuery, retrieveApplyCountParams);


        if (!retrieveApplyCountList.isEmpty()) {
            getApplyRes = GetApplyRes.builder()
                    .total((Integer) retrieveApplyCountList.get(0).get("total"))
                    .applicationCompleted((Integer) retrieveApplyCountList.get(0).get("applicationCompleted"))
                    .documentPass((Integer) retrieveApplyCountList.get(0).get("documentPass"))
                    .finalPass((Integer) retrieveApplyCountList.get(0).get("finalPass"))
                    .fail((Integer) retrieveApplyCountList.get(0).get("fail"))
                    .build();
        }

        // 지원 회사 조회
        String retrieveApplyCompanyQuery = "select logo,\n" +
                "       companyName,\n" +
                "       title,\n" +
                "       A.createdAt,\n" +
                "       case\n" +
                "           when A.status = 0 then '지원완료'\n" +
                "           when A.status = 1 then '서류통과'\n" +
                "           when A.status = 2 then '최종합격'\n" +
                "           when A.status = 3 then '불합격' end as status\n" +
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


    //지원 생성
    public void createApplication(PostApplyReq postApplyReq, Long userIdx, int employmentIdx){
        String createApplicationQuery = "insert into Apply (userIdx, employmentIdx, resumeIdx, status) values (?, ?, ?, ?)";
        Object[] createApplicationParams = new Object[]{userIdx, employmentIdx, postApplyReq.getResumeIdx(), postApplyReq.getStatus()};
        this.jdbcTemplate.update(createApplicationQuery, createApplicationParams);

    }

    //지원 수정
    public void updateApplication(PatchApplyReq patchApplyReq, Long userIdx, int employmentIdx){
        String updateApplicationQuery = "update Apply set status = ? where userIdx = ? and employmentIdx = ?";
        Object[] updateApplicationParams = new Object[]{patchApplyReq.getStatus(), userIdx, employmentIdx};

        this.jdbcTemplate.update(updateApplicationQuery, updateApplicationParams);
    }

}
