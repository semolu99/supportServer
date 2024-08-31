package surport.supportServer.notification.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.notification.dto.NotificationDateDto
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.dto.NotificationDtoResponse
import surport.supportServer.notification.entity.Notification
import surport.supportServer.notification.service.NotificationService


@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    /**
     * 포스트 작성
     */
    @PostMapping("/notifications")
    fun addNotification(@RequestBody @Valid notificationDto: NotificationDto): BaseResponse<Unit> {
        val result = notificationService.addNotification(notificationDto)
        return BaseResponse(statusMessage = result)
    }

    /**
     * 특정 공지사항 꺼내기
     */
    @GetMapping("/notifications/{id}")
    fun getNotification(@PathVariable id: Long):BaseResponse<NotificationDtoResponse> {
        val result = notificationService.notificationView(id)
        return BaseResponse(data = result, statusMessage = "정상 작동")
    }
    /**
     * 공지 월별 리스트
     */
    @GetMapping("/notifications")
    fun getNotifications(@RequestBody notificationDateDto: NotificationDateDto): BaseResponse<List<Notification>> {
        return notificationService.getMonthNotifications(notificationDateDto)
    }
}
