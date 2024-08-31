package surport.supportServer.common.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class MailRepositoryRedis(
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object{
        private const val KEY_PREFIX = "mail"
    }
    private val timeout: Long = 1000 * 60 * 10

    fun save(loginId: String, mailCode: String){
//        val key = "$KEY_PREFIX:$loginId:$mailCode"
//        redisTemplate.opsForValue().set(key, "", timeout, TimeUnit.MILLISECONDS)
        redisTemplate.opsForValue().set(loginId, mailCode, timeout, TimeUnit.MILLISECONDS)
    }

    fun findByMailCode(loginId: String) :String? {
        //val key = redisTemplate.keys("$KEY_PREFIX:*:$mailCode").firstOrNull()
        val key = redisTemplate.keys(loginId).firstOrNull()
        return key?.let { redisTemplate.opsForValue().get(it) }
    }

    fun deleteByLoginId(loginId: String){
        val key = redisTemplate.keys(loginId)
        redisTemplate.delete(key)
    }
}