package surport.supportServer.notification.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.entity.Notification
import org.springframework.stereotype.Service
import surport.supportServer.common.exception.InvalidInputException
import surport.supportServer.notification.dto.NotificationDtoResponse
import surport.supportServer.notification.repository.NotificationRepository
import java.time.LocalDate

@Transactional
@Service
class NotificationService(private val notificationRepository: NotificationRepository) {
//
//    fun addNotification(notificationDto: NotificationDto): String {
//        val notification :Notification = notificationDto.toEntity()
//        notificationRepository.save(notification)
//        return "정상 작동"
//    }
//
////    fun addNotification(notificationDto: NotificationDto): String {
////        val notification: Notification = notificationDto.toEntity()
////        return "정상 작동"
////    }
//
//
//
////    fun listNotifications(local: String, startDate: LocalDate): List<Notification> {
////        val notifications = notificationRepository.findByLocalAndStartDate(local , startDate)
////            ?: throw InvalidInputException("month and startDate","게시물이 없습니다.")
////        return notifications
////    }
//
//    fun notificationView(id: Long): NotificationDtoResponse{
//        val notification = notificationRepository.findByIdOrNull(id)
//            ?: throw InvalidInputException("id","없는 게시물(${id}) 입니다.")
//        return notification.toDto()
//    }

}