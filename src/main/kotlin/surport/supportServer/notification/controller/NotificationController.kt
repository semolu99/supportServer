package surport.supportServer.notification.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.dto.NotificationDtoResponse
import surport.supportServer.notification.entity.Notification
import surport.supportServer.notification.service.NotificationService


@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
//    @PostMapping("/notifications")
//    fun addNotification(@RequestBody @Valid notificationDto: NotificationDto): BaseResponse<Unit> {
//        val result = notificationService.addNotification(notificationDto)
//        return BaseResponse(message = result)
//    }
//
//    @GetMapping("/notifications/{id}")
//    fun getNotification(@PathVariable id: Long):BaseResponse<NotificationDtoResponse> {
//        val result = notificationService.notificationView(id)
//        return BaseResponse(data = result, message = "정상 작동")
//    }
}
