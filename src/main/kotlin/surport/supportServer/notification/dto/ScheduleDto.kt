package surport.supportServer.notification.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import surport.supportServer.notification.entity.Schedule
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class ScheduleDto(
    var id: Long?,

    @field:NotBlank
    @JsonProperty("title")
    private val _title: String?,

    @field:NotBlank
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
){
    val title:String
        get()=_title!!

    val content:String
        get()=_content!!

    val startDate:LocalDate
        get()=_startDate!!.toLocalDate()

    val endDate:LocalDate
        get()=_endDate!!.toLocalDate()

    private fun String.toLocalDate():LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity(): Schedule =
        Schedule(id, title, content, startDate, endDate)
}

data class ScheduleDtoResponse(
    val id:Long,
    val title:String,
    val content:String,
    val startDate:LocalDate,
    val endDate:LocalDate
)
