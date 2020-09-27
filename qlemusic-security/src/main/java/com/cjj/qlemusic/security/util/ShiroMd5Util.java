package com.cjj.qlemusic.security.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 使用shiro中的SimpleHash将密码加密
 * hashAlgorithmName和hashIterations必须和HashCredentialsMacther的配置一样
 */
public class ShiroMd5Util {
    /**
     * 算法的类型
     */
    public static String hashAlgorithmName = "MD5";
    /**
     * 密码加密的次数
     */
    public static Integer hashIterations = 2;

    /**
     * 生成加密密码
     * @param password
     * @return
     */
    public static String getEncodePassword(String password,String salt){
        return new SimpleHash(
                hashAlgorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
    }

    /**
     * 生成盐
     */
    public static String getSalt(){
        String s = new SecureRandomNumberGenerator().nextBytes().toHex();
        System.out.println("盐："+s);
        return s;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String encodePassword = getEncodePassword("123",getSalt());
        System.out.println("密码+盐："+encodePassword);
    }
}
