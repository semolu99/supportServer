package surport.supportServer.notification.entity

import jakarta.persistence.*
import surport.supportServer.notification.dto.ScheduleDtoResponse

import java.time.LocalDate

@Entity
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    val endDate: LocalDate,
) {
    fun toDto(): ScheduleDtoResponse =
        ScheduleDtoResponse(id!!, title, content, startDate, endDate)

}
