package surport.supportServer.common.status

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

enum class ResultCode(val msg: String){
    SUCCESS("정상 처리 되었습니다."),
    ERROR("에러가 발생했습니다.")
}