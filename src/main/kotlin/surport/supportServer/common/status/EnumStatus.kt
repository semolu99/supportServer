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
    GyungM("경상남자"),
    GyungW("경상여자")
}

enum class ResultCode(val statusCode: Int,val code: String, val message: String) {
    SUCCESS(HttpStatus.OK.value(), "SUCCESS","성공"), // 200
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERR001","서버 에러가 발생했습니다"), // 500
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ERR002","요청하신 api를 찾을 수 없습니다."), // 404
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "ERR003","항목이 올바르지 않습니다"), // 400
    MAIL_ERROR(HttpStatus.BAD_REQUEST.value(),"ERR004","발급 받은 인증 코드가 만료 되었거나 잘못 되었습니다."),
    NOT_MEMBER(HttpStatus.NOT_FOUND.value(), "ERR005", "회원 정보를 찾을 수 없습니다."),
    INVALID_JSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERR006","전달된 JSON 형식이 올바르지 않습니다"), // 400
    DUPLICATION_ID(HttpStatus.BAD_REQUEST.value(),"ERR007","중복된 아이디 입니다."),
    LOGIN_ERROR(HttpStatus.BAD_REQUEST.value(), "ERR100","아이디 혹은 비밀번호를 다시 확인하세요."), // 403
    INVALID_DATA(HttpStatus.BAD_REQUEST.value(), "ERR101","데이터 처리 오류 발생"), // 400
    PASSWORD_ERROR(HttpStatus.BAD_REQUEST.value(), "ERR102", "현재 비밀 번호가 맞지 않습니다."),
    NOT_FIND_ID(HttpStatus.NOT_FOUND.value(),"ERR103","존재하지 않은 아이디 입니다."),
    TOKEN_EXPIRED(HttpStatus.FORBIDDEN.value(), "EER402","토큰이 만료 되었습니다"), // 403
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN.value(), "ERR403","토큰이 유효하지 않습니다."), // 403
    REST_TYPE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERR404","REST API TYPE 오류"), // 500
}

enum class ROLE{
    MEMBER,
    ADMIN
}
