package surport.supportServer.member.entity

import surport.supportServer.common.status.Dorm_type
import surport.supportServer.common.status.Gender
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import surport.supportServer.common.status.ROLE
import surport.supportServer.member.dto.MemberDtoResponse
import java.time.LocalDate
import java.time.LocalDateTime

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

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false, length = 10)
    var nickname: String,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender: Gender,

    @Column(nullable = false)
    var admin: Boolean,

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    var dormType: Dorm_type,

    @Column(nullable = false)
    @Min(value = 1)
    var dormNo: Int,

    @Column(nullable = true)
    var roomNo: Int? = null,
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null

    fun toDto(): MemberDtoResponse =
        MemberDtoResponse(id!!, loginId, nickname, gender.desc, admin, dormType.desc, dormNo, roomNo)
}

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY) //다대일
    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_member_id"))
    val member: Member
)

@Entity
class Mail(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    var loginId: String,

    @Column(nullable = false)
    var authCode: String,

    @Column(nullable = false)
    var sendDate: LocalDateTime
)