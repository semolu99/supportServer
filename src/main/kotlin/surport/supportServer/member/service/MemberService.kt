package surport.supportServer.member.service

import jakarta.transaction.Transactional
import surport.supportServer.member.dto.MemberDtoRequest
import surport.supportServer.member.entity.Member
import surport.supportServer.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest):String{
        //ID 중복 검사 나중에 평션 새로 만들기
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if(member != null){
            return "이미 등록된 ID입니다."
        }

        member = Member(
            null,
            memberDtoRequest.loginId,
            memberDtoRequest.password,
            memberDtoRequest.nickname,
            memberDtoRequest.gender,
            memberDtoRequest.admin,
            memberDtoRequest.dormType,
            memberDtoRequest.dormNo,
            memberDtoRequest.roomNo
        )

        memberRepository.save(member)

        return "회원가입이 완료 되었습니다."
    }
}