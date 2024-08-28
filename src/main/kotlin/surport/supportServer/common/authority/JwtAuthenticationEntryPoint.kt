package surport.supportServer.common.authority

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.common.status.ResultCode
import java.io.IOException


@Component
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.apply {
            contentType = "application/json"
            status = HttpServletResponse.SC_UNAUTHORIZED

            val errorResponse = BaseResponse<String>(
                statusCode = status,
                statusMessage = ResultCode.INVALID_ACCESS_TOKEN.message,
                code = ResultCode.INVALID_ACCESS_TOKEN.code
            )

            val writer = writer
            objectMapper.writeValue(writer, errorResponse)
            writer.flush()
        }
    }
}