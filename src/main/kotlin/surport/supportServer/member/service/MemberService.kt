package surport.supportServer.member.service

import jakarta.transaction.Transactional
import surport.supportServer.member.dto.MemberDtoRequest
import surport.supportServer.member.entity.Member
import surport.supportServer.member.repository.MemberRepository
import org.springframework.stereotype.Service
import surport.supportServer.common.exception.InvalidInputException

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest):String {
        //ID 중복 검사 나중에 평션 새로 만들기
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        return "회원가입이 완료 되었습니다."
    }
}