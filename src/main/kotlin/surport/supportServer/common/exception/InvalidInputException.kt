package surport.supportServer.common.exception

class InvalidInputException(
    val statusCode: Int,
    val statusMessage: String,
    val code: String
) : RuntimeException()