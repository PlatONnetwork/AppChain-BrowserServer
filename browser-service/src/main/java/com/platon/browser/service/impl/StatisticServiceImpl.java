//package com.platon.browser.service.impl;
//
//import com.platon.browser.dto.*;
//import com.platon.browser.service.StatisticService;
//import com.platon.browser.service.cache.StatisticCacheService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//public class StatisticServiceImpl implements StatisticService {
//
//    @Autowired
//    private StatisticCacheService statisticCacheService;
//
//    /*@Override
//    public IndexInfo getIndexInfo(String chainId) {
//        StatisticsCache cache = statisticCacheService.getStatisticsCache(chainId);
//        IndexInfo index = new IndexInfo();
//        BeanUtils.copyProperties(cache,index);
//        index.setConsensusNodeAmount(cache.getConsensusCount());
//        index.setCurrentTransaction(cache.getTransactionCount());
//        index.setAddressAmount(cache.getAddressCount());
//        return index;
//    }
//
//    @Override
//    public StatisticInfo getStatisticInfo(String chainId) {
//        StatisticsCache cache = statisticCacheService.getStatisticsCache(chainId);
//        StatisticInfo statistic = new StatisticInfo();
//        BeanUtils.copyProperties(cache,statistic);
//        *//************** 组装图表数据 ************//*
//        List<StatisticPushItem> items = statisticCacheService.getStatisticPushCache(chainId,1,50);
//        StatisticGraphData graphData = new StatisticGraphData();
//        for (int i=0;i<items.size();i++){
//            StatisticPushItem currentBlock = items.get(i);
//            if(i==0||i==items.size()-1) continue;
//            StatisticPushItem previousBlock = items.get(i-1);
//            graphData.getX().add(currentBlock.getHeight());
//            BigDecimal sec = BigDecimal.valueOf(currentBlock.getTime()-previousBlock.getTime()).divide(BigDecimal.valueOf(1000));
//            graphData.getYa().add(sec.doubleValue());
////                graphData.getYa().add(new Double(currentBlock.getTime()-previousBlock.getTime()));
//            graphData.getYb().add(currentBlock.getTransaction()==null?0:currentBlock.getTransaction());
//        }
//        statistic.setGraphData(graphData);
//        return statistic;
//    }*/
//}
