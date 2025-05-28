package com.aliyun.oss.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 000
 * @description 抽奖策略奖品实体
 * @create 2025/05/28
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StrategyAwardEntity {

    //抽奖策略ID
    private Long strategyId;
    //抽奖奖品ID - 内部流转使用
    private Integer awardId;
    //奖品库存总量
    private Integer awardCount;
    //奖品库存剩余
    private Integer awardCountSurplus;
    //奖品中奖概率
    private BigDecimal awardRate;

}
