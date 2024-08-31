package surport.supportServer.notification.entity

import jakarta.persistence.*
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.dto.NotificationDtoResponse

import java.time.LocalDate

@Entity
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val startDate: LocalDate,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val endDate: LocalDate
) {
    fun toDto(): NotificationDtoResponse =
        NotificationDtoResponse(id!!, title, content, startDate, endDate)

}
