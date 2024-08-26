package surport.supportServer.member.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import surport.supportServer.member.entity.Member
import surport.supportServer.member.repository.MemberRepository
import org.springframework.stereotype.Service
import surport.supportServer.common.authority.JwtTokenProvider
import surport.supportServer.common.authority.TokenInfo
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.common.mail.MailUtility
import surport.supportServer.common.service.RefreshTokenIngoRepositoryRedis
import surport.supportServer.common.status.ROLE
import surport.supportServer.member.dto.*
import surport.supportServer.member.entity.Mail
import surport.supportServer.member.entity.MemberRole
import surport.supportServer.member.entity.RefreshToken
import surport.supportServer.member.repository.MailRepository
import surport.supportServer.member.repository.MemberRoleRepository
import java.time.Duration
import java.time.LocalDateTime

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val mailUtility: MailUtility,
    private val mailRepository: MailRepository,
    private val refreshTokenIngoRepository: RefreshTokenIngoRepositoryRedis,

    ) {
    /**
     * 이메일 인증
     */
    fun sendMail(mailDto: MailDto):String {
        val member: Member? = memberRepository.findByLoginId(mailDto.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        val randomString = mailUtility.sendMail(mailDto)

        mailRepository.save(
            Mail(
                loginId = mailDto.loginId,
                authCode = randomString,
                sendDate = LocalDateTime.now()
            )
        )

        return "메일을 성공적으로 보냈습니다."
    }

    /**
     * 이메일 검증
     */
    fun mailCheck(loginId: String, authCode: String):String {
        val mailList: List<Mail> = mailRepository.findAllByLoginId(loginId)
            ?: throw InvalidInputException("loginID", "일치하는 아이디를 찾을 수 없습니다")

        val now = LocalDateTime.now()

        val matchingMail = mailList.find { it.authCode == authCode }
            ?: throw InvalidInputException("authcode", "인증 코드가 틀렸습니다.")

        if (Duration.between(matchingMail.sendDate, now).toMinutes() > 15) {
            throw IllegalStateException("인증 번호가 만료된 상태입니다.")
        }

        mailRepository.deleteByLoginId(loginId)

        return "메일 인증 완료!"
    }

    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest):String {
        var member = memberDtoRequest.toEntity()

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
        var member: Member = memberRepository.findByLoginId(loginDto.loginId)
            ?: throw InvalidInputException("Input", "아이디와 비밀번호를 확인하세요")
        val encoder = SCryptPasswordEncoder(16,8,1,32,64)
        if(!encoder.matches(loginDto.password,member.password)){
            throw InvalidInputException("Input", "아이디와 비밀번호를 확인하세요")
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, member?.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val createToken: TokenInfo = jwtTokenProvider.createToken(authentication)
        val timeout =  jwtTokenProvider.getTimeoutFromRefreshToken(createToken.refreshToken)
        val userId = member.id!!
        val refreshToken =RefreshToken(null,userId, createToken.refreshToken, timeout)

        refreshTokenIngoRepository.save(userId,createToken.refreshToken)

        return createToken
    }

    /**
     * 유저의 모든 Refresh 토큰 삭제
     */
    fun deleteAllRefreshToken(userId: Long){
        refreshTokenIngoRepository.deleteByUserId(userId)
    }

    /**
     * Refresh 토큰 검증 후 토큰 재발급
     */
    fun validateRefreshTokenAndCreateToken(refreshToken: String): TokenInfo{
        refreshTokenIngoRepository.findByRefreshToken(refreshToken)
            ?: throw InvalidInputException("refreshToken", "만료 되거나 찾을 수 없는 Refresh 토큰 입니다. 재 로그인이 필요합니다.")

        val newTokenInfo: TokenInfo = jwtTokenProvider.validateRefreshTokenAndCreateToken(refreshToken)

        refreshTokenIngoRepository.deleteByRefreshToken(refreshToken)

        val userId:Long = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken)
        val timeout =  jwtTokenProvider.getTimeoutFromRefreshToken(newTokenInfo.refreshToken)

        val refreshToken =RefreshToken(null,userId, newTokenInfo.refreshToken, timeout)

        refreshTokenIngoRepository.save(userId,newTokenInfo.refreshToken)

        return newTokenInfo
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

    /**
     * 비밀번호 찾기
     */
    fun findPassword(loginId: String):String{
        var member: Member? = memberRepository.findByLoginId(loginId)
            ?: throw InvalidInputException("loginId", "없는 ID 입니다.")
        val encoder = SCryptPasswordEncoder(16,8,1,32,64)
        val newPassword = mailUtility.sendPassword(loginId)
        member?.password= encoder.encode(newPassword)

        return "정상적으로 발송 되었습니다"
    }

    /**
     * 회원 탈퇴
     */
    fun deleteMember(userId: Long): String{
        val member = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("Token","토큰 값을 확인해주세요")
        memberRoleRepository.deleteByMember(member)
        memberRepository.deleteByLoginId(member!!.loginId)
        refreshTokenIngoRepository.deleteByUserId(userId)
        return "성공적으로 탈퇴 되었습니다."
    }
}