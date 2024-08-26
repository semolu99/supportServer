package surport.supportServer.common.mail

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import surport.supportServer.member.dto.MailDto
import surport.supportServer.member.repository.MemberRepository
import java.util.*

@Component
class MailUtility(
    private val mailSender: JavaMailSender,
    private val memberRepository: MemberRepository,
) {
    fun getRandomString(): String {
        val length = 6
        return (UUID.randomUUID().toString()).substring(0,length)
    }
    fun sendMail(mailDto: MailDto) : String {
        val randomString = getRandomString()

        val content = "SUPPORT 이메일 인증<br><h2>인증번호 : ${randomString}</h2><br>이용 해 주셔서 감사합니다"
        val suwonmail = mailDto.loginId + "@gmail.com"

        val mimeMessage = mailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(mimeMessage, false, "UTF-8")
        mimeMessageHelper.setFrom("supportofficial@naver.com")
        mimeMessageHelper.setTo(suwonmail)
        mimeMessageHelper.setSubject("[Support] 이메일 인증 메일")
        mimeMessageHelper.setText(content,true)
        mailSender.send(mimeMessage)

        return randomString
    }
}