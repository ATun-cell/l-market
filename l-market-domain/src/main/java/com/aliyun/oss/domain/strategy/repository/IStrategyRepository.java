package com.aliyun.oss.domain.strategy.repository;

import com.aliyun.oss.domain.strategy.model.entity.StrategyAwardEntity;
import com.aliyun.oss.domain.strategy.model.entity.StrategyEntity;
import com.aliyun.oss.domain.strategy.model.entity.StrategyRuleEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author 000
 * @description 策略仓储接口
 * @create 2025/05/28
 */
public interface IStrategyRepository {

    /**
     * 获取奖品策略实体列表到仓库中
     * @param strategyId 策略ID
     * @return 奖品策略实体列表
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 存储传进来的参数到缓存中
     * @param key redis存储键，策略ID挂钩
     * @param rangeRate 离散概率范围
     * @param shuffleStrategyAwardSearchTables 奖品ID装配表
     */
    void storeSrategyAwardSearchRateTables(String key, BigDecimal rangeRate, HashMap<Integer, Integer> shuffleStrategyAwardSearchTables);

    /**
     * 获取离散概率范围
     * @param strategyId 策略ID
     * @return 离散概率范围
     */
    Integer getRateRange(Long strategyId);
    Integer getRateRange(String key);
    /**
     * 根据产生的随机值抽取装配表中对应值
     * @param key 策略IDconcat规则权重
     * @param rateKey 产生的离散范围内随机值
     * @return
     */
    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    /**
     * 获取策略实体
     * @param strategyId 策略ID
     * @return 策略实体
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 获取策略规则实体
     * @param strategyId 策略ID
     * @param ruleModel 要查询的规则
     * @return 策略规则实体
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    /**
     * 获取规则权重值
     * @param strategyId 策略id
     * @param awardId 奖品id
     * @param ruleModel 规则模型
     * @return 规则权重值
     */
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

}
