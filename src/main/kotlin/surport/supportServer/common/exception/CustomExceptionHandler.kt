package surport.supportServer.common.exception

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.util.ExceptionTypeFilter
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.ErrorResponse
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import surport.supportServer.common.dto.BaseResponse
import surport.supportServer.common.status.ResultCode
import java.net.BindException
import java.security.SignatureException

@RestControllerAdvice
class CustomExceptionHandler {

    /*// @Valid 어노테이션이 사용될때, DTO 검증 실패시 예외처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<String>> {
        val error = ex.bindingResult.allErrors[0]
        return ResponseEntity(BaseResponse(statusCode = ResultCode.BAD_REQUEST.statusCode, statusMessage = (error as FieldError).field.replaceFirst("_","") + ": " + error.defaultMessage, code = ResultCode.BAD_REQUEST.code), HttpStatus.BAD_REQUEST)
    }*/

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<String>> {
        val errors = ex.bindingResult.fieldErrors
        val errorMessages = errors.joinToString(", ") { fieldError ->
            getErrorCode(fieldError).message
        }
        val combinedErrorCode = getCombinedErrorCode(errors)

        return ResponseEntity(
            BaseResponse(
                statusCode = ResultCode.BAD_REQUEST.statusCode,
                statusMessage = errorMessages,
                code = combinedErrorCode
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /// 매개변수 값이 올바르게 처리 되지 않았을때 에러처리
    @ExceptionHandler(IllegalArgumentException::class)
    protected fun illegalArgumentException(ex: IllegalArgumentException): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(BaseResponse(statusCode = ResultCode.BAD_REQUEST.statusCode, statusMessage = ex.message, code = ResultCode.BAD_REQUEST.code), HttpStatus.BAD_REQUEST)
    }
//    ///런타임 에러 처리
//    @ExceptionHandler(RuntimeException::class)
//    protected fun runTimeException(ex: RuntimeException): ResponseEntity<BaseResponse<String>>{
//        return ResponseEntity(BaseResponse(statusCode = ResultCode.BAD_REQUEST.statusCode, statusMessage = ResultCode.BAD_REQUEST.message, code = ResultCode.BAD_REQUEST.code), HttpStatus.BAD_REQUEST
//        )
//    }

    // 기본적인 에러 처리
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception): ResponseEntity<BaseResponse<String>> {
        val resultCode = when (ex) {
            is BadCredentialsException -> ResultCode.LOGIN_ERROR
            is BindException -> ResultCode.INVALID_DATA
            is HttpMessageNotReadableException -> ResultCode.INVALID_JSON
            is SignatureException, is SecurityException, is MalformedJwtException -> ResultCode.INVALID_ACCESS_TOKEN
            is ExpiredJwtException -> ResultCode.TOKEN_EXPIRED
            is NoHandlerFoundException -> ResultCode.NOT_FOUND
            is RuntimeException -> ResultCode.RUN_TIME_ERROR
            else -> ResultCode.INTERNAL_SERVER_ERROR
        }

        return ResponseEntity(BaseResponse(resultCode.statusCode, resultCode.message, code = resultCode.code), HttpStatusCode.valueOf(resultCode.statusCode))
    }
    @ExceptionHandler(SignatureException::class)
    fun handleSignatureException(ex: Exception): ResponseEntity<BaseResponse<String>>{
        return ResponseEntity(BaseResponse(statusCode = ResultCode.INVALID_ACCESS_TOKEN.statusCode, statusMessage = ResultCode.INVALID_ACCESS_TOKEN.message, code = ResultCode.INVALID_ACCESS_TOKEN.code), HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(MalformedJwtException::class)
    fun handleMalformedJwtException(ex: Exception): ResponseEntity<BaseResponse<String>>{
        return ResponseEntity(BaseResponse(statusCode = ResultCode.INVALID_ACCESS_TOKEN.statusCode, statusMessage = ResultCode.INVALID_ACCESS_TOKEN.message, code = ResultCode.INVALID_ACCESS_TOKEN.code), HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException(ex: Exception): ResponseEntity<BaseResponse<String>>{
        return ResponseEntity(BaseResponse(statusCode = ResultCode.TOKEN_EXPIRED.statusCode, statusMessage = ResultCode.TOKEN_EXPIRED.message, code = ResultCode.TOKEN_EXPIRED.code), HttpStatus.BAD_REQUEST)
    }
    // 잘못된 Request Method(GET, POST, PUT..)으로 호출되었을때 예외처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun httpRequestMethodNotSupportedException(ex: HttpRequestMethodNotSupportedException, req: HttpServletRequest): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(BaseResponse(statusCode = ResultCode.REST_TYPE_ERROR.statusCode, statusMessage = "Does not support request method '" + req.method + "'", code = ResultCode.REST_TYPE_ERROR.code), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(InvalidInputException::class)
    protected fun apiCustomException(ex: InvalidInputException): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(BaseResponse(statusCode = ex.statusCode, statusMessage = ex.statusMessage, code = ex.code), HttpStatus.BAD_REQUEST)
    }

    // 에러 코드(및 메시지)를 반환하는 헬퍼 메서드
    private fun getErrorCode(fieldError: FieldError): ResultCode {
        val not = "NotBlank"
        //val pat
        return when (fieldError.code) {

            "NotBlank", "Pattern", "ValidEnum", "Max", "Min" -> when (fieldError.field) {
                "_loginId" -> ResultCode.WRONG_FORMAT_LOGIN_ID
                "_password" -> ResultCode.WRONG_FORMAT_PASSWORD
                "_nickname" -> ResultCode.WRONG_FORMAT_NICKNAME
                "_gender" -> ResultCode.WRONG_FORMAT_GENDER
                "_dormType" -> ResultCode.WRONG_FORMAT_DORM_TYPE
                "_dormNo" -> ResultCode.WRONG_FORMAT_DORM_NUMBER
                "_currentPassword" -> ResultCode.WRONG_FORMAT_CURRENT_PASSWORD
                "_newPassword" -> ResultCode.WRONG_FORMAT_NEW_PASSWORD
                "_title" -> ResultCode.WRONG_FORMAT_TITLE
                "_content" -> ResultCode.WRONG_FORMAT_CONTENT
                "_startDate" -> ResultCode.WRONG_FORMAT_START_DATE
                "_endDate" -> ResultCode.WRONG_FORMAT_END_DATE
                "_creationDate" -> ResultCode.WRONG_FORMAT_CREATION_DATE
                "_authCode" -> ResultCode.WRONG_FORMAT_AUTH_CODE
                "_color" -> ResultCode.WRONG_FORMAT_COLOR
                else -> ResultCode.BAD_REQUEST // 기본 에러 코드
            }
            else -> ResultCode.BAD_REQUEST // 기본 에러 코드
        }
    }

    // 첫 번째 에러 코드에 맞는 코드만 반환하는 헬퍼 메서드
    private fun getCombinedErrorCode(errors: List<FieldError>): String {
        return errors.firstOrNull()?.let { fieldError ->
            getErrorCode(fieldError).code
        } ?: ResultCode.BAD_REQUEST.code
    }
}