package com.cjj.qlemusic.security.dao;

import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserCollect;
import com.cjj.qlemusic.security.entity.UmsUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理Dao
 */
public interface UmsUserDao {
    /**
     * 用户注册用户信息添加
     * @param umsUserInfo
     */
    Long insertUserInfo(UmsUserInfo umsUserInfo);

    /**
     * 用户注册授权信息添加
     * @param umsUser
     */
    void insertUser(UmsUser umsUser);

    /**
     * 根据标识获取用户
     * @param identity
     * @return
     */
    UmsUser selectUserByIdentity(String identity);

    /**
     * 根据身份标识获取用户id
     * @param identity
     * @return
     */
    Long selectUserIdByIdentity(String identity);


    /**
     * 修改用户信息
     * @param umsUserInfo
     * @return
     */
    int updateUserInfo(UmsUserInfo umsUserInfo);

    /**
     * 根据Id获取用户
     * @param id
     * @return
     */
    UmsUserInfo selectUserInfoById(Long id);

    /**
     * 修改用户唯一ID
     * @param umsUserInfo
     * @return
     */
    int updateUniqueId(UmsUserInfo umsUserInfo);

    /**
     * 查询用户是否修改过唯一ID
     * @param id
     * @return
     */
    Long selectUserByIdFromUniqueId(Long id);

    /**
     * 将修改唯一ID记录插入
     * @param id
     */
    void addUpdateUniqueIdRecord(Long id);

    /**
     * 修改用户头像
     * @param id
     * @param avatar
     * @return
     */
    int updateAvatar(@Param("id")Long id,@Param("avatar") String avatar);

    /**
     * 根据id集合获取用户
     * @param userIds
     * @return
     */
    List<UmsUserInfo> selectUserInfoByIds(List<Long> userIds);

    /**
     * 更新access_token
     * @param umsUser
     */
    void updateUser(UmsUser umsUser);

    /**
     * 存入收藏夹
     * @param umsUserCollect
     */
    Long insertCollect(UmsUserCollect umsUserCollect);

    /**
     * 存入用户收藏夹关联表
     * @param userId
     * @param collectId
     */
    void insertUserAndCollect(@Param("userId") Long userId, @Param("collectId") Long collectId);
}
