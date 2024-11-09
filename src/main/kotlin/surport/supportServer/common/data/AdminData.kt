package surport.supportServer.common.data

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import surport.supportServer.member.entity.Member
import surport.supportServer.member.entity.MemberRole
import surport.supportServer.common.status.ROLE
import surport.supportServer.common.status.Gender
import surport.supportServer.common.status.Dorm_type
import surport.supportServer.member.repository.MemberRepository
import surport.supportServer.member.repository.MemberRoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder

@Component
@Transactional
class AdminData(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val passwordEncoder: PasswordEncoder,

    @Value("\${admin.loginId}")
    private var loginId: String,

    @Value("\${admin.password}")
    private var password: String,

) : CommandLineRunner {

    override fun run(vararg args: String?) {

        // ADMIN 역할을 가진 멤버가 존재하는지 확인
        if (memberRepository.findByLoginId("admin") == null) {
            val encoder = SCryptPasswordEncoder(16,8,1,32,64)
            val encodedPassword = encoder.encode(password)

            // ADMIN 멤버 생성
            val adminMember = Member(
                loginId = loginId,
                password = encodedPassword, // 비밀번호는 안전하게 관리하세요
                nickname = "Admin",
                gender = Gender.MAN, // 적절한 Gender 값을 사용하세요
                dormType = Dorm_type.GounA, // 적절한 Dorm_type 값을 사용하세요
                dormNo = 1,
                roomNo = 1
            )

            // ADMIN 멤버를 저장
            val savedMember = memberRepository.save(adminMember)

            // ADMIN 역할 생성
            val adminRole = MemberRole(
                role = ROLE.ADMIN,
                member = savedMember
            )

            // ADMIN 역할을 저장
            memberRoleRepository.save(adminRole)
        }
    }
}
