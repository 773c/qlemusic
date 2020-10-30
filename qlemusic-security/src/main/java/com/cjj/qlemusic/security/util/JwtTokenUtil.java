package com.cjj.qlemusic.security.util;

import cn.hutool.core.date.DateUtil;
import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserInfo;
import com.cjj.qlemusic.security.service.UmsAdminService;
import com.cjj.qlemusic.security.service.UmsUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

/**
 * JwtToken生成工具
 */

public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    public static final String CLAIM_ACCOUNT = "account";

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private UmsUserService umsUserService;

    /**
     * 根据用户信息创建JWT的token
     *
     * @param claims
     * @return
     */
    public String generateToken(Map<String, Object> claims) {
        return tokenHead + " " + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * 生成token过期时间
     *
     * @return
     */
    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }


    /**
     * 获取token的负载payload，如果token格式不正确则返回null
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.error("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 获取用户账号
     *
     * @param token
     * @return
     */
    public String getAccountFromToken(String token) {
        String account = null;
        try {
            Claims claims = getClaimsFromToken(token);
            account = (String) claims.get(CLAIM_ACCOUNT);
            LOGGER.info("当前用户:{}", account);
        } catch (Exception e) {
            account = null;
        }
        return account;
    }

    /**
     * 验证token是否有效
     *
     * @param token
     */
    public boolean validateToken(String token) {
        String identity = getAccountFromToken(token);
        UmsUser user = umsUserService.getUserByIdentity(identity);
        if (user == null) return false;
        return true;
    }

    /**
     * 判断token是否已经过期
     *
     * @return
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = null;
        try {
            expiredDate = getExpiredDateFromToken(token);
        }catch (NullPointerException e){
            LOGGER.info("token过期了");
            return false;
        }
        LOGGER.info("token过期时间为：{}", DateUtil.format(expiredDate,"yyyy-MM-dd HH:mm:ss"));
        return expiredDate.before(new Date()); //expiredDate 在 new Date()之前 则返回true
    }


    /**
     * 获取token过期时间
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
}
