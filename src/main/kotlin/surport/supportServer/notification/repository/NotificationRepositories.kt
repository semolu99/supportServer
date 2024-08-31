package surport.supportServer.notification.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import surport.supportServer.notification.entity.Notification
import java.time.LocalDate

@Repository
interface NotificationRepository : JpaRepository<Notification, Long>{
    fun findAllById(id:Long): Notification?

    @Query("SELECT e FROM Notification e WHERE e.startDate >= :startDate AND e.endDate <= :endDate")
    fun findAllByStartDateAndEndDate(startDate:LocalDate, endDate:LocalDate):List<Notification>
}

