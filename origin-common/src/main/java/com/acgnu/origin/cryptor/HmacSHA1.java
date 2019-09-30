package com.acgnu.origin.cryptor;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class HmacSHA1 {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public static String genHMAC(String data, String key) {
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            var signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            //生成一个指定 Mac 算法 的 Mac 对象
            var mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            //用给定密钥初始化 Mac 对象
            mac.init(signinKey);
            //完成 Mac 操作
            var rawHmac = mac.doFinal(data.getBytes());
            result = Base64.getEncoder().encode(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
        }
        return null == result ? null : new String(result, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        System.out.println(genHMAC("123456", "111"));   //j82jn7HzisORTRXxEEKwP6TRQJo=
    }
}
