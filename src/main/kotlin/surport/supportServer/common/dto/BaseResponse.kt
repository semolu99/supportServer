package surport.supportServer.common.dto

import surport.supportServer.common.status.ResultCode
import java.io.ObjectInputFilter.Status

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg,
)