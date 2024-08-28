package surport.supportServer.member.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.common.status.ROLE
import surport.supportServer.common.status.ResultCode
import surport.supportServer.member.entity.Member
import surport.supportServer.member.entity.MemberRole
import surport.supportServer.member.repository.MemberRepository
import surport.supportServer.member.repository.MemberRoleRepository


@Transactional
@Service
class AdminService(
    private val memberRoleRepository: MemberRoleRepository,
    private val memberRepository: MemberRepository,
) {
    /**
     * 어드민 부여
     */
    fun addAdmin(loginId:String): String{
        var member : Member? = memberRepository.findByLoginId(loginId)
            ?: throw throw InvalidInputException(ResultCode.NOT_FIND_ID.statusCode, ResultCode.NOT_FIND_ID.message, ResultCode.NOT_FIND_ID.code)
        val tempMemberRole: MemberRole = memberRoleRepository.findByMember(member)
        val memberRole:MemberRole = MemberRole(tempMemberRole.id, ROLE.ADMIN, tempMemberRole.member)
        memberRoleRepository.save(memberRole)

        return "${member?.nickname}님의 권한을 관리자로 변경 했습니다."
    }
}