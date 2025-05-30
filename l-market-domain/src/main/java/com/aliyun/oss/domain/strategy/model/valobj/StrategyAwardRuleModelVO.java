package com.aliyun.oss.domain.strategy.model.valobj;

import com.aliyun.oss.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.aliyun.oss.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 000
 * @description 奖品规则值对象；值对象，没有唯一ID，仅限于从数据库查询对象
 * @create 2025/05/30
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {

    private String ruleModels;

    public String[] getRaffleCenterRuleModelList(){
        String[] ruleModelValues = this.ruleModels.split(Constants.SPLIT);
        List<String> ruleModelList = new ArrayList<>();
        for (String ruleModelValue : ruleModelValues) {
            if (DefaultLogicFactory.LogicModel.isCenter(ruleModelValue)) {
                ruleModelList.add(ruleModelValue);
            }
        }
        return ruleModelList.toArray(new String[0]);
    };

    public String[] getRaffleAfterRuleModelList(String ruleModel){
        String[] ruleModelValues = this.ruleModels.split(Constants.SPLIT);
        List<String> ruleModelList = new ArrayList<>();
        for (String ruleModelValue : ruleModelValues) {
            if (DefaultLogicFactory.LogicModel.isAfter(ruleModelValue)) {
                ruleModelList.add(ruleModelValue);
            }
        }
        return ruleModelList.toArray(new String[0]);
    };

}
