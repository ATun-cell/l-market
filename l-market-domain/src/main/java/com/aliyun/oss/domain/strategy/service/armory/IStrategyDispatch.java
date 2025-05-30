package com.aliyun.oss.domain.strategy.service.armory;

/**
 * @author 000
 * @description 抽奖调度
 * @create 2025/05/28
 */
public interface IStrategyDispatch {

    /**
     * 获取抽奖结果
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     *
     */
    Integer getRandomAwardId(Long strategyId, String ruleWeight);

}
