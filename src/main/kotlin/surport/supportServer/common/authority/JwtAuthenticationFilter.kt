package surport.supportServer.common.authority

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import surport.supportServer.common.exception.CustomExceptionHandler

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        try {
            val token = resolveToken(request as HttpServletRequest)

            if(token != null && jwtTokenProvider.validateAccessTokenForFilter(token)){
                val authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: SignatureException) {
            httpRequest.setAttribute("exceptionType", "INVALID_ACCESS_TOKEN")
            httpRequest.setAttribute("exceptionMessage", "Signature validation failed")
        } catch (e: MalformedJwtException) {
            httpRequest.setAttribute("exceptionType", "INVALID_ACCESS_TOKEN")
            httpRequest.setAttribute("exceptionMessage", "Malformed JWT token")
        } catch (e: ExpiredJwtException) {
            httpRequest.setAttribute("exceptionType", "TOKEN_EXPIRED")
            httpRequest.setAttribute("exceptionMessage", "JWT token expired")
        } catch (e: Exception) {
            httpRequest.setAttribute("exceptionType", "UNKNOWN_ERROR")
            httpRequest.setAttribute("exceptionMessage", "Unknown error occurred")
        }
        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest):String? {
        val bearerToken = request.getHeader("Authorization")

        return if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            bearerToken.substring(7)
        } else {
            null
        }
    }
}