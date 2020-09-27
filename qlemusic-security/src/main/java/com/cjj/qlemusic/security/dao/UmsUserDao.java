package com.cjj.qlemusic.security.dao;


import com.cjj.qlemusic.security.entity.UmsUser;
import org.apache.ibatis.annotations.Param;

/**
 * 用户管理Dao
 */
public interface UmsUserDao {
    /**
     * 用户注册添加
     * @param umsUser
     */
    void insert(UmsUser umsUser);

    /**
     * 根据手机号获取用户
     * @param telephone
     * @return
     */
    UmsUser selectUserByTelephone(String telephone);

    /**
     * 修改用户信息
     * @param umsUser
     * @return
     */
    int updateInfo(UmsUser umsUser);

    /**
     * 根据Id获取用户
     * @param id
     * @return
     */
    UmsUser selectUserById(Long id);

    /**
     * 修改用户唯一ID
     * @param id
     * @param uniqueId
     * @return
     */
    int updateUniqueId(@Param("id") Long id, @Param("uniqueId") String uniqueId);

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
}
