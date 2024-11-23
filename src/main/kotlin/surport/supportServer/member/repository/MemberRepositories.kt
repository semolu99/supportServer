package surport.supportServer.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import surport.supportServer.member.entity.Member
import surport.supportServer.member.entity.MemberRole

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
    fun deleteByLoginId(loginId: String)
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long> {
    fun findByMember(member: Member?): MemberRole
    fun deleteByMember(member: Member?)
}