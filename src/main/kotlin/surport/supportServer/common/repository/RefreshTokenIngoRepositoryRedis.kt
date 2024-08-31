package surport.supportServer.common.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import surport.supportServer.common.authority.REFRESH_EXPIRATION_MILLISECONDS
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenIngoRepositoryRedis(
    private val redisTemplate: RedisTemplate<String,String>,
) {
    companion object{
        private const val KEY_PREFIX = "refreshToken"
    }

    fun save(userId:Long, refreshToken: String){
        val key = "$KEY_PREFIX:$userId:$refreshToken"
        redisTemplate.opsForValue().set(key, "", REFRESH_EXPIRATION_MILLISECONDS, TimeUnit.MILLISECONDS)
    }

    fun findByRefreshToken(refreshToken: String): String?{
        val key = redisTemplate.keys("$KEY_PREFIX:*:$refreshToken").firstOrNull()
        return key?.let { redisTemplate.opsForValue().get(it) }
    }

    fun deleteByRefreshToken(refreshToken: String){
        val keys = redisTemplate.keys("$KEY_PREFIX:*:$refreshToken")
        redisTemplate.delete(keys)
    }

    fun deleteByUserId(userId:Long){
        val keys = redisTemplate.keys("$KEY_PREFIX:$userId:*")
        redisTemplate.delete(keys)
    }
}