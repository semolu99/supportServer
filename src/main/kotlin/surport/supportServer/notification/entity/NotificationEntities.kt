package surport.supportServer.notification.entity

import jakarta.persistence.*
import surport.supportServer.notification.dto.NotificationDtoResponse
import surport.supportServer.notification.dto.NotificationListDtoResponse
import surport.supportServer.notification.dto.ScheduleDtoResponse
import java.time.LocalDate

@Entity
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column(nullable = false, length = 1000)
    val content: String,

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.DATE)
    val startDate: LocalDate,

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.DATE)
    val endDate: LocalDate,
) {
    fun toDto(): ScheduleDtoResponse =
        ScheduleDtoResponse(id!!, title, content, startDate, endDate)

}

@Entity
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?= null,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column(nullable = false, length = 1000)
    val content: String,

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.DATE)
    val creationDate: LocalDate,
) {
    fun toDto(): NotificationDtoResponse =  // 수정된 부분
        NotificationDtoResponse(id!!, title, content, creationDate)
    fun toList(): NotificationListDtoResponse =
        NotificationListDtoResponse(id!!, title, creationDate)
}

