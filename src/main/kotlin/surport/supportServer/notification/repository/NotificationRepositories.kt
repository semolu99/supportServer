package surport.supportServer.notification.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import surport.supportServer.notification.entity.Notification
import java.time.LocalDate

@Repository
interface NotificationRepository : JpaRepository<Notification, Long>{
//    fun findAllBy(): List<Notification>
}

