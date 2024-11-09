package surport.supportServer.notification.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.notification.dto.*
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.dto.ScheduleDto
import surport.supportServer.notification.dto.ScheduleDtoResponse
import surport.supportServer.notification.entity.Schedule
import surport.supportServer.notification.service.NotificationService

@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    /**
     * 스케줄 작성
     */
    @PostMapping("/schedule/add")
    fun addSchedule(@RequestBody @Valid scheduleDto: ScheduleDto): BaseResponse<Unit> {
        val result = notificationService.addSchedule(scheduleDto)
        return BaseResponse(statusMessage = result)
    }

    /**
     * 특정 스케줄 꺼내기
     */
    @GetMapping("/schedule/{id}")
    fun getSchedule(@PathVariable id: Long): BaseResponse<ScheduleDtoResponse> {
        val result = notificationService.getSchedule(id)
        return BaseResponse(data = result, statusMessage = "정상 작동")
    }

    /**
     * 스케줄 월별 리스트
     */
    @GetMapping("/schedule/list")
    fun getSchedule(@RequestParam date: String): List<Schedule> {
        return notificationService.getMonthSchedule(date)
    }

    /**
     *  공지사항 작성
     */
    @PostMapping("/notification/add")
    fun addNotification(@RequestBody @Valid notificationDto: NotificationDto): BaseResponse<Unit> {
        val result = notificationService.addNotification(notificationDto)
        return BaseResponse(statusMessage = result)
    }
    /**
     * 특정 공지 꺼내기
     */

    @GetMapping("/notification/{id}")
    fun getNotification(@RequestParam id: Long): BaseResponse<NotificationDtoResponse> {
        val result = notificationService.getNotification(id)
        return BaseResponse(data = result, statusMessage = "정상 작동")
    }

    /**
     * 공지 전체 리스트 꺼내기
     */
    @GetMapping("/notification/list")
    fun getNotificationList(@RequestParam date: String): List<NotificationListDtoResponse> {
        return notificationService.getNotificationList()
    }
    /**
     *  공지사항 삭제
     */
    @DeleteMapping("/notification/{id}/delete")
    fun deleteNotification(@PathVariable id: Long): BaseResponse<Unit> {
        val result = notificationService.deleteNotification(id);
        return BaseResponse(statusMessage = result)
    }
}