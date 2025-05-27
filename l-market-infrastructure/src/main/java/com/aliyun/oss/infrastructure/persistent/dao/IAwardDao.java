package com.aliyun.oss.infrastructure.persistent.dao;

import com.aliyun.oss.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 000
 * @description 奖品表 DAO
 * @create 2025/05/27
 */
@Mapper
public interface IAwardDao {

    List<Award> queryAwardList();

}
