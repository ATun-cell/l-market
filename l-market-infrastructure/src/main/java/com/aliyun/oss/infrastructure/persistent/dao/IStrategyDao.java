package com.aliyun.oss.infrastructure.persistent.dao;

import com.aliyun.oss.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 000
 * @description 抽奖策略 DAO
 * @create 2025/05/27
 */
@Mapper
public interface IStrategyDao {

    /**
     * 从库中获取策略对象
     * @param strategyId 策略ID
     * @return 库中的策略对象
     */
    Strategy queryStrategyByStrategyId(Long strategyId);

}
