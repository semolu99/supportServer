package surport.supportServer.common.dto

import org.springframework.validation.FieldError
import surport.supportServer.common.status.ResultCode
import java.io.ObjectInputFilter.Status
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class BaseResponse<T>(
    val statusCode: Int = ResultCode.SUCCESS.statusCode,
    val statusMessage: String? = ResultCode.SUCCESS.message,
    val responseTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    val data: T? = null,
    val code: String = ResultCode.SUCCESS.code
)