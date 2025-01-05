package surport.supportServer.common.status

import org.springframework.http.HttpStatus

enum class Gender(val desc: String) {
    MAN("남"),
    WOMAN("여")
}

enum class Dorm_type(val desc: String){
    GounA("고운A"),
    GounB("고운B"),
    GounC("고운C"),
    GyungM11("경상11"),
    GyungM12("경상12"),
    GyungM13("경상13"),
    GyungM14("경상14"),
    GyungW11("경상11"),
    GyungW12("경상12"),
    GyungW13("경상13"),
    GyungW14("경상14"),
}

enum class ResultCode(val statusCode: Int,val code: String, val message: String) {
    //001 ~ 100 서버 환경
    //101 ~ 150 member
    //401 ~ 500 토큰, 메소드 오류
    SUCCESS(HttpStatus.OK.value(), "SUCCESS","성공"), // 200
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERR001","서버 에러가 발생했습니다"), // 500
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ERR002","요청하신 api를 찾을 수 없습니다."), // 404
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "ERR003","항목이 올바르지 않습니다"), // 400
    MAIL_ERROR(HttpStatus.BAD_REQUEST.value(),"ERR004","발급 받은 인증 코드가 만료 되었거나 잘못 되었습니다."),
    INVALID_JSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERR005","전달된 JSON 형식이 올바르지 않습니다"), // 400
    RUN_TIME_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"ERR006","런타임 에러"),
    //member
    WRONG_FORMAT_LOGIN_ID(HttpStatus.BAD_REQUEST.value(), "ERR101","아이디를 형식에 맞게 작성해 주세요."),
    WRONG_FORMAT_PASSWORD(HttpStatus.BAD_REQUEST.value(),"ERR102","영문, 숫자, 특수 문자를 포함한 8~20자리로 입력 해 주세요."),
    WRONG_FORMAT_NICKNAME(HttpStatus.BAD_REQUEST.value(),"ERR103","닉네임을 형식에 맞게 작성해 주세요."),
    WRONG_FORMAT_GENDER(HttpStatus.BAD_REQUEST.value(), "ERR104","성별을 형식에 맞게 작성해 주세요."),
    WRONG_FORMAT_DORM_TYPE(HttpStatus.BAD_REQUEST.value(),"ERR105","기숙사 유형을 선택해 주세요."),
    WRONG_FORMAT_DORM_NUMBER(HttpStatus.BAD_REQUEST.value(),"ERR106","기숙사 호실를 입력해 주세요."),
    WRONG_FORMAT_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST.value(),"ERR107","기존의 비밀번호를 입력해 주세요."),
    WRONG_FORMAT_NEW_PASSWORD(HttpStatus.BAD_REQUEST.value(),"ERR108","새로운 비밀 번호 : 영문, 숫자, 특수 문자를 포함한 8~20자리로 입력 해 주세요."),
    DUPLICATION_ID(HttpStatus.BAD_REQUEST.value(),"ERR109","중복된 아이디 입니다."),
    WRONG_FORMAT_AUTH_CODE(HttpStatus.BAD_REQUEST.value(),"ERR110", "이메일 인증 코드를 입력해 주세요"),
    LOGIN_ERROR(HttpStatus.BAD_REQUEST.value(), "ERR111","아이디 혹은 비밀번호를 다시 확인하세요."), // 403
    PASSWORD_ERROR(HttpStatus.BAD_REQUEST.value(), "ERR112", "현재 비밀 번호가 맞지 않습니다."),
    NOT_FIND_ID(HttpStatus.NOT_FOUND.value(),"ERR113","존재 하지 않은 아이디 입니다."),
    NOT_MEMBER(HttpStatus.NOT_FOUND.value(), "ERR114", "회원 정보를 찾을 수 없습니다."),
    NOT_MAIL_CHECKED(HttpStatus.NOT_FOUND.value(),"ERR115","메일 인증이 되어 있지 않습니다."),
    //notification
    WRONG_FORMAT_TITLE(HttpStatus.BAD_REQUEST.value(),"ERR151", "제목을 형식에 맞게 작성해 주세요."),
    WRONG_FORMAT_CONTENT(HttpStatus.BAD_REQUEST.value(),"ERR152", "내용을 형식에 맞게 작성해 주세요."),
    WRONG_FORMAT_START_DATE(HttpStatus.BAD_REQUEST.value(),"ERR153", "시작 날짜를 형식에 맞게 작성해 주세요."),
    WRONG_FORMAT_END_DATE(HttpStatus.BAD_REQUEST.value(),"ERR154", "종료 날짜을 형식에 맞게 작성 주세요."),
    WRONG_FORMAT_CREATION_DATE(HttpStatus.BAD_REQUEST.value(),"ERR155", "생성 일을 형식에 맞게 작성해 주세요.."), // 안쓸 가능성 존재
    NOT_FIND_SCHEDULE(HttpStatus.NOT_FOUND.value(), "ERR156","스케줄을 찾을 수 없습니다."), // 403
    NOT_FIND_NOTIFICATION(HttpStatus.NOT_FOUND.value(), "ERR157","공지사항을 찾을 수 없습니다."),
    WRONG_FORMAT_COLOR(HttpStatus.NOT_FOUND.value(), "ERR158","색상을 찾을 수 없습니다."),
    WRONG_DATE(HttpStatus.BAD_REQUEST.value(),"ERR159", "시작 날짜는 종료 날짜 이전 이어야 합니다."),
    //Token, Auth
    INVALID_DATA(HttpStatus.BAD_REQUEST.value(), "ERR401","데이터 처리 오류 발생"), // 400
    TOKEN_EXPIRED(HttpStatus.FORBIDDEN.value(), "EER402","토큰이 만료 되었습니다"), // 403
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN.value(), "ERR403","토큰이 유효 하지 않습니다."), // 403
    REST_TYPE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERR404","REST API TYPE 오류"), // 500
}

enum class ROLE{
    MEMBER,
    ADMIN
}
