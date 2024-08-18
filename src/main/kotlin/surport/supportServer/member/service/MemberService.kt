package surport.supportServer.member.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import surport.supportServer.member.dto.MemberDtoRequest
import surport.supportServer.member.entity.Member
import surport.supportServer.member.repository.MemberRepository
import org.springframework.stereotype.Service
import surport.supportServer.common.authority.JwtTokenProvider
import surport.supportServer.common.authority.TokenInfo
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.common.status.ROLE
import surport.supportServer.member.dto.LoginDto
import surport.supportServer.member.dto.MemberDtoResponse
import surport.supportServer.member.dto.MemberUpdateDto
import surport.supportServer.member.entity.MemberRole
import surport.supportServer.member.repository.MemberRoleRepository

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider
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

        val memberRole: MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료 되었습니다."
    }

    /**
     * 로그인 -> 토큰발행
     */
    fun login(loginDto: LoginDto): TokenInfo{
        //암호화된 비밀번호와의 대조
        var member: Member? = memberRepository.findByLoginId(loginDto.loginId)
            ?: throw InvalidInputException("Input", "아이디와 비밀번호를 확인하세요")
        val encoder = SCryptPasswordEncoder(16,8,1,32,64)
        if(!encoder.matches(loginDto.password,member?.password)){
            throw InvalidInputException("Input", "아이디와 비밀번호를 확인하세요")
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, member?.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     * 내 정보 조회
     */
    fun searchMyInfo(id: Long): MemberDtoResponse{
        val member : Member = memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원 번호(${id})가 존재 하지 않는 유저입니다.")
        return member.toDto()
    }

    /**
     * 내 정보 수정
     */
    fun saveMyInfo(memberUpdateDto: MemberUpdateDto): String {
        val existingMember = memberRepository.findById(memberUpdateDto.id)
            .orElseThrow { RuntimeException("회원 정보를 찾을 수 없습니다.") }

        memberUpdateDto.nickname?.let { existingMember.nickname = it }
        memberUpdateDto.gender?.let { existingMember.gender = it }
        memberUpdateDto.admin?.let { existingMember.admin = it }
        memberUpdateDto.dormType?.let { existingMember.dormType = it }
        memberUpdateDto.dormNo?.let { existingMember.dormNo = it }
        memberUpdateDto.roomNo?.let { existingMember.roomNo = it }

        memberRepository.save(existingMember)
        return "정보가 성공적으로 업데이트되었습니다."
    }
    /**
     * 비밀 번호 변경
     */
    fun changePassword(userId: Long, currentPassword: String, newPassword: String): String {
        val member = memberRepository.findById(userId).orElseThrow {
            RuntimeException("회원 정보를 찾을 수 없습니다.")
        }
        val encoder = SCryptPasswordEncoder(16,8,1,32,64)
        if (!encoder.matches(currentPassword, member.password)) {
            throw RuntimeException("현재 비밀 번호가 맞지 않습니다.")
        }
        member.password = encoder.encode(newPassword)
        memberRepository.save(member)
        return "비밀 번호 변경 완료 되었습니다."
    }


}