package com.aliyun.oss.infrastructure.persistent.dao;

import com.aliyun.oss.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 000
 * @description 抽奖策略奖品明细 DAO
 * @create 2025/05/27
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);
}
