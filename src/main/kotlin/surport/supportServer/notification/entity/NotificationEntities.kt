package surport.supportServer.notification.entity

import jakarta.persistence.*
import surport.supportServer.notification.dto.NotificationListDtoResponse
import surport.supportServer.notification.dto.ScheduleDtoResponse
import java.time.LocalDate

@Entity
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column(nullable = false, length = 1000)
    var content: String,

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.DATE)
    var startDate: LocalDate,

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.DATE)
    var endDate: LocalDate,

    @Column(nullable = false, length = 1)
    @Temporal(TemporalType.DATE)
    var color: Int,
) {
    fun toDto(): ScheduleDtoResponse =
        ScheduleDtoResponse(id!!, title, content, startDate, endDate, color)

}

@Entity
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?= null,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column(nullable = false, length = 1000)
    var content: String,

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.DATE)
    val creationDate: LocalDate,
) {
    fun toList(): NotificationListDtoResponse =
        NotificationListDtoResponse(id!!, title, content, creationDate)
}

