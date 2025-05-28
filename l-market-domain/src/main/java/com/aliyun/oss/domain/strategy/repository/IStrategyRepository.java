package com.aliyun.oss.domain.strategy.repository;

import com.aliyun.oss.domain.strategy.model.entity.StrategyAwardEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author 000
 * @description 策略仓储接口
 * @create 2025/05/28
 */
public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeSrategyAwardSearchRateTables(Long strategyId, BigDecimal rangeRate, HashMap<Integer, Integer> shuffleStrategyAwardSearchTables);

    Integer getRateRange(Long strategyId);

    Integer getStrategyAwardAssemble(Long strategyId, int rateKey);
}
