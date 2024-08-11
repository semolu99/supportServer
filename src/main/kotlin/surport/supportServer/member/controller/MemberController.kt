package surport.supportServer.member.controller

import surport.supportServer.member.dto.MemberDtoRequest
import surport.supportServer.member.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/hello")
    fun hello():String{
        return "hello"
    }
    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@RequestBody memberDtoRequest: MemberDtoRequest): String{
        return memberService.signUp(memberDtoRequest)
    }
}