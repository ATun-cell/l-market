package com.aliyun.oss.domain.strategy.service.rule.impl;

import com.aliyun.oss.domain.strategy.model.annotation.LogicStrategy;
import com.aliyun.oss.domain.strategy.model.entity.RuleActionEntity;
import com.aliyun.oss.domain.strategy.model.entity.RuleMatterEntity;
import com.aliyun.oss.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.aliyun.oss.domain.strategy.repository.IStrategyRepository;
import com.aliyun.oss.domain.strategy.service.rule.IRuleLogicFilter;
import com.aliyun.oss.domain.strategy.service.rule.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 000
 * @description 抽奖次数限制规则规律
 * @create 2025/05/30
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_LOCK)
public class RuleLockLogicFilter implements IRuleLogicFilter<RuleActionEntity.RaffleCenterEntity> {

    @Resource
    private IStrategyRepository repository;

    // 用户抽奖次数，后续完成这部分流程开发的时候，从数据库/Redis中读取
    private Long userRaffleCount = 0L;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleCenterEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-次数锁 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());

        Long strategyId = ruleMatterEntity.getStrategyId();
        Integer awardId = ruleMatterEntity.getAwardId();
        String ruleModel = ruleMatterEntity.getRuleModel();
        //查询规则值
        String ruleValue = repository.queryStrategyRuleValue(strategyId, awardId, ruleModel);

        long ruleValueLong = Long.parseLong(ruleValue);
        if (ruleValueLong <= userRaffleCount) {
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                .build();
    }
}
