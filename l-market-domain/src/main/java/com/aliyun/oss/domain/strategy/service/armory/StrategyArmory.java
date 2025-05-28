package com.aliyun.oss.domain.strategy.service.armory;

import com.aliyun.oss.domain.strategy.model.entity.StrategyAwardEntity;
import com.aliyun.oss.domain.strategy.repository.IStrategyRepository;
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
public class StrategyArmory implements IStrateryArmory {

    @Resource
    private IStrategyRepository strategyRepository;


    @Override
    public void assembleLotteryStrategy(Long strategyId) {
        //查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);

        //获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        //获取概率总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //用1%0.0001获取概率范围
        BigDecimal rangeRate = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        //处理概率范围
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
        strategyRepository.storeSrategyAwardSearchRateTables(strategyId, BigDecimal.valueOf(strategyAwardSearchRateTables.size()), shuffleStrategyAwardSearchTables);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rangeRate = strategyRepository.getRateRange(strategyId);
        return strategyRepository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rangeRate));
    }

}
