package com.aliyun.oss.infrastructure.persistent.dao;

import com.aliyun.oss.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 000
 * @description 策略规则 DAO
 * @create 2025/05/27
 */
@Mapper
public interface IStrategyRuleDao {

    List<StrategyRule> queryStrategyRuleList();

    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);


    String queryStrategyRuleValue(StrategyRule strategyRule);
}
