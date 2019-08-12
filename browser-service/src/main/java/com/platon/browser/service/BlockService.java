package com.platon.browser.service;

import com.platon.browser.dao.entity.Block;
import com.platon.browser.dto.block.BlockDetail;
import com.platon.browser.req.block.*;

import java.util.List;
import java.util.Set;

public interface BlockService {
    BlockDetail getDetail(BlockDetailReq req);
    BlockDetail getDetailNavigate(BlockDetailNavigateReq req);
    List<Block> getList(BlockDownloadReq req);
    void clearCache(String chainId);
    void updateCache(String chainId, Set<Block> data);
}
