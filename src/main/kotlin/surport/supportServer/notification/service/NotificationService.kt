package surport.supportServer.notification.service

import jakarta.transaction.Transactional
import surport.supportServer.notification.dto.ScheduleDto
import surport.supportServer.notification.entity.Schedule
import org.springframework.stereotype.Service
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.common.status.ResultCode
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.dto.NotificationDtoResponse
import surport.supportServer.notification.dto.NotificationListDtoResponse
import surport.supportServer.notification.dto.ScheduleDtoResponse
import surport.supportServer.notification.entity.Notification
import surport.supportServer.notification.repository.NotificationRepository
import surport.supportServer.notification.repository.NoticeRepository
import surport.supportServer.notification.repository.ScheduleRepository

@Transactional
@Service
class NotificationService(
    private val scheduleRepository: ScheduleRepository,
    private val notificationRepository: NotificationRepository
    private val scheduleRepository: ScheduleRepository,
    private val noticeRepository: NoticeRepository
) {
    /**
     * 스케줄 작성
     */
    fun addSchedule(scheduleDto: ScheduleDto): String {
        val schedule :Schedule = scheduleDto.toEntity()
        scheduleRepository.save(schedule)
        return "정상 작동"
    }

    /**
     * 특정 스케줄 보기
     */
    fun getSchedule(id: Long): ScheduleDtoResponse{
        val notification = scheduleRepository.findAllById(id)
            ?: throw InvalidInputException(statusCode = ResultCode.NOT_FIND_SCHEDULE.statusCode,statusMessage = ResultCode.NOT_FIND_SCHEDULE.message, code = ResultCode.NOT_FIND_SCHEDULE.code)
        return notification.toDto()
    }

    /**
     * 해당 월 리스트 뽑아오기
     */
    fun getMonthSchedule(date: String): List<Schedule> {
        return scheduleRepository.findAllByDate(date)
            ?: throw InvalidInputException(statusCode = ResultCode.NOT_FIND_SCHEDULE.statusCode,statusMessage = ResultCode.NOT_FIND_SCHEDULE.message, code = ResultCode.NOT_FIND_SCHEDULE.code)
    }

    /**
     * 특정 공지 꺼내기
     */
    fun getNotification(id: Long): NotificationDtoResponse{
        val notification = noticeRepository.findAllById(id)
            ?: throw InvalidInputException(statusCode = ResultCode.NOT_FIND_SCHEDULE.statusCode,statusMessage = ResultCode.NOT_FIND_SCHEDULE.message, code = ResultCode.NOT_FIND_SCHEDULE.code)
        return notification.toDto()
    }

    /**
     * 공지 전체 리스트 꺼내기
     */
    fun getNotificationList(): List<NotificationListDtoResponse>{
        return noticeRepository.findAllNotificationListDtoResponse()
            ?: throw InvalidInputException(statusCode = ResultCode.NOT_FIND_SCHEDULE.statusCode,statusMessage = ResultCode.NOT_FIND_SCHEDULE.message, code = ResultCode.NOT_FIND_SCHEDULE.code)

    /**
     *  공지사항 작성
     */
    fun addNotification(notificationDto: NotificationDto): String {
        val notification: Notification = notificationDto.toEntity() // 수정된 부분
        notificationRepository.save(notification)
        return "정상 작동"
    }

    /**
     *  공지사항 삭제
     */
    fun deleteNotification(id: Long): String{
        notificationRepository.deleteById(id)
        return "삭제 완료"
    }
    }
}