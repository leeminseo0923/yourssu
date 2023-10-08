package com.example.yourssu.security

import com.example.yourssu.user.UserRole
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

@Component
class JwtProvider (
    val userDetailsService: UserDetailsService,
    key: JWTKey
) {

    enum class ExpirationTime(val number: Long) {
        REFRESH(1000*60*60L),
        ACCESS(1000*60*30L)
    }

    val key: SecretKey = Keys.hmacShaKeyFor(key.key.toByteArray())

    fun createToken(email: String, role: UserRole, expirationTime: ExpirationTime): String {
        val claims = Jwts.claims()
        claims.subject = email
        claims["roles"] = role.name

        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationTime.number))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getAccount(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }
    fun getAccount(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }
    fun resolveToken(request: HttpServletRequest): String {
        println("request = ${request.getHeader("Authorization")}")
        return request.getHeader("Authorization")
    }

    fun validateToken(token: String): Boolean {
        try {
            if (!token.substring(0, "BEARER".length).equals("BEARER ", ignoreCase = true)) {
                return false
            }
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.split(" ")[1].trim())
            return !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }



}