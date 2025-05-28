package com.aliyun.oss.domain.strategy.service.armory;

/**
 * @author 000
 * @description 策略装配兵工厂，负责初始化策略计算
 * @create 2025/05/27
 */
public interface IStrateryArmory {

    //装配策略概率分布
    void assembleLotteryStrategy(Long strategyId);
    //获取策略
    Integer getRandomAwardId(Long strategyId);

}
