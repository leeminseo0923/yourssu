package com.example.yourssu.error

import com.example.yourssu.user.exception.WrongPasswordException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(e: NotFoundException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PermissionDeniedError::class)
    fun permissionDeniedException(e: PermissionDeniedError): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(WrongPasswordException::class)
    fun wrongPasswordException(e: WrongPasswordException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}
