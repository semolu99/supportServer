package surport.supportServer.notification.service

import jakarta.transaction.Transactional
import surport.supportServer.notification.dto.ScheduleDto
import surport.supportServer.notification.entity.Schedule
import org.springframework.stereotype.Service
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.common.status.ResultCode
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.dto.NotificationListDtoResponse
import surport.supportServer.notification.dto.ScheduleDtoResponse
import surport.supportServer.notification.entity.Notification
import surport.supportServer.notification.repository.NoticeRepository
import surport.supportServer.notification.repository.ScheduleRepository

@Transactional
@Service
class NotificationService(
    private val scheduleRepository: ScheduleRepository,
    private val noticeRepository: NoticeRepository
) {
    /**
     * 스케줄 작성
     */
    fun addSchedule(scheduleDto: ScheduleDto): String {
        val schedule :Schedule = scheduleDto.toEntity()
        scheduleDto.dateCompare(scheduleDto.startDate,scheduleDto.endDate)
        scheduleRepository.save(schedule)
        return "정상 작동"
    }

    /**
     * 특정 스케줄 수정
     */
    fun putSchedule(id: Long, scheduleDto: ScheduleDto): String{
        val existingSchedule = scheduleRepository.findById(id)
            .orElseThrow { InvalidInputException(ResultCode.NOT_FIND_SCHEDULE.statusCode, ResultCode.NOT_FIND_SCHEDULE.message, ResultCode.NOT_FIND_SCHEDULE.code) }

        scheduleDto.title?.let { existingSchedule.title = it }
        scheduleDto.content?.let { existingSchedule.content = it }
        scheduleDto.startDate?.let { existingSchedule.startDate = it }
        scheduleDto.endDate?.let { existingSchedule.endDate = it }
        scheduleDto.color?.let { existingSchedule.color = it }

        scheduleRepository.save(existingSchedule)

        return "정상 작동"
    }
    /**
     * 특정 스케줄 보기
     */
    fun getSchedule(id: Long): ScheduleDtoResponse{
        val notification = scheduleRepository.findAllById(id)
            ?: throw InvalidInputException(ResultCode.NOT_FIND_SCHEDULE.statusCode,ResultCode.NOT_FIND_SCHEDULE.message,ResultCode.NOT_FIND_SCHEDULE.code)
        return notification.toDto()
    }

    /**
     * 해당 월 리스트 뽑아오기
     */
    fun getMonthSchedule(date: String): List<Schedule> {
        return scheduleRepository.findAllByDate(date)
            ?: throw InvalidInputException(ResultCode.NOT_FIND_SCHEDULE.statusCode,ResultCode.NOT_FIND_SCHEDULE.message,ResultCode.NOT_FIND_SCHEDULE.code)
    }

    /**
     *  스케줄 삭제
     */
    fun deleteSchedule(id: Long): String{
        val Schedule = scheduleRepository.findScheduleById(id)
            ?: throw InvalidInputException(ResultCode.NOT_FIND_SCHEDULE.statusCode,ResultCode.NOT_FIND_SCHEDULE.message, ResultCode.NOT_FIND_SCHEDULE.code)
        scheduleRepository.deleteById(id)
        return "삭제 완료"
    }

    /**
     * 특정 공지 수정
     */
    fun putNotification(id: Long, notificationDto: NotificationDto):String{
        val existingNotification = noticeRepository.findById(id)
            .orElseThrow { InvalidInputException(ResultCode.NOT_FIND_NOTIFICATION.statusCode, ResultCode.NOT_FIND_NOTIFICATION.message, ResultCode.NOT_FIND_NOTIFICATION.code) }

        notificationDto.title?.let { existingNotification.title = it }
        notificationDto.content?.let { existingNotification.content = it }

        noticeRepository.save(existingNotification)
        return "정상 작동"
    }
    /**
     * 공지 전체 리스트 꺼내기
     */
    fun getNotificationList(): List<NotificationListDtoResponse> {
        return noticeRepository.findAllNotificationListDtoResponse()
            ?: throw InvalidInputException(ResultCode.NOT_FIND_NOTIFICATION.statusCode, ResultCode.NOT_FIND_NOTIFICATION.message, ResultCode.NOT_FIND_NOTIFICATION.code)
    }
    /**
     *  공지사항 작성
     */
    fun addNotification(notificationDto: NotificationDto): String {
        val notification: Notification = notificationDto.toEntity() // 수정된 부분
        noticeRepository.save(notification)
        return "정상 작동"
    }
    /**
     *  공지사항 삭제
     */
    fun deleteNotification(id: Long): String{
        val notification = noticeRepository.findNotificationById(id)
            ?: throw InvalidInputException(ResultCode.NOT_FIND_NOTIFICATION.statusCode,ResultCode.NOT_FIND_NOTIFICATION.message, ResultCode.NOT_FIND_NOTIFICATION.code)
        noticeRepository.deleteById(id)
        return "삭제 완료"
    }
}