package com.sun.springboot.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.springboot.consts.SystemConst;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sun on 16/12/15.
 */
public class JWTUtils {

    /**
     * 生成token
     *
     * @param seconds
     * @param params
     * @return
     */
    public static String createToken(Long seconds, Map<String, Object> params) {
        try {
            // Claim's value class must be Integer,Double,Boolean,Date,String.
            JWTCreator.Builder builder = JWT.create();
            if (params != null && params.keySet() != null)
                for (String key : params.keySet()) {
                    Object value = params.get(key);
                    if (value != null) {
                        builder.withClaim(key, value.toString());
                    }
                }
            String token = builder.withIssuer(SystemConst.TOKEN_ISSUE_KEY)
                    .withExpiresAt(new Date(new Date().getTime() + seconds * 1000))
                    .sign(Algorithm.HMAC256(SystemConst.TOKEN_ENCRYPT_KEY));
            return token;
        } catch (JWTCreationException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证token并返回参数
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static DecodedJWT verify(String token) throws UnsupportedEncodingException {
        // Reusable verifie instance
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SystemConst.TOKEN_ENCRYPT_KEY))
                .withIssuer(SystemConst.TOKEN_ISSUE_KEY).build();
        DecodedJWT verify = verifier.verify(token);
        return verify;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        JWTUtils u = new JWTUtils();

        Map<String, Object> params = new HashMap<>();
        params.put("userId", 111111111l);
        params.put("employeeId", null);

        String token = u.createToken(10l, params);
        System.out.println(token);

        while (true) {
            DecodedJWT verify = u.verify(token);
            System.out.println(verify);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
