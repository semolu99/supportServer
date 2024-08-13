package surport.supportServer.member.entity

import surport.supportServer.common.status.Dorm_type
import surport.supportServer.common.status.Gender
import jakarta.persistence.*
import jakarta.validation.constraints.Min

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val nickname: String,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false)
    val admin: Boolean,

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    val dormType: Dorm_type,

    @Column(nullable = false)
    @Min(value = 1)
    val dormNo: Int,

    @Column(nullable = true)
    val roomNo: Int? = null,
)