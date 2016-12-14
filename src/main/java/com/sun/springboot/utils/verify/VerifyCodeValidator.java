package com.sun.springboot.utils.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sun on 16/12/14.
 */
@Component
public class VerifyCodeValidator {

    /**
     * 过期时间30s
     */
    public static Long time = 30l;
    public static int w = 200, h = 80;
    public static int length = 4;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取验证码
     *
     * @param id
     * @param os
     */
    @Transactional
    public void getVerifyCode(String id, OutputStream os) {
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        try {
            VerifyCodeUtils.outputVerifyImage(w, h, os, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(id, verifyCode, time, TimeUnit.SECONDS);
    }

    /**
     * 验证 验证码
     *
     * @param id
     * @param os
     * @param code
     * @return
     */
    @Transactional
    public boolean verifyCode(String id, OutputStream os, String code) {
        String codeExist = redisTemplate.opsForValue().get(id);
        redisTemplate.delete(id);
        if (code.equals(codeExist)) {
            return true;
        } else {
            getVerifyCode(id, os);
            return false;
        }
    }


}
