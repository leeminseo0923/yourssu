package com.example.yourssu.security

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class JwtHandlerMethodArgumentResolver(
    val jwtProvider: JwtProvider
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        println(parameter.hasParameterAnnotation(Auth::class.java))
        return parameter.hasParameterAnnotation(Auth::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val token = webRequest.getHeader("Authorization")?.substring(7)


        if (token != null) {
            val email = jwtProvider.getAccount(token)
            return AuthInfo(email)
        }

        return null

    }
}