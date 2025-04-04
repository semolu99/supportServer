package surport.supportServer.notification.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.*
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.common.status.ResultCode
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
    private var _title: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^.{1,1000}"
    )
    @JsonProperty("content")
    private var _content: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$"
    )
    @JsonProperty("startDate")
    private var _startDate: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$"
    )
    @JsonProperty("endDate")
    private var _endDate: String?,

    @NotBlank
    @field:Min(value = 1)
    @field:Max(value = 5)
    @JsonProperty("color")
    private var _color: String? ="6",
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
        get() = _color!!.toInt()

    private fun String.toLocalDate():LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity(): Schedule =
        Schedule(id, title, content, startDate, endDate, color)

    fun dateCompare(startDate: LocalDate, endDate: LocalDate){
        if(endDate < startDate) throw InvalidInputException(ResultCode.WRONG_DATE.statusCode,ResultCode.WRONG_DATE.message, ResultCode.WRONG_DATE.code)
    }
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
){
    val title:String
        get() = _title!!

    val content:String
        get() = _content!!

    private val now = LocalDate.now().toString()
    private fun String.toLocalDate():LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity(): Notification =
        Notification(id, title, content, now.toLocalDate())
}

data class NotificationListDtoResponse(
    val id:Long,
    val title:String,
    val content:String,
    val creationDate:LocalDate
)
