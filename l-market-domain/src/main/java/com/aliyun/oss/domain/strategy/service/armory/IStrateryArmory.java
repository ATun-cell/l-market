package com.aliyun.oss.domain.strategy.service.armory;

/**
 * @author 000
 * @description 策略装配兵工厂，负责初始化策略计算
 * @create 2025/05/27
 */
public interface IStrateryArmory {





    /**
     * 获取抽奖策略装配结果
     * @param strategyId 策略ID
     */
    boolean assembleLotteryStrategy(Long strategyId);
}
