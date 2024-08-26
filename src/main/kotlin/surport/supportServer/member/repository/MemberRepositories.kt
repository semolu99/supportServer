package surport.supportServer.member.repository

import surport.supportServer.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import surport.supportServer.member.entity.Mail
import surport.supportServer.member.entity.MemberRole
import surport.supportServer.member.entity.RefreshToken

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
    fun deleteByLoginId(loginId: String)
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long> {
    fun findByMember(member: Member?): MemberRole
    fun deleteByMember(member: Member?)
}

interface MailRepository : JpaRepository<Mail, Long> {
    fun findAllByLoginId(loginId: String) : List<Mail>?
    fun deleteByLoginId(loginId: String) : List<Mail>?
}

interface RefreshTokenIngoRepository : JpaRepository<RefreshToken, Long>{
    fun deleteByUserId(usrId:Long) : RefreshToken
    fun findByRefreshToken(refreshToken: String): RefreshToken?
    fun deleteByRefreshToken(refreshToken: String): RefreshToken
}