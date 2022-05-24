package com.example.demo.src.resume;

import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import com.example.demo.src.validation.model.email.PostEmailReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
                String createMajorAccomplishmentQuery = "insert MajorAccomplishment(careerIdx, content, startDate, endDate) values(?, ?, ?, ?)";
                Object[] createMajorAccomplishmentParams = new Object[]{careerIdx, postResumeReq.getCareers().get(i).getMajorAccomplishments().get(i).getContent(), postResumeReq.getCareers().get(i).getMajorAccomplishments().get(i).getStartDate(), postResumeReq.getCareers().get(i).getMajorAccomplishments().get(i).getEndDate()};
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

}
