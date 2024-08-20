package surport.supportServer.common.service

import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration
import java.util.*

class RedisUtils(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    private val DURATION_TIME = 1000 * 60 * 5L

    fun getData(key: String): String? {
        val valueOperations = redisTemplate.opsForValue()
        return valueOperations[key]
    }

    fun setDataExpire(key: String, value: String) {
        val valueOperations = redisTemplate.opsForValue()
        valueOperations.set(key, value, Duration.ofMillis(DURATION_TIME))
    }

    fun deleteData(key: String) {
        redisTemplate.delete(key)
    }


}