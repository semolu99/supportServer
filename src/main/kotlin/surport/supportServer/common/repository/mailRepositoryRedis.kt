package surport.supportServer.common.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class MailRepositoryRedis(
    private val mailRedisTemplate: RedisTemplate<String, String>,
    private val checkedRedisTemplate : RedisTemplate<String, Boolean>
) {
    companion object{
        private const val KEY_PREFIX = "mail"
    }
    private val timeOutMail: Long = 1000 * 60 * 10
    private val timeOutMailChecked:Long = 1000 * 60 * 30

    fun saveMail(loginId: String, mailCode: String){
        mailRedisTemplate.opsForValue().set(loginId, mailCode, timeOutMail, TimeUnit.MILLISECONDS)
    }

    fun saveChecked(loginId: String){
        val checked = false
        checkedRedisTemplate.opsForValue().set(loginId, checked, timeOutMailChecked, TimeUnit.MILLISECONDS)
    }

    fun findByMailCode(loginId: String) :String? {
        //val key = redisTemplate.keys("$KEY_PREFIX:*:$mailCode").firstOrNull()
        val key = mailRedisTemplate.keys(loginId).firstOrNull()
        return key?.let { mailRedisTemplate.opsForValue().get(it) }
    }

    fun deleteMailByLoginId(loginId: String){
        val key = mailRedisTemplate.keys(loginId)
        mailRedisTemplate.delete(key)
        saveChecked(loginId)
    }

    fun deleteCheckByLoginId(loginId: String){
        val key = mailRedisTemplate.keys(loginId)
        mailRedisTemplate.delete(key)
    }

}