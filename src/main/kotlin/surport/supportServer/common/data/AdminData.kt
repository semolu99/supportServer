package surport.supportServer.common.data

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.stereotype.Component
import surport.supportServer.common.status.Dorm_type
import surport.supportServer.common.status.Gender
import surport.supportServer.common.status.ROLE
import surport.supportServer.member.entity.Member
import surport.supportServer.member.entity.MemberRole
import surport.supportServer.member.repository.MemberRepository
import surport.supportServer.member.repository.MemberRoleRepository

@Component
@Transactional
class AdminData(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,

    @Value("\${admin.loginId}")
    private val loginId: String,

    @Value("\${admin.password}")
    private val password: String,

) : CommandLineRunner {

    override fun run(vararg args: String?) {

        // ADMIN 역할을 가진 멤버가 존재하는지 확인
        if (memberRepository.findByLoginId("admin") == null) {
            val encoder = SCryptPasswordEncoder(16,8,1,32,64)
            val encodedPassword = encoder.encode(password)

            // ADMIN 멤버 생성
            val adminMember = Member(null, loginId,encodedPassword,"Admin",Gender.MAN, Dorm_type.GounA)
            val adminRole = MemberRole(null,ROLE.ADMIN, adminMember)
            memberRepository.save(adminMember)
            memberRoleRepository.save(adminRole)
        }
    }
}
