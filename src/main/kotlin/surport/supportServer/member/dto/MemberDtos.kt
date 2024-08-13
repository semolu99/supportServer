package surport.supportServer.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import surport.supportServer.common.annotation.ValidEnum
import surport.supportServer.common.status.Dorm_type
import surport.supportServer.common.status.Gender

//회원 가입시 받을 정보? 룸 넘버는 일단 보류
data class MemberDtoRequest(
    val id: Long?,

    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @field:Pattern(
        regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수 문자를 포함한 8~20자리로 입력 해 주세요."
    )
    @JsonProperty("password")
    private val _password: String?,

    @field:NotBlank
    @JsonProperty("nickname")
    private val _nickname: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Gender::class, message = "MAN 이나 WOMEN 중 하나를 선택 해 주세요.")
    @JsonProperty("gender")
    private val _gender: String?,

    @field:NotNull
    @JsonProperty("admin")
    private val _admin: Boolean?,

    @field:NotBlank
    @JsonProperty("dormType")
    @field:ValidEnum(enumClass = Dorm_type::class, message = "알맞은 값을 선택 해 주세요.")
    private val _dormType: String?,

    @field:NotNull
    @JsonProperty("dormNo")
    private val _dormNo: Int?,

    @JsonProperty("roomNo")
    private val _roomNo: Int?,
){
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val nickname: String
        get() = _nickname!!
    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    val admin: Boolean
        get() = _admin!!
    val dormType: Dorm_type
        get() = Dorm_type.valueOf(_dormType!!)
    val dormNo: Int
        get() = _dormNo!!
    val roomNo: Int?
        get() = _roomNo
}
