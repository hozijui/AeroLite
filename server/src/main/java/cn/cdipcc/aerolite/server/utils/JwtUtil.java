package cn.cdipcc.aerolite.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
public class JwtUtil {
    @Getter
    private static String ISSUER;

    @Value("${jwt.issuer}")
    private void setISSUER(String issuer) {
        ISSUER = issuer;
    }

    @Getter
    private static int EXPIRES_IN; // min

    @Value("${jwt.expires_in}")
    private void setEXPIRES_IN(int expires_in) {
        EXPIRES_IN = expires_in;
    }

    @Getter
    private static int ALLOW_EXPIRES_IN; // min

    @Value("${jwt.allow_expires_in}")
    private void setALLOW_EXPIRES_IN(int allow_expires_in) {
        ALLOW_EXPIRES_IN = allow_expires_in;
    }

    public String genSecretKey(Date lastLogin) {
        return String.valueOf(lastLogin.toInstant());
    }

    private String createToken(boolean isRefresh, String subject, Instant issueAt, Algorithm algorithm) {
        HashMap<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("isRefresh", isRefresh);
        Instant exp = issueAt.plusSeconds(isRefresh ? ALLOW_EXPIRES_IN * 60L : EXPIRES_IN * 60L);
        return JWT.create()
                .withHeader(headerClaims)
                .withIssuer(ISSUER)
                .withClaim("sub", subject)
                .withClaim("iat", Date.from(issueAt))
                .withClaim("exp", Date.from(exp))
                .sign(algorithm);
    }

    public String getToken(boolean isRefresh, String secret_key, String subject, Instant issueAt) {
        Algorithm algorithm = Algorithm.HMAC256(secret_key);
        return createToken(isRefresh, subject, issueAt, algorithm);
    }

    public String refreshToken(String secret_key, DecodedJWT oldToken) {
        Instant now = Instant.now();
        Algorithm algorithm = Algorithm.HMAC256(secret_key);
        return createToken(false, oldToken.getSubject(), now, algorithm);
    }

    public DecodedJWT decode(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public boolean verify(String secret_key, String token, boolean isRefresh) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret_key)).withIssuer(ISSUER).build();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            Claim refreshClaim = decodedJWT.getHeaderClaim("isRefresh");
            if (!refreshClaim.isNull() && refreshClaim.asBoolean() == isRefresh) return true;
            else throw new InvalidClaimException("The claim is invalid");
        } catch (SignatureVerificationException | TokenExpiredException | InvalidClaimException e) {
            log.info(e.getMessage());
        }
        return false;
    }

}
