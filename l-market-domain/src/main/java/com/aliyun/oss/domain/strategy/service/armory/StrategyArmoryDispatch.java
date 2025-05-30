package com.aliyun.oss.domain.strategy.service.armory;

import com.aliyun.oss.domain.strategy.model.entity.StrategyAwardEntity;
import com.aliyun.oss.domain.strategy.model.entity.StrategyEntity;
import com.aliyun.oss.domain.strategy.model.entity.StrategyRuleEntity;
import com.aliyun.oss.domain.strategy.repository.IStrategyRepository;
import com.aliyun.oss.types.enums.ResponseCode;
import com.aliyun.oss.types.exception.AppException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author 000
 * @description 策略装配库，负责策略初始化
 * @create 2025/05/28
 */
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        //权重策略配置 适用于rule_weight权重规则配置
        StrategyEntity strategyEntities = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntities.getRuleWeight();
        if (ruleWeight == null) {return true;}

        StrategyRuleEntity strategyRuleEntity = strategyRepository.queryStrategyRule(strategyId, ruleWeight);
        if (strategyRuleEntity == null) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());}

        Map<String, List<Integer>> ruleWeightMaps = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightMaps.keySet();
        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightMaps.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }

        return true;

    }

    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
        //获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        //获取概率总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //用1 % 0.0001获取离散概率范围
        BigDecimal rangeRate = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        //根据每个奖品概率进行离散概率范围的奖品ID装配
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(rangeRate.intValue());
        for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();
            //计算每个概率值需要存放到查找表的数量，循环填充
            for (int i = 0; i < rangeRate.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }

        }
        Collections.shuffle(strategyAwardSearchRateTables);
        HashMap<Integer, Integer> shuffleStrategyAwardSearchTables = new HashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchTables.put(i, strategyAwardSearchRateTables.get(i));
        }

        //存储到redis
        strategyRepository.storeSrategyAwardSearchRateTables(key, BigDecimal.valueOf(strategyAwardSearchRateTables.size()), shuffleStrategyAwardSearchTables);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rangeRate = strategyRepository.getRateRange(strategyId);
        return strategyRepository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rangeRate));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeight) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeight);
        int rangeRate = strategyRepository.getRateRange(key);
        return strategyRepository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rangeRate));
    }


}
