package surport.supportServer.member.dto

import surport.supportServer.common.status.Dorm_type
import surport.supportServer.common.status.Gender

//회원 가입시 받을 정보? 룸 넘버는 일단 보류
data class MemberDtoRequest(
    val id: Long?,
    val loginId: String,
    val password: String,
    val nickname: String,
    val gender: Gender,
    val admin: Boolean,
    val dormType: Dorm_type,
    val dormNo: Int,
    val roomNo: Int?,
)