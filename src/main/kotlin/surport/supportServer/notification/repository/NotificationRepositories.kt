package surport.supportServer.notification.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import surport.supportServer.notification.entity.Notification
import surport.supportServer.notification.dto.NotificationListDtoResponse
import surport.supportServer.notification.entity.Schedule

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Long>{
    fun findAllById(id:Long): Schedule?

    //@Query("SELECT e FROM Notification e WHERE e.startDate >= :startDate OR e.endDate <= :endDate")
    @Query("SELECT * FROM schedule e where e.id in (select notification_ids.id from(select i.id ,substring(i.start_date,1,7) as sd, substring(i.end_date ,1,7) as ed from schedule i) notification_ids where notification_ids.sd = :date or notification_ids.ed = :date)", nativeQuery = true)
    fun findAllByDate(date: String): List<Schedule>?
}

@Repository
interface NoticeRepository : JpaRepository<Notification, Long>{
    fun findAllById(id:Long): Notification?

    @Query("SELECT new surport.supportServer.notification.dto.NotificationListDtoResponse(n.id, n.title, n.creationDate) FROM Notification n")
    fun findAllNotificationListDtoResponse(): List<NotificationListDtoResponse>?

    fun deleteById(id: Long?)
}