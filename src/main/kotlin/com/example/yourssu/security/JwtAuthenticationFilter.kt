package com.example.yourssu.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {
    private val ignoringPaths = arrayOf("/user", "/user/login")

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        if (request.method == RequestMethod.POST.name) {
            for (ignoringPath in ignoringPaths) {
                if (path == ignoringPath) return true
            }
        }
        return false
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String? = jwtProvider.resolveToken(request)

        if (token?.let { jwtProvider.validateToken(it) } == true) {
            val authentication = jwtProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
