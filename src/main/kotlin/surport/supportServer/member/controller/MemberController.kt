package surport.supportServer.member.controller

import jakarta.validation.Valid
import org.bouncycastle.asn1.pkcs.CertificationRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import surport.supportServer.member.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import surport.supportServer.common.authority.TokenInfo
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.common.dto.CustomUser
import surport.supportServer.member.dto.*
//import surport.supportServer.member.repository.MailRepository

@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService,
) {
    /**
     * 테스트
     */
    @GetMapping("/test")
    fun gettest():String{
        return "ddd"
    }
    /**
     * 이메일 인증
     */
    @PostMapping("/mail")
    fun sendMail(@RequestBody @Valid mailDto: MailDto): BaseResponse<Unit> {
        val resultMsg: String = memberService.sendMail(mailDto)
        return BaseResponse(statusMessage = resultMsg)
    }

    /**
     * 이메일 검증
     */
    @PostMapping("/mailcheck")
    fun mailCheck(@RequestBody @Valid mailCheckDto: MailCheckDto): BaseResponse<Unit> {
        val resultMsg: String = memberService.mailCheck(mailCheckDto.loginId, mailCheckDto.authCode)
        return BaseResponse(statusMessage = resultMsg)
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(statusMessage = resultMsg)
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo>{
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data= tokenInfo)
    }

    /**
     * Refresh 토큰
     */
    @PostMapping("/token/refresh")
    fun tokenRefresh(@RequestBody @Valid tokenRefreshDto: TokenRefreshDto): BaseResponse<TokenInfo>{
        val tokenInfo: TokenInfo = memberService.validateRefreshTokenAndCreateToken(tokenRefreshDto.refreshToken)
        return BaseResponse(data = tokenInfo)
    }

    /**
     * 내 정보 보기
     */
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<MemberDtoResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val response = memberService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }

    /**
     * 내 정보 수정
     */
    @PutMapping("/info")
    fun saveMyInfo(@RequestBody @Valid memberUpdateDto: MemberUpdateDto): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberUpdateDto.id = userId
        val resultMsg: String = memberService.saveMyInfo(memberUpdateDto)
        return BaseResponse(statusMessage = resultMsg)
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/info/password")
    fun changePassword(@RequestBody @Valid passwordChangeDto: PasswordChangeDto): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val resultMsg = memberService.changePassword(userId, passwordChangeDto.currentPassword, passwordChangeDto.newPassword)
        return BaseResponse(statusMessage = resultMsg)
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    fun logOut():BaseResponse<Unit>{
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberService.deleteAllRefreshToken(userId)
        return BaseResponse()
    }

    /**
     * 비밀번호 찾기
     */
    @PostMapping("/mail/find")
    fun findPassword(@RequestParam("loginId")loginId:String): BaseResponse<Unit>{
        val resultMsg:String = memberService.findPassword(loginId)
        return BaseResponse(statusMessage = resultMsg)
    }
    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/delete")
    fun deleteMember():BaseResponse<Unit>{
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val resultMsg = memberService.deleteMember(userId)
        return BaseResponse(statusMessage = resultMsg)
    }

}