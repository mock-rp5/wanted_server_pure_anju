package com.example.demo.src.resume;

import com.example.demo.src.resume.model.GetResumeRes;
import com.example.demo.src.resume.model.PatchResumeReq;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ResumeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 이력서 작성
    public PostResumeRes createResume(Long userIdx, PostResumeReq postResumeReq) {

        // 이력서 생성
        String createResumeQuery = "insert Resume(userIdx, title, name, email, phoneNumber, content, isCompleted, isDefaulted) values (?, ?, ?, ?,?,?, ?, 0)";
        Object[] createResumeParams = new Object[]{userIdx, postResumeReq.getTitle(), postResumeReq.getName(), postResumeReq.getEmail(), postResumeReq.getPhoneNumber(), postResumeReq.getContent(), postResumeReq.getIsCompleted()};
        this.jdbcTemplate.update(createResumeQuery, createResumeParams);

        Long resumeIdx = this.jdbcTemplate.queryForObject("select last_insert_id()", Long.class);

        // 경력 생성
        for (int i = 0; i < postResumeReq.getCareers().size(); i++) {
            String createCareerQuery = "insert Career(resumeIdx, companyName, employmentForm, startDate, resignationDate, isPresented) values(?, ?, ?, ?, ?, ?)";
            Object[] createCareerParams = new Object[]{resumeIdx, postResumeReq.getCareers().get(i).getCompanyName(), postResumeReq.getCareers().get(i).getEmploymentForm(), postResumeReq.getCareers().get(i).getStartDate(), postResumeReq.getCareers().get(i).getResignationDate(), postResumeReq.getCareers().get(i).getIsPresented()};
            this.jdbcTemplate.update(createCareerQuery, createCareerParams);

            // 주요 성과 생성
            if (!postResumeReq.getCareers().get(i).getMajorAccomplishments().isEmpty()) {
                Long careerIdx = this.jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
                for (int k = 0; k < postResumeReq.getCareers().get(i).getMajorAccomplishments().size(); k++) {
                    String createMajorAccomplishmentQuery = "insert MajorAccomplishment(careerIdx, content, startDate, endDate) values(?, ?, ?, ?)";
                    Object[] createMajorAccomplishmentParams = new Object[]{careerIdx, postResumeReq.getCareers().get(i).getMajorAccomplishments().get(k).getContent(), postResumeReq.getCareers().get(i).getMajorAccomplishments().get(k).getStartDate(), postResumeReq.getCareers().get(i).getMajorAccomplishments().get(k).getEndDate()};
                    this.jdbcTemplate.update(createMajorAccomplishmentQuery, createMajorAccomplishmentParams);
                }
            }
        }

        // 학력 생성
        for (int i = 0; i < postResumeReq.getEducations().size(); i++) {
            String createEducationQuery = "insert Education(resumeIdx, schoolName, major, admissionDate, graduationDate, content, isAttended) values(?, ?, ?, ?, ?, ?, ?)";
            Object[] createEducationParams = new Object[]{resumeIdx, postResumeReq.getEducations().get(i).getSchoolName(), postResumeReq.getEducations().get(i).getMajor(), postResumeReq.getEducations().get(i).getAdmissionDate(), postResumeReq.getEducations().get(i).getGraduationDate(), postResumeReq.getEducations().get(i).getContent(), postResumeReq.getEducations().get(i).getIsAttended()};
            this.jdbcTemplate.update(createEducationQuery, createEducationParams);
        }

        // 스킬 생성
        for (int i = 0; i < postResumeReq.getSkills().size(); i++) {
            String createSkillQuery = "insert Skill(resumeIdx, skillName) values (?, ?)";
            Object[] createSkillParams = new Object[]{resumeIdx, postResumeReq.getSkills().get(i).getSkillName()};
            this.jdbcTemplate.update(createSkillQuery, createSkillParams);
        }

        // 수상 및 기타 생성
        for (int i = 0; i < postResumeReq.getAwards().size(); i++) {
            String createAwardsQuery = "insert Awards(resumeIdx, awardsDate, awardsTitle, awardsContent) values(?,?,?,?)";
            Object[] createAwardsParams = new Object[]{resumeIdx, postResumeReq.getAwards().get(i).getAwardsDate(), postResumeReq.getAwards().get(i).getAwardsTitle(), postResumeReq.getAwards().get(i).getAwardsContent()};
            this.jdbcTemplate.update(createAwardsQuery, createAwardsParams);
        }

        // 외국어 자격증 생성
        for (int i = 0; i < postResumeReq.getForeignLanguages().size(); i++) {
            String createForeignLanguageQuery = "insert ForeignLanguage(resumeIdx, languageType, languageLevel, licenseName, licenseScore, acquisitionDate) values(?,?,?,?,?,?)";
            Object[] createForeignLanguageParams = new Object[]{resumeIdx, postResumeReq.getForeignLanguages().get(i).getLanguageType(), postResumeReq.getForeignLanguages().get(i).getLanguageLevel(), postResumeReq.getForeignLanguages().get(i).getLicenseName(), postResumeReq.getForeignLanguages().get(i).getLicenseScore(), postResumeReq.getForeignLanguages().get(i).getAcquisitionDate()};
            this.jdbcTemplate.update(createForeignLanguageQuery, createForeignLanguageParams);
        }

        // 포트폴리오 생성
        for (int i = 0; i < postResumeReq.getPortfolios().size(); i++) {
            String createPortfoliosQuery = "insert Portfolio(resumeIdx, portfolioUrl1, portfolioUrl2, portfolioUrl3) values(?,?,?,?)";
            Object[] createPortfoliosParams = new Object[]{resumeIdx, postResumeReq.getPortfolios().get(i).getPortfolioUrl1(), postResumeReq.getPortfolios().get(i).getPortfolioUrl2(), postResumeReq.getPortfolios().get(i).getPortfolioUrl3()};
            this.jdbcTemplate.update(createPortfoliosQuery, createPortfoliosParams);
        }

        return PostResumeRes.builder()
                .resumeIdx(resumeIdx)
                .build();
    }

    // 모든 이력서 조회
    public List<GetResumeRes.RetrieveAllResume> retrieveAllResume(Long userIdx) {
        String retrieveAllResumeQuery = "select * from Resume where userIdx = ? and status = 'ACTIVE' order by updatedAt desc";
        Object[] retrieveAllResumeParams = new Object[]{userIdx};
        List<Map<String, Object>> resumeList = this.jdbcTemplate.queryForList(retrieveAllResumeQuery, retrieveAllResumeParams);
        List<GetResumeRes.RetrieveAllResume> retrieveAllResumeList = new ArrayList<>();

        for (Map<String, Object> resume : resumeList) {
            retrieveAllResumeList.add(
                    GetResumeRes.RetrieveAllResume.builder()
                            .resumeIdx((Long) resume.get("resumeIdx"))
                            .title((String) resume.get("title"))
                            .isCompleted((Boolean) resume.get("isCompleted"))
                            .isDefaulted((Boolean) resume.get("isDefaulted"))
                            .updatedAt(LocalDate.parse(Objects.toString(resume.get("updatedAt")).substring(0, 10)))
                            .build()
            );
        }
        return retrieveAllResumeList;
    }

    // 특정 이력서 조회
    public GetResumeRes retrieveResume(Long resumeIdx) {

        // 이력서 조회
        String retrieveResumeQuery = "select * from Resume where resumeIdx = ?";
        Object[] retrieveAllResumeParams = new Object[]{resumeIdx};
        List<Map<String, Object>> resumeList = this.jdbcTemplate.queryForList(retrieveResumeQuery, retrieveAllResumeParams);
        GetResumeRes getResumeRes = GetResumeRes.builder()
                .resumeIdx((Long) resumeList.get(0).get("resumeIdx"))
                .title((String) resumeList.get(0).get("title"))
                .name((String) resumeList.get(0).get("name"))
                .email((String) resumeList.get(0).get("email"))
                .phoneNumber((String) resumeList.get(0).get("phoneNumber"))
                .content((String) resumeList.get(0).get("content"))
                .isCompleted((Boolean) resumeList.get(0).get("isCompleted"))
                .careers(new ArrayList<>())
                .educations(new ArrayList<>())
                .skills(new ArrayList<>())
                .awards(new ArrayList<>())
                .foreignLanguages(new ArrayList<>())
                .portfolios(new ArrayList<>())
                .build();

        // 경력 조회
        String retrieveCareerQuery = "select * from Career where resumeIdx = ? AND status = 'ACTIVE'";
        Object[] retrieveCareerParams = new Object[]{resumeIdx};
        List<Map<String, Object>> careerList = this.jdbcTemplate.queryForList(retrieveCareerQuery, retrieveCareerParams);
        if (!careerList.isEmpty()) {

            for (int i = 0; i < careerList.size(); i++) {
                Long careerIdx = (Long) careerList.get(i).get("careerIdx");

                // 주요 성과 조회
                String majorAccomplishmentQuery = "select * from MajorAccomplishment where careerIdx = ? AND status = 'ACTIVE'";
                Object[] majorAccomplishmentParams = new Object[]{careerIdx};
                List<Map<String, Object>> majorAccomplishmentList = this.jdbcTemplate.queryForList(majorAccomplishmentQuery, majorAccomplishmentParams);
                List<GetResumeRes.MajorAccomplishment> majorAccomplishments = new ArrayList<>();

                for (Map<String, Object> majorAccomplishment : majorAccomplishmentList) {
                    majorAccomplishments.add(
                            GetResumeRes.MajorAccomplishment.builder()
                                    .majorAccomplishmentIdx((Long) majorAccomplishment.get("majorAccomplishmentIdx"))
                                    .content((String) majorAccomplishment.get("content"))
                                    .startDate(LocalDate.parse(Objects.toString(majorAccomplishment.get("startDate")).substring(0, 10)))
                                    .endDate(LocalDate.parse(Objects.toString(majorAccomplishment.get("endDate")).substring(0, 10)))
                                    .build()
                    );
                }


                getResumeRes.getCareers().add(
                        GetResumeRes.Career.builder()
                                .careerIdx((Long) careerList.get(i).get("careerIdx"))
                                .companyName((String) careerList.get(i).get("companyName"))
                                .employmentForm((String) careerList.get(i).get("employmentForm"))
                                .startDate(LocalDate.parse(Objects.toString(careerList.get(i).get("startDate")).substring(0, 10)))
                                .resignationDate(LocalDate.parse(Objects.toString(careerList.get(i).get("resignationDate")).substring(0, 10)))
                                .isPresented((Boolean) careerList.get(i).get("isPresented"))
                                .majorAccomplishments((List) majorAccomplishments)
                                .build()
                );
            }
        }
        // 학력 조회
        String retrieveEducationQuery = "select * from Education where resumeIdx = ? AND status = 'ACTIVE'";
        Object[] retrieveEducationParams = new Object[]{resumeIdx};
        List<Map<String, Object>> educationList = this.jdbcTemplate.queryForList(retrieveEducationQuery, retrieveEducationParams);
        if (!educationList.isEmpty()) {
            for (Map<String, Object> education : educationList) {
                getResumeRes.getEducations().add(
                        GetResumeRes.Education.builder()
                                .educationIdx((Long) education.get("educationIdx"))
                                .schoolName((String) education.get("schoolName"))
                                .major((String) education.get("major"))
                                .admissionDate(LocalDate.parse(Objects.toString(education.get("admissionDate")).substring(0, 10)))
                                .graduationDate(LocalDate.parse(Objects.toString(education.get("graduationDate")).substring(0, 10)))
                                .content((String) education.get("content"))
                                .isAttended((Boolean) education.get("isAttended"))
                                .build()
                );
            }
        }

        // 기술 조회
        String retrieveSkillQuery = "select * from Skill where resumeIdx = ? AND status = 'ACTIVE'";
        Object[] retrieveSkillParams = new Object[]{resumeIdx};
        List<Map<String, Object>> skillList = this.jdbcTemplate.queryForList(retrieveSkillQuery, retrieveSkillParams);
        if (!skillList.isEmpty()) {
            for (Map<String, Object> skill : skillList) {
                getResumeRes.getSkills().add(
                        GetResumeRes.Skill.builder()
                                .skillIdx((Long) skill.get("skillIdx"))
                                .skillName((String) skill.get("skillName"))
                                .build()
                );
            }
        }
        // 수상 및 기타 조회
        String retrieveAwardsQuery = "select * from Awards where resumeIdx = ? AND status = 'ACTIVE'";
        Object[] retrieveAwardsParams = new Object[]{resumeIdx};
        List<Map<String, Object>> awardsList = this.jdbcTemplate.queryForList(retrieveAwardsQuery, retrieveAwardsParams);
        if (!awardsList.isEmpty()) {
            for (Map<String, Object> awards : awardsList) {
                getResumeRes.getAwards().add(
                        GetResumeRes.Awards.builder()
                                .awardsIdx((Long) awards.get("awardsIdx"))
                                .awardsTitle((String) awards.get("awardsTitle"))
                                .awardsContent((String) awards.get("awardsContent"))
                                .awardsDate(LocalDate.parse(Objects.toString(awards.get("awardsDate")).substring(0, 10)))
                                .build()
                );
            }
        }

        // 외국어 능력 조회
        String retrieveForeignLanguageQuery = "select * from ForeignLanguage where resumeIdx = ? AND status = 'ACTIVE'";
        Object[] retrieveForeignLanguageParams = new Object[]{resumeIdx};
        List<Map<String, Object>> foreignLanguageList = this.jdbcTemplate.queryForList(retrieveForeignLanguageQuery, retrieveForeignLanguageParams);
        if (!foreignLanguageList.isEmpty()) {
            for (Map<String, Object> foreignLanguage : foreignLanguageList) {
                getResumeRes.getForeignLanguages().add(
                        GetResumeRes.ForeignLanguage.builder()
                                .foreignLanguageIdx((Long) foreignLanguage.get("foreignLanguageIdx"))
                                .languageType((String) foreignLanguage.get("languageType"))
                                .languageLevel((String) foreignLanguage.get("languageLevel"))
                                .licenseName((String) foreignLanguage.get("licenseName"))
                                .licenseScore((Double) foreignLanguage.get("licenseScore"))
                                .acquisitionDate(LocalDate.parse(Objects.toString(foreignLanguage.get("acquisitionDate")).substring(0, 10)))
                                .build()
                );
            }
        }
        // 포트폴리오 조회
        String retrievePortfolioQuery = "select * from Portfolio where resumeIdx = ? AND status = 'ACTIVE'";
        Object[] retrievePortfolioParams = new Object[]{resumeIdx};
        List<Map<String, Object>> portfolioList = this.jdbcTemplate.queryForList(retrievePortfolioQuery, retrievePortfolioParams);
        if (!portfolioList.isEmpty()) {
            getResumeRes.getPortfolios().add(
                    GetResumeRes.Portfolio.builder()
                            .portfolioIdx((Long) portfolioList.get(0).get("portfolioIdx"))
                            .portfolioUrl1((String) portfolioList.get(0).get("portfolioUrl1"))
                            .portfolioUrl2((String) portfolioList.get(0).get("portfolioUrl2"))
                            .portfolioUrl3((String) portfolioList.get(0).get("portfolioUrl3"))
                            .build()
            );
        }
        return getResumeRes;
    }

    // 이력서 삭제
    public void deleteResume(Long resumeIdx) {
        String deleteResumeQuery = "update Resume set status = 'NOTACTIVE' where resumeIdx = ?;";
        Object[] deleteResumeQueryParams = new Object[]{resumeIdx};
        this.jdbcTemplate.update(deleteResumeQuery, deleteResumeQueryParams);
    }

    // 이력서 이름 변경
    public void updateResumeTitle(Long resumeIdx, PatchResumeReq.UpdateResumeTitleReq updateResumeTitleReq) {
        String updateResumeTitleQuery = "update Resume set title = ? where resumeIdx = ?";
        Object[] updateResumeTitleParams = new Object[]{updateResumeTitleReq.getTitle(), resumeIdx};
        this.jdbcTemplate.update(updateResumeTitleQuery, updateResumeTitleParams);
    }

    // 기본 이력서로 변경
    public void updateResumeDefault(Long resumeIdx) {
        String updateResumeDefaultQuery = "update Resume set isDefaulted = CASE WHEN resumeIdx = ? then true ELSE isDefaulted = false END";
        Object[] updateResumeDefaultParams = new Object[]{resumeIdx};
        this.jdbcTemplate.update(updateResumeDefaultQuery, updateResumeDefaultParams);
    }

    // 이력서 작성상태 변경
    public void updateResumeCompleted(Long resumeIdx) {
        String updateResumeCompletedQuery = "update Resume set isCompleted = if(isCompleted = 1, 0, 1) where resumeIdx = ?";
        Object[] updateResumeCompletedParams = new Object[]{resumeIdx};
        this.jdbcTemplate.update(updateResumeCompletedQuery, updateResumeCompletedParams);
    }


    // 이력서 수정
    public void updateResume(Long resumeIdx, PatchResumeReq patchResumeReq) {
        // 이력서 수정
        String updateResumeQuery = "update Resume set title = ?, name = ? , email = ?, phoneNumber = ?, content = ?, isCompleted = ? where resumeIdx = ?";
        Object[] updateResumeParams = new Object[]{patchResumeReq.getTitle(), patchResumeReq.getName(), patchResumeReq.getEmail(), patchResumeReq.getPhoneNumber(), patchResumeReq.getContent(), patchResumeReq.getIsCompleted(), resumeIdx};
        this.jdbcTemplate.update(updateResumeQuery, updateResumeParams);


        String deleteMajorAccomplishmentQuery = "delete MajorAccomplishment FROM MajorAccomplishment join Career C on C.careerIdx = MajorAccomplishment.careerIdx where resumeIdx = ?";
        this.jdbcTemplate.update(deleteMajorAccomplishmentQuery, resumeIdx);

        String deleteCareerQuery = "delete FROM Career where resumeIdx = ?";
        this.jdbcTemplate.update(deleteCareerQuery, resumeIdx);

        String deleteEducationQuery = "delete FROM Education where resumeIdx = ?";
        this.jdbcTemplate.update(deleteEducationQuery, resumeIdx);

        String deleteSkillQuery = "delete FROM Skill where resumeIdx = ?";
        this.jdbcTemplate.update(deleteSkillQuery, resumeIdx);

        String deleteAwardsQuery = "delete FROM Awards where resumeIdx = ?";
        this.jdbcTemplate.update(deleteAwardsQuery, resumeIdx);

        String deleteForeignLanguageQuery = "delete FROM ForeignLanguage where resumeIdx = ?";
        this.jdbcTemplate.update(deleteForeignLanguageQuery, resumeIdx);

        String deletePortfoliosQuery = "delete FROM Portfolio where resumeIdx = ?";
        this.jdbcTemplate.update(deletePortfoliosQuery, resumeIdx);

        // 경력 생성

        for (int i = 0; i < patchResumeReq.getCareers().size(); i++) {
            String createCareerQuery = "insert Career(resumeIdx, companyName, employmentForm, startDate, resignationDate, isPresented) values(?, ?, ?, ?, ?, ?)";
            Object[] createCareerParams = new Object[]{resumeIdx, patchResumeReq.getCareers().get(i).getCompanyName(), patchResumeReq.getCareers().get(i).getEmploymentForm(), patchResumeReq.getCareers().get(i).getStartDate(), patchResumeReq.getCareers().get(i).getResignationDate(), patchResumeReq.getCareers().get(i).getIsPresented()};
            this.jdbcTemplate.update(createCareerQuery, createCareerParams);

            // 주요 성과 생성
            if (!patchResumeReq.getCareers().get(i).getMajorAccomplishments().isEmpty()) {
                Long careerIdx = this.jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
                for (int k = 0; k < patchResumeReq.getCareers().get(i).getMajorAccomplishments().size(); k++) {
                    String createMajorAccomplishmentQuery = "insert MajorAccomplishment(careerIdx, content, startDate, endDate) values(?, ?, ?, ?)";
                    Object[] createMajorAccomplishmentParams = new Object[]{careerIdx, patchResumeReq.getCareers().get(i).getMajorAccomplishments().get(k).getContent(), patchResumeReq.getCareers().get(i).getMajorAccomplishments().get(k).getStartDate(), patchResumeReq.getCareers().get(i).getMajorAccomplishments().get(k).getEndDate()};
                    this.jdbcTemplate.update(createMajorAccomplishmentQuery, createMajorAccomplishmentParams);
                }
            }
        }

        // 학력 생성
        for (int i = 0; i < patchResumeReq.getEducations().size(); i++) {
            String createEducationQuery = "insert Education(resumeIdx, schoolName, major, admissionDate, graduationDate, content, isAttended) values(?, ?, ?, ?, ?, ?, ?)";
            Object[] createEducationParams = new Object[]{resumeIdx, patchResumeReq.getEducations().get(i).getSchoolName(), patchResumeReq.getEducations().get(i).getMajor(), patchResumeReq.getEducations().get(i).getAdmissionDate(), patchResumeReq.getEducations().get(i).getGraduationDate(), patchResumeReq.getEducations().get(i).getContent(), patchResumeReq.getEducations().get(i).getIsAttended()};
            this.jdbcTemplate.update(createEducationQuery, createEducationParams);
        }

        // 스킬 생성
        for (int i = 0; i < patchResumeReq.getSkills().size(); i++) {
            String createSkillQuery = "insert Skill(resumeIdx, skillName) values (?, ?)";
            Object[] createSkillParams = new Object[]{resumeIdx, patchResumeReq.getSkills().get(i).getSkillName()};
            this.jdbcTemplate.update(createSkillQuery, createSkillParams);
        }

        // 수상 및 기타 생성
        for (int i = 0; i < patchResumeReq.getAwards().size(); i++) {
            String createAwardsQuery = "insert Awards(resumeIdx, awardsDate, awardsTitle, awardsContent) values(?,?,?,?)";
            Object[] createAwardsParams = new Object[]{resumeIdx, patchResumeReq.getAwards().get(i).getAwardsDate(), patchResumeReq.getAwards().get(i).getAwardsTitle(), patchResumeReq.getAwards().get(i).getAwardsContent()};
            this.jdbcTemplate.update(createAwardsQuery, createAwardsParams);
        }

        // 외국어 자격증 생성
        for (int i = 0; i < patchResumeReq.getForeignLanguages().size(); i++) {
            String createForeignLanguageQuery = "insert ForeignLanguage(resumeIdx, languageType, languageLevel, licenseName, licenseScore, acquisitionDate) values(?,?,?,?,?,?)";
            Object[] createForeignLanguageParams = new Object[]{resumeIdx, patchResumeReq.getForeignLanguages().get(i).getLanguageType(), patchResumeReq.getForeignLanguages().get(i).getLanguageLevel(), patchResumeReq.getForeignLanguages().get(i).getLicenseName(), patchResumeReq.getForeignLanguages().get(i).getLicenseScore(), patchResumeReq.getForeignLanguages().get(i).getAcquisitionDate()};
            this.jdbcTemplate.update(createForeignLanguageQuery, createForeignLanguageParams);
        }

        // 포트폴리오 생성
        for (int i = 0; i < patchResumeReq.getPortfolios().size(); i++) {
            String createPortfoliosQuery = "insert Portfolio(resumeIdx, portfolioUrl1, portfolioUrl2, portfolioUrl3) values(?,?,?,?)";
            Object[] createPortfoliosParams = new Object[]{resumeIdx, patchResumeReq.getPortfolios().get(i).getPortfolioUrl1(), patchResumeReq.getPortfolios().get(i).getPortfolioUrl2(), patchResumeReq.getPortfolios().get(i).getPortfolioUrl3()};
            this.jdbcTemplate.update(createPortfoliosQuery, createPortfoliosParams);
        }
    }
}


