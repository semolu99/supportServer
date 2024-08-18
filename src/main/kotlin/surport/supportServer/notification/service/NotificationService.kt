package surport.supportServer.notification.service

import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.entity.Notification
import surport.supportServer.notification.repository.NotificationRepository


import org.springframework.stereotype.Service

@Service
class NotificationService(private val notificationRepository: NotificationRepository) {

    fun addNotification(notificationDto: NotificationDto) {
        val notification = Notification(
            title = notificationDto.title,
            content = notificationDto.content,
            startDate = notificationDto.startDate,
            endDate = notificationDto.endDate
        )
        notificationRepository.save(notification)
    }
}
