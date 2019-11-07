package com.platon.browser.task;

import com.platon.browser.common.service.elasticsearch.EsDelegationService;
import com.platon.browser.dao.entity.Delegation;
import com.platon.browser.dao.entity.DelegationExample;
import com.platon.browser.dao.mapper.DelegationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: dongqile
 * @Date: 2019/11/6
 * @Description: 数据中已解除的委托删除并存入es中数据操作任务
 */
@Component
@Slf4j
public class DelegateHistorySyn {

    @Autowired
    private DelegationMapper delegationMapper;

    @Autowired
    private EsDelegationService esDelegationService;

    //只查询委托历史列表
    private static final int isHistory = 1;

    @Scheduled(cron = "0/5  * * * * ?")
    private void cron () throws InterruptedException {
        start();
    }

    protected void start () throws InterruptedException {
        try {
            DelegationExample delegationExample = new DelegationExample();
            delegationExample.createCriteria().andIsHistoryEqualTo(isHistory);
            List <Delegation> delegationList = delegationMapper.selectByExample(delegationExample);
            if (delegationList.size() > 0 && null != delegationList) {
                Syn(delegationList);
            }
        } catch (Exception e) {
            log.error("[DelegateHistorySyn] Syn delegate Exception !!!...", e.getMessage());
        }
    }

    @Transactional
    void Syn ( List <Delegation> list ) throws Exception {
        Set <Delegation> delegationSet = new HashSet <>(list);
        esDelegationService.save(delegationSet);
        DelegationExample delegationExample = new DelegationExample();
        delegationExample.createCriteria().andIsHistoryEqualTo(isHistory);
        delegationMapper.deleteByExample(delegationExample);
        log.debug("[DelegateHistorySyn Syn()] Syn transactional finish!!");
    }
}
