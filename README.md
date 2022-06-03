# 📝 Project Introduction
원티드 웹 클론 코딩(프론트엔드 1명, 백엔드 2명)

'본 템플릿의 저작권은 (주)소프트스퀘어드에 있습니다. 상업적 용도의 사용을 금합니다'

>제작 기간: 2022년 5월 21일 ~ 06월 03일

## Member
<p>
  <a href="https://github.com/dkswnkk">
     <img src="https://github.com/dkswnkk.png" width="100">
  </a>
  &nbsp;
  <a href="https://github.com/kys95">
   <img src="https://github.com/kys95.png" width="100">
  </a>
  &nbsp;
  <a href="https://github.com/Sukyung-Park">
   <img src="https://github.com/Sukyung-Park.png" width="100">
  </a>
</p>




## ERD
<img width="500" height="300" src="https://user-images.githubusercontent.com/74492426/171628759-592a7ebd-8689-4c4a-9ce1-1daac59e2408.png">

## Architecture
![Architecture](https://user-images.githubusercontent.com/74492426/171627674-8b66a197-b380-487e-80a1-13c93edac510.png)

## Components
|분류|내용|
|:---:|:---:|
|Language & Frameworks|Java, Spring Boot, Spring Security(JWT)|
|Database|MySQL|
|Infra|AWS(EC2, RDS), Nginx, GitHub Action|
|API Docs.|Swagger, Excel|


## Commit Convention
- [AngularJS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153) 을 따른다.
- `feat`: 새로운 기능 개발
- `fix`: 버그 수정
- `refactor`: 기능 변경이 없는 코드 수정
- `test`: 테스트 코드 작성 및 수정
- `docs`: 문서 작성 및 수정
- `build`: 빌드 파일 작성 및 수정
- `style`: 스타일 변경(reformat, indent)
- `chore`: 단순한 작업

## 📝 개발일지

<details>
<summary>개발 일지(2022년 5월 21일 ~ 06월 03일)</summary>

## 2022-05-21(토)
안주
- ERD설계(80%진행)
  
퓨어
- ERD설계(80%진행)
- 가비아 도메인 구입 및 dev/prod 서버 구축
  
  
## 2022-05-22(일) 
안주
- ERD설계 마무리
- API 명세서 작성
 
퓨어
- ERD수정
- API 리스트업(80%)

## 2022-05-23(월) 
안주
- 회원가입 API 구현
- 로그인 API 구현
- 프론트와 회의 진행 
- 1차 피드백 받음

퓨어
- git pull 에러 해결
- git merge conflict 에러 해결
- 프론트와 회의 진행 : 리스트업한 API 중 주요 API먼저 구현하기로 함.
- 1차 피드백 : ERD 테이블 칼럼값 중 카멜케이스 안된 것 카멜케이스로 수정, API Method 중 DELETE를 PATCH로 수정
 
## 2022-05-24(화)
안주
- 배너 광고 조회 API 구현
- 유저 정보 조회 API 구현

퓨어
- 채용공고 화면 조회 API 구현

## 2022-05-25(수)
안주
- 이력서 생성 API구현
- 해당 유저의 모든 이력서 조회 API 구현
- 이력서 상세 조회 API 구현
- 이력서 삭제 API 구현
  
퓨어
- 채용공고 상세화면 조회 API 구현
- 채용공고 북마크 생성 API 구현
- 채용공고 북마크 취소 API 구현
- 북마크 조회 API 구현
  
## 2022-05-26(목)
 안주
  - 이력서 이름 변경 API 구현
  - 기본 이력서로 변경 API 구현
  - 이력서 작성 상태 변경 API 구현
  - 이력서 수정 API 구현
  
 퓨어
 - 회사 상세 조회 API 구현
 - 회사 정보 생성 API 구현
 - 회사 정보 수정 API 구현
 - 회사 정보 삭제 API 구현
  
## 2022-05-27(금)
안주
- 회원 탈퇴 API 구현
- 유저 프로필 이미지 변경 API 구현
  
퓨어
- 채용공고 좋아요 생성, 취소 버그 해결 
  
## 2022-05-28(토)
안주
- API 명세서 수정
- GitHub Action CI 추가
  
퓨어
- API 명세서 수정
  
## 2022-05-29(일)
안주
- 유저 기본정보 수정 API 구현
- 유저 프로필 조회 API 구현
  
퓨어
- 메인화면 이벤트 조회 API 구현
- 메인화면 아티클 조회 API 구현
  
  
## 2022-05-30(월)
안주
- 프로필 전문분야 설정 API 구현
- 프로필 정보 조회 API 오류 수정
- SMS 인증메세지 API구현(coolsms사용)
  
퓨어
- 회사 팔로우 유무 버그 해결
- 특정조건으로 회사 조회 API 구현
- 특정 태그로 회사 조회 API 구현
- 프론트와 회의 진행 : 프론트분께서 이제 막 퍼블리싱을 끝냄, 주요 화면 관련 API 관련 회의
  
## 2022-05-31(화)
안주
- 지원현황 조회 모델링
- API 명세서 
- 과제를 위한 prod, sub 도메인 구현
 
퓨어
- 채용정보 조회 API 구현
- 진행중인 이벤트 조회 API 구현
- 채용공고 좋아요 리스트 조회 API 구현
  
## 2022-06-01(수)
안주
- ERD 수정에 따른 몇몇개의 API 수정 중
- 마무리 작업을 위한 API 명세서 다듬기
- 마무리 지을 작업 정리 중(남은 API 개발 및 영상 과정 고민)
 
퓨어
- 카카오 OAuth 로그인 API 구현
- SMS로 4자리 인증번호 API(네이버 sens이용) 구현
- ERD 테이블 수정
- 2차 피드백 : ERD 각 status comment 수정, Alarm 테이블과 User 테이블 연결, query string이 포함된 API URL 구체화 

## 2022-06-02(목)
안주
- 작성중인 지원현황 조회 API 구현
- 회사 지원 현황 반환값 수정
- README Architecture 추가  
  

퓨어
- 팔로우, 좋아요 validation 추가
- 검색어 validation 추가 
- API 명세서 수정 및 보완
</details>

## 📝 이슈 
<details>
<summary>개발 이슈 정리</summary>

## 안주
1. git branch 충돌
- 문제: 충돌이 일어난 경우 간단한 두세 문장의 경우 inteliJ 상에서 충돌을 해결하지 않고 깃허브 상에서 해결하였는데, 그때 미처 해결하지 못했던 오류들이 ec2 상에서 build 할 때 에러를 뿜어댔다.
- 해결: GitHub Action을 이용한 CI를 적용하여, 모든 main branch로 보내는 모든 pull request의 코드들을 검증하여 에러를 사전에 발견하도록 하여 번거로움을 해결하였다.

2. validation 적용시 중복된 코드들로 인한 지저분함
- 문제: 기존의 경우 post 요청 시 controller 단에서 모두 조건문을 걸어줘서 해결해야 했다. 그러다 보니 코드가 지저분해졌다.
- 해결: @ControllerAdvice를 이용하여 모든 에러들을 한곳에서 처리하게끔 하였고, Dto 상에서 @valid를 이용하여 검증하였다.
  
3. DTO 클래스의 갯수 증가로 인한 지저분함
- 문제: 이력서 작성의 경우 Post로 요청받아야 하는 값이 너무 많다보니 Dto 클래스 파일이 너무 많아졌고, 가독성이 줄어들고 뭔가 객체지향 적이지 못했다.
- 해결: inner class를 사용하여 한 클래스 파일 내에서 전부 기입할 수 있게 해결하였다.
  
4. CORS ERROR
- 문제: 클라이언트 측에서 API요청시 CORS 에러가 발생했다.
- 해결: WebConfig.class 파일을 생성하고 아래 메서드를 Bean 등록 해줌으로써 해결했다.
 ```java
  
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final long MAX_AGE_SECOND = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECOND);
    }

}
```
 
  
  
  
## 퓨어
1. ec2서버에서 git pull 할 때 에러(2022-05-23)
- 문제 : 새롭게 추가된 기능을 포함시키기 위해 원격 리포지토리(remote repository)에서 소스를 땡겨 올때 다음과 같은 에러가 발생했다.
```git
error: Your local changes to the following files would be overwritten by merge:
         logs/app.log
please commit your changes or stash them before you merge.
```
- 해결 : merge 하기 전에 변경사항을 commit 하거나 stash한다.

2. ec2서버에 배포(2022-05-23)
- 문제 : 원격 접속이 끊어지면 서버가 죽어버린다.
- 해결 : nohum을 통해서 원격 접속이 끊어져도 백그라운드에서 알아서 돌아갈 수 있도록 했다.
         jar 파일을 동작시킬 때의 명령어의 앞, 뒤에 nohup, &만 붙여주면 된다.
  
3. 브랜치 생성 에러(2022-05-25)
- 문제 : 로컬에서 브랜치를 생성하고 원격 리포지토리에 적용시키려 했지만 실패했다.
- 해결 : 원격 리포지토리에서 브랜치를 먼저 생성한 후 로컬에서 
```git
git clone -b {branch_name} --single-branch {저장소 URL}
```
을 통해 해당 브랜치만 따온다.
  
4. boolean 타입 validation 에러(2022-05-26)
- 문제 : 회사 생성 API 구현 중 boolean타입의 이용약관 및 가입동의 부분을 @AsserTrue 로 validation할 때 에러가 발생했다.
- 해결 : boolean대신에 Boolean으로 변경했다.
  
5. postman에서 데이터베이스 연결 실패 에러(2022-05-27)
- 문제 : FollowService단에서 해당 유저가 회사를 팔로우하고 있는지 status로 받아와서 0이아니면 이미 팔로우하고 있다고 처리하여 Exception으로 날려주고  0이라면 팔로우하는 비즈니스 로직을 다음과같이 구성했다.
```java
public void createFollow(int companyIdx, Long userIdx) throws BaseException {
        int status = followDao.getFollowCompany(companyIdx, userIdx);
        System.out.println(status);
        if(status != 0 ){
            throw new BaseException(POST_FOLLOW_EXISTS);
        }
        try{
            followDao.createFollow(companyIdx, userIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
```

FollowDao에서의 쿼리문은 다음과 같다.
```java
public int getFollowCompany(int companyIdx, Long userIdx){
        String GetFollowCompanyQuery = "select count(companyFollowIdx) from CompanyFollow where userIdx = ? and companyIdx = ? and status = 'ACTIVE'";
        Object[] GetFollowCompanyParams = new Object[]{userIdx, companyIdx};
        return this.jdbcTemplate.queryForObject(GetFollowCompanyQuery, int.class,GetFollowCompanyParams);
    }
```

하지만 실행해보면 실제 db상에서 팔로우하고 있음에도 불구하고 status가 0으로 출력되고 postman에서 다음과 같이 데이터베이스 연결에 실패하였다라고 출력됐다.
```java
{
  "isSuccess" : "false",
  "code" : 4000,
  "message" : "데이터베이스 연결에 실패하였습니다."
}
```
- 해결 : 해당 비즈니스 로직과 코드 구성은 Like(좋아요)와 똑같지만 Like관련 API는 문제없이 출력되는 것을 확인하고 해당 로직과 구성은 문제없다고 판단했다. 
         이에 따라 해당 이슈를 팀장님께 보고드렸지만 해결방안을 찾지 못했다. 결국, 다른 작업을 하다가 이틀 뒤에 다시 실행해보니 정상적으로 출력됐다.

  
  
</details>
