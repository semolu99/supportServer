package surport.supportServer.common.authority

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import surport.supportServer.common.dto.CustomUser
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.util.*

const val MILLISECOND: Long = 1000
const val ACCESS_EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30
const val REFRESH_EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 60 * 24 * 24

@Component
class JwtTokenProvider {
    @Value("\${jwt.access_secret}")
    lateinit var accessSecretKey: String

    @Value("\${jwt.refresh_secret}")
    lateinit var refreshSecretKey : String

    private val accessKey by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretKey)) }
    private val refreshKey by lazy {Keys.hmacShaKeyFor(Decoders.BASE64.decode((refreshSecretKey)))}
    /**
     * Token 생성
     */
    fun createToken(authentication: Authentication): TokenInfo {
        val authorities: String = authentication
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        //println("*$now")
        val accessExpiration = Date(now.time + ACCESS_EXPIRATION_MILLISECONDS)
        val refreshExpiration = Date(now.time + REFRESH_EXPIRATION_MILLISECONDS)
        //println("*$accessExpiration")
        //println("*$refreshExpiration")
        // Access Token
        val accessToken = Jwts
            .builder()
            .subject(authentication.name)
            .claim("auth", authorities)
            .claim("userId",(authentication.principal as CustomUser).userId)
            .issuedAt(now)
            .expiration(accessExpiration)
            .signWith(accessKey, Jwts.SIG.HS256)
            .compact()

        val refreshToken = Jwts
            .builder()
            .subject(authentication.name)
            .claim("auth",authorities)
            .claim("userId",(authentication.principal as CustomUser).userId)
            .issuedAt(now)
            .expiration(refreshExpiration)
            .signWith(refreshKey, Jwts.SIG.HS256)
            .compact()
        println("**"+accessExpiration)
        println("**"+refreshExpiration)

        return TokenInfo("Bearer",accessToken, refreshToken)
    }
    /**
     * token 정보추출
     */
    fun getAuthentication(token: String): Authentication{
        val claims: Claims = getAccessTokenClaims(token)

        val auth = claims["auth"]?: throw RuntimeException("잘못된 토큰입니다.")
        val userId = claims["userId"]?: throw RuntimeException("잘못된 토큰입니다.")

        //권한 정보 추출
        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails = CustomUser(userId.toString().toLong(),claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun getUserIdFromRefreshToken(token:String):Long{
        val claims: Claims = getRefreshTokenClaims(token)
        var userId = claims["userId"]?: throw RuntimeException("잘못된 토큰입니다.")
        return (userId.toString()).toLong()
    }

    fun getTimeoutFromRefreshToken(token: String): Date {
        val claims: Claims = getRefreshTokenClaims(token)
        val timeOut = claims.issuedAt ?: throw RuntimeException("잘못된 토큰입니다.")
        val refreshExpiration = Date(timeOut.time+ REFRESH_EXPIRATION_MILLISECONDS)
        return refreshExpiration
    }

    fun validateRefreshTokenAndCreateToken(refreshToken : String): TokenInfo{
        try{
            val refreshClaims: Claims = getRefreshTokenClaims(refreshToken)
            val now = Date()

            //새로운 ACCESS 토크 발행
            val newAccessToken : String = Jwts
                .builder()
                .subject(refreshClaims.subject)
                .claim("auth", refreshClaims["auth"])
                .claim("userId",refreshClaims["userId"])
                .issuedAt(now)
                .expiration(Date(now.time + ACCESS_EXPIRATION_MILLISECONDS))
                .signWith(accessKey, Jwts.SIG.HS256)
                .compact()

            val newRefreshToken: String = Jwts
                .builder()
                .subject(refreshClaims.subject)
                .claim("auth", refreshClaims["auth"])
                .claim("userId", refreshClaims["userId"])
                .issuedAt(now)
                .expiration(Date(now.time + REFRESH_EXPIRATION_MILLISECONDS))
                .signWith(refreshKey, Jwts.SIG.HS256)
                .compact()

            return TokenInfo("Bearer",newAccessToken, newRefreshToken)
        } catch (e:Exception){
            throw e
        }
    }
    /**
     * Token 검증
     */
    fun validateAccessTokenForFilter(token: String): Boolean {
        try {
            getAccessTokenClaims(token)
            return true
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {} // Invalid JWT Token
                is MalformedJwtException -> {} // Invalid JWT Token
                is ExpiredJwtException -> {} // Expired JWT Token
                is UnsupportedJwtException -> {} // Unsupported JWT Token
                is IllegalArgumentException -> {} // JWT claims string is empty
                else -> {} // else
            }
            println(e.message)
        }
        return false
    }

    private fun getAccessTokenClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(accessKey)
            .build()
            .parseSignedClaims(token)
            .payload

    private fun getRefreshTokenClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(refreshKey)
            .build()
            .parseSignedClaims(token)
            .payload
}