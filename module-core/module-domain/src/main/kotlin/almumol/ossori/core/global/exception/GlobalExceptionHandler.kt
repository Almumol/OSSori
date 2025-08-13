package almumol.ossori.core.global.exception

import almumol.ossori.core.global.exception.dto.ExceptionResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exception: BadRequestException): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(exception.message)
        return ResponseEntity.badRequest()
                .body(response)
    }
}
