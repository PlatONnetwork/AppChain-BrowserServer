/*
package com.platon.browser.controller;

import com.platon.browser.config.ChainsConfig;
import com.platon.browser.dto.NodeRespPage;
import com.platon.browser.dto.RespPage;
import com.platon.browser.dto.block.BlockDownload;
import com.platon.browser.dto.block.BlockListItem;
import com.platon.browser.enums.RetEnum;
import com.platon.browser.exception.BusinessException;
import com.platon.browser.exception.ResponseException;
import com.platon.browser.req.block.BlockDownloadReq;
import com.platon.browser.req.block.BlockListReq;
import com.platon.browser.req.node.NodeDetailReq;
import com.platon.browser.req.node.NodePageReq;
import com.platon.browser.res.BaseResp;
import com.platon.browser.service.ExportService;
import com.platon.browser.service.NodeService;
import com.platon.browser.enums.I18nEnum;
import com.platon.browser.util.I18nUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

*/
/**
 * User: dongqile
 * Date: 2018/10/23
 * Time: 09:35
 *//*

@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private I18nUtil i18n;
    @Autowired
    private ChainsConfig chainsConfig;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private ExportService exportService;
    private static Logger logger = LoggerFactory.getLogger(NodeController.class);

    */
/**
     * @api {post} node/list a.节点列表
     * @apiVersion 1.0.0
     * @apiName list
     * @apiGroup node
     * @apiDescription 节点列表
     * @apiParamExample {json} Request-Example:
     * {
     *      "cid":"", // 链ID (必填)
     *      "keyword": "node-1"// 节点账户名称(可选)，用于节点列表的筛选
     * }
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "voteCount":90, // 投票数
     *      "proportion":86,//占比: 小数
     *      "blockReward":56.33,//每个区块奖励（单位Energon）
     *      "ticketPrice":3.66,//票价（单位Energon）
     *      "selectedNodeCount":33,//已选中节点数
     *      "totalNodeCount":200,//总节点数
     *      "lowestDeposit":545.44, // 最低质押（单位Energon）
     *      "highestDeposit":545.44, // 最高质押（单位Energon）
     *      "data": [
     *           {
     *           "id": "0b9a39c791fdcbda987ff64717ef72f", // 节点ID
     *           "ranking": 1,// 排名
     *           "logo":"", // 节点LOGO，具体形式待定
     *           "name": "node-1",// 账户名称
     *           "electionStatus": 1,// 竞选状态:1-候选前100名,2-出块中,3-验证节点,4-备选前100名
     *           "countryCode":"CN", // 国家代码
     *           "location": "中国广东深圳",// 地理位置
     *           "deposit": "1.254555555", // 质押金(单位:Energon)
     *           "blockCount": 252125,// 产生的总区块数
     *           "rewardRatio": 0.02,// 分红比例:小数
     *           "address": "0xsfjl34jfljsl435kd", // 节点地址
     *           "ticketCount":"",//得票数
     *           "nodeType":""//节点类型
     *              candidates—候选节点
     *              nominees—提名节点
     *              validator—验证节点
     *           }
     *       ]
     * }
     *//*

    @PostMapping("list")
    public NodeRespPage<NodeListItem> getPage (@Valid @RequestBody NodePageReq req) {
        if(!chainsConfig.isValid(req.getCid())){
            throw new ResponseException(i18n.i(I18nEnum.CHAIN_ID_ERROR,req.getCid()));
        }
        req.setIsValid(1);
        req.setPageSize(200);
        NodeRespPage<NodeListItem> returnData = nodeService.getPage(req);
        return returnData;
    }

    */
/**
     * @api {post} node/historyList b.历史节点列表
     * @apiVersion 1.0.0
     * @apiName historyList
     * @apiGroup node
     * @apiDescription 节点列表
     * @apiParamExample {json} Request-Example:
     * {
     *      "cid":"", // 链ID (必填)
     *      "keyword": "node-1"// 节点账户名称(可选)，用于节点列表的筛选
     * }
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "data": [
     *           {
     *           "id": "0b9a39c791fdcbda987ff64717ef72f", // 库标识
     *           "ranking": 1,// 排名
     *           "logo":"", // 节点LOGO，具体形式待定
     *           "name": "node-1",// 账户名称
     *           "electionStatus": 1,// 竞选状态:1-候选前100名,2-出块中,3-验证节点,4-备选前100名
     *           "countryCode":"CN", // 国家代码
     *           "location": "中国广东深圳",// 地理位置
     *           "deposit": "1.254555555", // 质押金(单位:Energon)
     *           "blockCount": 252125,// 产生的总区块数
     *           "rewardRatio": 0.02,// 分红比例:小数
     *           "address": "0xsfjl34jfljsl435kd", // 节点地址
     *           "eliminateTime":"" // 淘汰时间 (毫秒)
     *           }
     *       ]
     * }
     *//*

    @PostMapping("historyList")
    public RespPage<NodeListItem> getHistory (@Valid @RequestBody NodePageReq req) {
        if(!chainsConfig.isValid(req.getCid())){
            throw new ResponseException(i18n.i(I18nEnum.CHAIN_ID_ERROR,req.getCid()));
        }
        req.setIsValid(0);
        RespPage<NodeListItem> returnData = nodeService.getPage(req);
        return returnData;
    }


    */
/**
     * @api {post} node/detail c.节点详情
     * @apiVersion 1.0.0
     * @apiName detail
     * @apiGroup node
     * @apiDescription 节点详情
     * @apiParamExample {json} Request-Example:
     * {
     *      "cid":"", // 链ID (必填)
     *      "id": "0xsfjl34jfljsl435kd", // 数据库ID (查看历史节点详情必须传id)
     *      "nodeId": "0xsfjl34jfljsl435kd", // 节点ID (必选)
     * }
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "data": {
     *           "id": "0xsfjl34jfljsl435kd", // 库标识
     *           "nodeId": "0xsfjl34jfljsl435kd", // 节点ID
     *           "address": "0xsfjl34jfljsl435kd", // 节点地址
     *           "name": "node-1",// 账户名称
     *           "logo":"", // 节点LOGO，具体形式待定
     *           "electionStatus": 1,// 竞选状态:1-候选前100名,2-出块中,3-验证节点,4-备选前100名
     *           "location": "中国广东深圳",// 所属区域
     *           "joinTime": 199880011,// 加入时间，单位-毫秒
     *           "deposit": "1.254555555", // 质押金(单位:Energon)
     *           "rewardRatio": 0.02,// 分红比例:小数
     *           "ranking": 1,// 质押排名
     *           "profitAmount": "2.12425451222222",// 累计收益(单位:Energon)
     *           "verifyCount": 44554, // 节点验证次数
     *           "blockCount": 252125,// 累计出块数
     *           "avgBlockTime": 1.312, // 平均出块时长,单位-秒
     *           "rewardAmount": "0.12425451222222",// 累计分红(单位:Energon)
     *           "nodeUrl":"http://mainnet.abc.cn:10332", // 节点URL地址
     *           "publicKey":"0xdE41ad9010ED7ae4a7bBc42b55665151dcc8DE", // 节点公钥
     *           "wallet":"0xdE41ad9010ED7ae4a7bBc42b55665151dcc8DEf4", // 节点钱包
     *           "intro":"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", // 节点简介
     *           "orgName":"platon", // 机构名称
     *           "orgWebsite":"https://www.platon.network", // 机构官网
     *           "ticketCount":"",//得票数
     *           "ticketEpoch":555, // 票龄
     *           "beginNumber":343, // 开始区块
     *           "endNumber":555, // 结束区块
     *           "hitCount":555 // 中选次数
     *        }
     * }
     *//*

    @PostMapping("detail")
    public BaseResp getDetail (@Valid @RequestBody NodeDetailReq req) {
        if(!chainsConfig.isValid(req.getCid())){
            throw new ResponseException(i18n.i(I18nEnum.CHAIN_ID_ERROR,req.getCid()));
        }
        if(req.getId()==null&&StringUtils.isBlank(req.getNodeId())){
            throw new ResponseException(i18n.i(I18nEnum.NODE_ERROR_NEED_ID_OR_NODE_ID,req.getCid()));
        }
        try{
            NodeDetail detail = nodeService.getDetail(req);
            return BaseResp.build(RetEnum.RET_SUCCESS.getCode(),i18n.i(I18nEnum.SUCCESS),detail);
        }catch (BusinessException be){
            throw new ResponseException(be.getMessage());
        }
    }


    */
/**
     * @api {post} node/blockList d.节点区块列表(显示最新20条)
     * @apiVersion 1.0.0
     * @apiName blockList
     * @apiGroup node
     * @apiDescription 节点区块列表
     * @apiParamExample {json} Request-Example:
     * {
     *      "cid":"", // 链ID (必填)
     *      "id":"", // 数据库ID (必填)
     *      "beginNumber":444, // 开始区块
     *      "endNumber":555, // 结束区块
     * }
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "data":[{
     *          "height": 17888,//块高
     *          "timestamp": 1798798798798,//出块时间
     *          "transaction": 10000,//块内交易数
     *          "size": 188,//块大小
     *          "miner": "0x234", // 出块节点
     *          "energonUsed": 111,//能量消耗
     *          "energonLimit": 24234,//能量消耗限制
     *          "energonAverage": 11, //平均能量价值(单位:Energon)
     *          "blockReward": "123123",//区块奖励(单位:Energon)
     *          "serverTime": 1708098077  //服务器时间
     *       }]
     * }
     *//*

    @PostMapping("blockList")
    public BaseResp blockList (@Valid @RequestBody BlockListReq req) {
        if(!chainsConfig.isValid(req.getCid())){
            throw new ResponseException(i18n.i(I18nEnum.CHAIN_ID_ERROR,req.getCid()));
        }
        try{
            List<BlockListItem> blocks = nodeService.getBlockList(req);
            return BaseResp.build(RetEnum.RET_SUCCESS.getCode(),i18n.i(I18nEnum.SUCCESS),blocks);
        }catch (BusinessException be){
            throw new ResponseException(be.getMessage());
        }
    }

    private void download(HttpServletResponse response, String filename, long length, byte [] data){
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        response.setContentType("application/octet-stream");
        response.setContentLengthLong(length);
        try {
            response.getOutputStream().write(data);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ResponseException(i18n.i(I18nEnum.DOWNLOAD_EXCEPTION));
        }
    }

    */
/**
     * @api {get} node/blockDownload?cid=:cid&nodeId=:nodeId&date=:date e.导出节点区块详情
     * @apiVersion 1.0.0
     * @apiName blockDownload
     * @apiGroup node
     * @apiDescription 导出节点区块详情
     * @apiParam {String} cid 链ID
     * @apiParam {String} nodeId 节点地址
     * @apiParam {String} date 数据起始日期
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * 响应为 二进制文件流
     *//*

    @GetMapping("blockDownload")
    public void blockDownload(@RequestParam String cid, @RequestParam String nodeId, @RequestParam String date, HttpServletResponse response) {
        BlockDownloadReq req = new BlockDownloadReq();
        req.setCid(cid);
        req.setNodeId(nodeId);
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = ymd.parse(date);
            String startStr = ymd.format(startDate);
            req.setStartDate(ymdhms.parse(startStr+" 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ResponseException(i18n.i(I18nEnum.FORMAT_DATE_ERROR));
        }
        req.setEndDate(new Date());
        BlockDownload blockDownload = exportService.exportNodeBlockCsv(req);
        download(response,blockDownload.getFilename(),blockDownload.getLength(),blockDownload.getData());
    }
}
*/
