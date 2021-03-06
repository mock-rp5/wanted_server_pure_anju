package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    AVAILABLE_EMAIL(true, 1001, "사용 가능한 이메일입니다."),



    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"이미 존재하는 이메일입니다."),

    // resume
    RESUME_EMPTY_RESUME_ID(false, 2020, "이력서 아이디 값을 확인해주세요."),

    //kakao
    OAUTH_KAKAO_EMPTY_TOKEN(false, 2030, "access token을 입력하세요."),

    //search
    GET_SEARCH_FAIL(false, 2040, "검색어를 입력해주세요."),





    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    //bookmark
    POST_BOOKMARK_FAIL(false, 3050, "중복된 북마크 입니다."),
    UPDATE_FAIL_BOOKMARK(false, 3051, "북마크 생성에 실패하였습니다."),
    FAIL_CANCEL_BOOKMARK(false, 3052, "북마크 해제에 실패하였습니다."),

    //company
    DUPLICATED_COMPANY_NAME(false, 3055, "중복된 회사이름입니다."),

    // [POST] /like
    //Like
    POST_LIKE_EXISTS(false, 3060, "이미 좋아요 중입니다."),
    DELETION_FAIL_LIKE(false,3061, "좋아요 취소에 실패하였습니다."),
    PATCH_LIKE_NOT_EXISTS(false, 3062, "좋아요 하고 있지 않습니다."),

    //follow
    POST_FOLLOW_EXISTS(false, 3070, "이미 팔로우 중입니다."),
    DELETION_FAIL_FOLLOW(false, 3071, "팔로우 취소에 실패하였습니다."),
    PATCH_FOLLOW_NOT_EXISTS(false, 3072, "팔로우 하고 있지 않습니다."),

    //kakao
    FAILED_TO_KAKAO_OAUTH(false, 3080, "카카오 유저 정보 조회에 실패하였습니다."),
    FAILED_TO_KAKAO_EMAIL(false, 3081, "카카오계정(이메일) 제공을 동의해주세요."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),


    // 5000 : 필요시 만들어서 쓰세요
    MESSAGE_INVALID_PHONE_NUMBER(false, 5000, "휴대폰번호는 10~11자이어야 합니다.");

    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
