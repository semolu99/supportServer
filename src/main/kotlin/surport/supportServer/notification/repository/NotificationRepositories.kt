package surport.supportServer.notification.repository

import org.springframework.data.jpa.repository.JpaRepository
import surport.supportServer.notification.entity.Notification

interface NotificationRepository : JpaRepository<Notification, Long>
