package surport.supportServer.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import surport.supportServer.common.annotation.ValidEnum
import surport.supportServer.common.status.Dorm_type
import surport.supportServer.common.status.Gender
import surport.supportServer.member.entity.Member
import java.time.LocalDate

//회원 가입시 받을 정보? 룸 넘버는 일단 보류
data class MemberDtoRequest(
    var id: Long?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,30}"
    )
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @field:Pattern(
        regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
    )
    @JsonProperty("password")
    private val _password: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,10}"
    )
    @JsonProperty("nickname")
    private val _nickname: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Gender::class)
    @JsonProperty("gender")
    private val _gender: String?,

    @field:NotBlank
    @JsonProperty("dormType")
    @field:Pattern(
        regexp = "^.{3,6}"
    )
    @field:ValidEnum(enumClass = Dorm_type::class)
    private val _dormType: String?,

    @field:NotNull
    @JsonProperty("dormNo")
    private val _dormNo: Int?,

    @JsonProperty("roomNo")
    private val _roomNo: Int?,
){
    val encoder = SCryptPasswordEncoder(16,8,1,32,64)

    val loginId: String
        get() = _loginId!!
    val password: String
        get() = encoder.encode(_password)!!
    val nickname: String
        get() = _nickname!!
    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    val dormType: Dorm_type
        get() = Dorm_type.valueOf(_dormType!!)
    val dormNo: Int
        get() = _dormNo!!
    val roomNo: Int?
        get() = _roomNo?.coerceIn(1, 3) ?: 0

    fun toEntity(): Member =
        Member(id, loginId,password,nickname,gender, dormType, dormNo, roomNo)
}

data class LoginDto(
    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,30}"
    )
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
}

data class MemberDtoResponse(
    val id: Long,
    val loginId: String,
    val nickname: String,
    val gender: String,
    val dormType: String,
    val dormNo: Int,
    val roomNo: Int?,
)

data class PasswordChangeDto(
    @field:NotBlank
    @JsonProperty("currentPassword")
    private val _currentPassword: String?,

    @field:NotBlank
    @field:Pattern(
        regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$"
    )
    @JsonProperty("newPassword")
    private val _newPassword: String?
){
    val currentPassword : String
        get() = _currentPassword!!

    val newPassword : String
        get() = _newPassword!!
}

data class MemberUpdateDto(
    var id: Long,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,10}"
    )
    @JsonProperty("nickname")
    private val _nickname: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Gender::class)
    @JsonProperty("gender")
    private val _gender: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,6}"
    )
    @field:ValidEnum(enumClass = Dorm_type::class)
    @JsonProperty("dormType")
    private val _dormType: String?,

    @field:NotNull
    @JsonProperty("dormNo")
    private val _dormNo: Int?,

    @JsonProperty("roomNo")
    private val _roomNo: Int?,
) {
    val nickname: String?
        get() = _nickname
    val gender: Gender?
        get() = _gender?.let { Gender.valueOf(it) }
    val dormType: Dorm_type?
        get() = _dormType?.let { Dorm_type.valueOf(it) }
    val dormNo: Int?
        get() = _dormNo
    val roomNo: Int?
        get() = _roomNo?.coerceIn(1, 3) ?: 0
}

data class MailDto(
    var id: Long?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,30}"
    )
    @JsonProperty("loginId")
    private val _loginId: String?,

    var authCode: String?,

    var sendDate: LocalDate?
){
    val loginId : String
        get() = _loginId!!

}

data class MailCheckDto(
    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,30}"
    )
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("authCode")
    private val _authCode: String?
){
    val loginId:String
        get() = _loginId!!
    val authCode:String
        get() = _authCode!!
}

data class AddAdminDto(
    @field:NotBlank
    @field:Pattern(
        regexp = "^.{3,30}"
    )
    @JsonProperty("loginId")
    private val _loginId: String?,
){
    val loginId:String
        get()=_loginId!!
}

data class TokenRefreshDto(
    val refreshToken : String
)