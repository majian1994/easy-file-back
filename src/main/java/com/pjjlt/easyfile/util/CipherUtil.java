package com.pjjlt.easyfile.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

/**
 * 加密模块
 *
 * */

public class CipherUtil {
    //密钥（使用对称加密）AES 必须128位 (16进制32位*4 = 128比特位)
    private static final String key = "AcCD123456AdCD123456AfCD12345600";
    //不知道为什么一定要16位
    private static final String iv = "p0j0j0ltEasyFile";
    //初始化AES
    private static final AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,key.getBytes(),iv.getBytes());
    /**
     * 通过AES方式加密
     * @param data 原始数据
     * @return result 加密后的数据
     */
    public static String encryptByAES(String data){
        return aes.encryptHex(data);
    }
    /**
     * 通过AES方式解密
     * @param data 加密数据
     * @return result 解密后的数据
     */
    public static String decryptByAES(String data){
        return aes.decryptStr(data);
    }
}
