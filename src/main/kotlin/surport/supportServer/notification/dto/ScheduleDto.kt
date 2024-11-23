package surport.supportServer.notification.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.*
import surport.supportServer.notification.entity.Notification
import surport.supportServer.notification.entity.Schedule
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ScheduleDto(
    var id: Long?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{1,100}"
    )
    @JsonProperty("title")
    private val _title: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{1,1000}"
    )
    @JsonProperty("content")
    private val _content: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜 형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("startDate")
    private val _startDate: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜 형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("endDate")
    private val _endDate: String?,

    @field:Min(value = 1, message = "색상 값은 1 이상이어야 합니다.")
    @field:Max(value = 5, message = "색상 값은 5 이하이어야 합니다.")
    @JsonProperty("color")
    private val _color: Int?,
){
    val title:String
        get()=_title!!

    val content:String
        get()=_content!!

    val startDate:LocalDate
        get()=_startDate!!.toLocalDate()

    val endDate:LocalDate
        get()=_endDate!!.toLocalDate()

    val color:Int
        get() = _color!!

    private fun String.toLocalDate():LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity(): Schedule =
        Schedule(id, title, content, startDate, endDate, color)
}

data class ScheduleDtoResponse(
    val id:Long,
    val title:String,
    val content:String,
    val startDate:LocalDate,
    val endDate:LocalDate,
    val color:Int,
)

data class NotificationDto(
    var id: Long?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{1,100}"
    )
    @JsonProperty("title")
    private val _title: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{1,1000}"
    )
    @JsonProperty("content")
    private val _content: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜 형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("creationDate")
    private val _creationDate: String?,
){
    val title:String
        get() = _title!!

    val content:String
        get() = _content!!

    val creationDate:LocalDate
        get() = _creationDate!!.toLocalDate()

    private fun String.toLocalDate():LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity(): Notification =
        Notification(id, title, content, creationDate)
}

data class NotificationDtoResponse(
    val id:Long,
    val title:String,
    val content:String,
    val creationDate:LocalDate
)

data class NotificationListDtoResponse(
    val id:Long,
    val title:String,
    val creationDate:LocalDate
)
