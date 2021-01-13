package com.platon.browser.service.elasticsearch;

import com.platon.browser.elasticsearch.Erc20TxEsRepository;
import com.platon.browser.elasticsearch.dto.EsErcTx;
import com.platon.browser.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 代币内部交易ES处理器
 *
 * @author AgentRJ
 * @create 2020-09-24 11:05
 */
@Service
@Slf4j
public class EsErc20TxService implements EsService<EsErcTx> {

    @Resource
    private Erc20TxEsRepository erc20TxEsRepository;

    @Retryable(value = BusinessException.class, maxAttempts = Integer.MAX_VALUE)
    public void save(Set<EsErcTx> recordSet) {
        try {
            if (recordSet.isEmpty()) return;
            // key: _doc id
            Map<String, EsErcTx> txMap = new HashMap<>();
            recordSet.forEach(t-> txMap.put(generateUniqueDocId(t.getHash(), t.getFrom(), t.getTto(), t.getSeq()), t));
            erc20TxEsRepository.bulkAddOrUpdate(txMap);
        }catch (Exception e){
            log.error("Batch save data of ESTokenTransferRecord exception", e);
            throw new BusinessException(e.getMessage());
        }
    }

    public String generateUniqueDocId(String txHash, String from, String to, long seq) {
        return seq + "_" + txHash.substring(0, txHash.length() / 2) + from.substring(0, from.length() / 2)
                + from.substring(0, to.length() / 2);
    }
}
