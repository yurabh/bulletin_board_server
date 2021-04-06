package com.security.jwt;

import com.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This is class {@link JwtProvider}.
 * It generate token,validate token and does other operation
 * about json web token.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Component
@PropertySource("classpath:jwt.properties")
public class JwtProvider {


    /**
     * This is field JWT_TOKEN_VALIDITY for validate token.
     */
    private static final long JWT_TOKEN_VALIDITY = (60 * 60 * 5);


    /**
     * This is field secret jwt.
     */
    @Value("${jwt.secret}")
    private String secret;


    /**
     * This is method which's get authorName from token.
     *
     * @param token {@link String}.
     * @return userName {@link String}.
     */
    String getUsernameFromToken(final String token) {
        Function<Claims, String> subject = Claims::getSubject;
        return getClaimFromToken(token, subject);
    }


    /**
     * This is method which's validate token
     * whether it valid or not.
     *
     * @param token       {@link String}.
     * @param userDetails {@link UserDetails}.
     * @return boolean {@link Boolean}.
     */
    Boolean validateToken(final String token,
                          final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }


    /**
     * This is method for generating token,
     * it is return generated token.
     *
     * @param userDetails {@link UserDetails}.
     * @return string {@link String}.
     */
    public String generateToken(final UserDetails userDetails) {
        return doGenerateToken(userDetails.getUsername());
    }


    /**
     * This is method for generating token,
     * it is return generated token.
     *
     * @param userName {@link String}.
     * @return string {@link String}.
     */
    public String generateToken(final String userName) {
        return doGenerateToken(userName);
    }


    /**
     * This is method which build's Jwt token and also
     * set's some characteristics like: claims,subjects,
     * start date for token and date expiration for token,
     * and sing for token like: secret,and algorithm
     * signature.
     *
     * @param subject {@link String}.
     * @return token {@link String}.
     */
    private String doGenerateToken(final String subject) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + JWT_TOKEN_VALIDITY
                        * SecurityConstant.ONE_THOUSAND))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    /**
     * This is private method which is get expiration date
     * from token.
     *
     * @param token {@link String}.
     * @return date {@link Date}.
     */
    private Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    /**
     * This is private method which is get claim from token.
     *
     * @param token          {@link String}.
     * @param claimsResolver {@link Function<Claims>}.
     * @param <T>            param T.
     * @return {@link T} return claims.
     */
    private <T> T getClaimFromToken(final String token,
                                    final Function<Claims, T>
                                            claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    /**
     * This is private method which is get all claims from token.
     *
     * @param token {@link String}.
     * @return {@link Claims} all claims from token.
     */
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
    }


    /**
     * This is method return true or else depends on token
     * expired or not.
     *
     * @param token {@link String}.
     * @return {@link Boolean} return if token expired or not.
     */
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
