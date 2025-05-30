package com.aliyun.oss.domain.strategy.service;

import com.aliyun.oss.domain.strategy.model.entity.RaffleAwardEntity;
import com.aliyun.oss.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author 000
 * @description 抽奖策略接口
 * @create 2025/05/29
 */
public interface IRaffleStrategy {

    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}
