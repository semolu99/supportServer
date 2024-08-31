package surport.supportServer.notification.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.entity.Notification
import org.springframework.stereotype.Service
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.common.status.ResultCode
import surport.supportServer.notification.dto.NotificationDateDto
import surport.supportServer.notification.dto.NotificationDtoResponse
import surport.supportServer.notification.repository.NotificationRepository
import java.time.LocalDate

@Transactional
@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {
    /**
     * 포스트 작성
     */
    fun addNotification(notificationDto: NotificationDto): String {
        val notification :Notification = notificationDto.toEntity()
        notificationRepository.save(notification)
        return "정상 작동"
    }

    /**
     * 특정 공지사항 보기
     */
    fun notificationView(id: Long): NotificationDtoResponse{
        val notification = notificationRepository.findAllById(id)
            ?: throw InvalidInputException(statusCode = ResultCode.BAD_REQUEST.statusCode,statusMessage = "없는 게시물(${id}) 입니다.", code = ResultCode.BAD_REQUEST.code)
        return notification.toDto()
    }
    /**
     * 해당 월 리스트 뽑아오기
     */
    fun getMonthNotifications(notificationDateDto: NotificationDateDto): BaseResponse<List<Notification>>{
        val notificationList = notificationRepository.findAllByStartDateAndEndDate(notificationDateDto.startDate,notificationDateDto.endDate)
        return BaseResponse(data = notificationList)
    }

}