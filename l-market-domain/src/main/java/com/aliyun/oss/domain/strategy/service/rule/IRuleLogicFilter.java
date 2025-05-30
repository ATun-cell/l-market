package com.aliyun.oss.domain.strategy.service.rule;

import com.aliyun.oss.domain.strategy.model.entity.RuleActionEntity;
import com.aliyun.oss.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author 000
 * @description 规则逻辑处理接口
 * @create 2025/05/29
 */
public interface IRuleLogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}
