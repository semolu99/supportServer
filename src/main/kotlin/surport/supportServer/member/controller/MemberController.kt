package surport.supportServer.member.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import surport.supportServer.member.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import surport.supportServer.common.authority.TokenInfo
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.common.dto.CustomUser
import surport.supportServer.member.dto.*

@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
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
        return BaseResponse(message = resultMsg)
    }

    @PutMapping("/info/password")
    fun changePassword(@RequestBody @Valid passwordChangeDto: PasswordChangeDto): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val resultMsg = memberService.changePassword(userId, passwordChangeDto.currentPassword, passwordChangeDto.newPassword)
        return BaseResponse(message = resultMsg)
    }


}