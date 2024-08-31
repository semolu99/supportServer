package surport.supportServer.common.mail

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import surport.supportServer.member.dto.MailDto
import surport.supportServer.member.repository.MemberRepository
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

@Component
class MailUtility(
    private val mailSender: JavaMailSender,
    private val memberRepository: MemberRepository,
) {
    fun mailmaker(loginId:String): String {
        return "${loginId}@icloud.com"
    }
    fun getRandomString(): String {
        val length = 6
        return (UUID.randomUUID().toString()).substring(0,length)
    }
    @Async
    fun sendMail(mailDto: MailDto) : String {
        val randomString = getRandomString()

        val content = "SUPPORT 이메일 인증<br><h2>인증번호 : ${randomString}</h2>이용 해 주셔서 감사합니다"

        val mimeMessage = mailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(mimeMessage, false, "UTF-8")
        mimeMessageHelper.setFrom("supportofficial@naver.com")
        mimeMessageHelper.setTo(mailmaker(mailDto.loginId))
        mimeMessageHelper.setSubject("[Support] 이메일 인증 메일")
        mimeMessageHelper.setText(content,true)
        println(now())
        mailSender.send(mimeMessage)
        println(now())
        return randomString
    }
    fun getTempPassword(): String {
        val charSet = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        )
        var str = ""

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        var idx = 0
        for (i in 0..9) {
            idx = (charSet.size * Math.random()).toInt()
            str += charSet[idx]
        }
        return str
    }

    fun sendPassword(loginId : String):String{
        val newPassword = getTempPassword()
        val title = "[Support] 임시 비밀번호 발급"
        val content = "SUPPORT 임시 비밀번호 발급<br><h2>임시 비밀번호 : ${newPassword}</h2>보안을 위해 바로 비밀번호를 변경 해주세요.<br>이용 해 주셔서 감사합니다"

        val mimeMessage = mailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(mimeMessage, false, "UTF-8")
        mimeMessageHelper.setFrom("supportofficial@naver.com")
        mimeMessageHelper.setTo(mailmaker(loginId))
        mimeMessageHelper.setSubject(title)
        mimeMessageHelper.setText(content,true)
        mailSender.send(mimeMessage)

        return newPassword
    }
}