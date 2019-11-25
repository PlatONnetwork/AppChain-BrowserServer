package com.platon.browser.now.service;

import com.platon.browser.req.home.QueryNavigationRequest;
import com.platon.browser.res.home.BlockStatisticNewResp;
import com.platon.browser.res.home.ChainStatisticNewResp;
import com.platon.browser.res.home.QueryNavigationResp;
import com.platon.browser.res.home.StakingListNewResp;
/**
 *  主页模块方法实现接口
 *  @file HomeService.java
 *  @description 
 *	@author zhangrj
 *  @data 2019年8月31日
 */
public interface HomeService {
    /**
     * 首页搜索框查询信息
     * @method queryNavigation
     * @param req
     * @return
     */
	 QueryNavigationResp queryNavigation(QueryNavigationRequest req);
	
	/**
	 * 区块实时推送websocket
	 * @method blockStatisticNew
	 * @return
	 */
	 BlockStatisticNewResp blockStatisticNew();
	
	/**
	 * 统计信息实时推送websocket
	 * @method chainStatisticNew
	 * @return
	 */
	 ChainStatisticNewResp chainStatisticNew();
	
	/**
	 * 首页验证人试试推送列表
	 * @method stakingListNew
	 * @return
	 */
	 StakingListNewResp stakingListNew();
}
