package com.platon.browser.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.platon.browser.utils.ChainVersionUtil;
import lombok.Data;

import java.math.BigInteger;

/**
 * @description:
 * @author: chendongming@matrixelements.com
 * @create: 2019-11-22 10:18:43
 **/
@Data
public class NodeVersion {
    @JSONField(name = "ProgramVersion", alternateNames = "programVersion")
    private Integer programVersion;

    @JSONField(name = "ValidatorId", alternateNames = "validatorId")
    private BigInteger ValidatorId;

    @JSONField(name = "NodeId", alternateNames = "nodeId" )
    private String nodeId;

    public Integer getProgramVersion(){
        return programVersion;
    }
	public Integer getBigVersion() {
		return ChainVersionUtil.toBigVersion(BigInteger.valueOf(programVersion)).intValue();
	}
}
