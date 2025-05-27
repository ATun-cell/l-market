package com.aliyun.oss.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.infrastructure.persistent.dao.IAwardDao;
import com.aliyun.oss.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 000
 * @description 持久化AwardDao测试
 * @create 2025/05/27
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardDaoTest {

    @Resource
    private IAwardDao awardDao;

    @Test
    public void queryAwardListTest() {
        List<Award> awardList = awardDao.queryAwardList();
        log.info("测试结果{}", JSON.toJSONString(awardList));
    }

}
