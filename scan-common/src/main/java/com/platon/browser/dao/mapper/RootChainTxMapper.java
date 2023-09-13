package com.platon.browser.dao.mapper;

import com.platon.browser.dao.entity.RootChainTxDto;

import java.util.List;

public interface RootChainTxMapper {
    void batchInsert(List<RootChainTxDto> rootChainTxDtoList);
}
