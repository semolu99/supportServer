package surport.supportServer.notification.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import surport.supportServer.common.dto.BaseResponse
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


}