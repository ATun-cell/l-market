package com.aliyun.oss.infrastructure.persistent.po;
/**
 * @author 000
 * @description 抽奖策略表
 * @create 2025/05/27
 */
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.util.Date;

@Data
public class Strategy {
    //自增ID
    private Long id;
    //抽奖策略ID
    private Long strategyId;
    //抽奖策略描述
    private String strategyDesc;
    //抽奖规则模型
    private String ruleModels;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
