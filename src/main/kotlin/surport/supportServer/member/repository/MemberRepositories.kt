package surport.supportServer.member.repository

import surport.supportServer.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import surport.supportServer.member.entity.MemberRole

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>