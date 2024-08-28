package surport.supportServer.member.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.common.dto.CustomUser
import surport.supportServer.member.dto.AddAdminDto
import surport.supportServer.member.service.AdminService

@RequestMapping("/admin")
@RestController
class AdminController(
    private val adminService: AdminService
) {
    /**
     * 어드민 부여
     */
    @PostMapping("/add")
    fun addAdmin(@RequestBody @Valid addAdminDto: AddAdminDto): BaseResponse<Unit> {
        val response = adminService.addAdmin(addAdminDto.loginId)
        return BaseResponse(statusMessage = response)
    }
}