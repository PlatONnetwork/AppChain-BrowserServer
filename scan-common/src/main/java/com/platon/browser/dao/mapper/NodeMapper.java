package com.platon.browser.dao.mapper;

import com.platon.browser.dao.entity.Node;
import com.platon.browser.dao.entity.NodeExample;
import java.util.List;

import com.platon.browser.dao.param.ppos.StakeCreate;
import org.apache.ibatis.annotations.Param;

public interface NodeMapper {
    long countByExample(NodeExample example);

    int deleteByExample(NodeExample example);

    int deleteByPrimaryKey(String nodeId);

    int insert(Node record);

    int insertSelective(Node record);

    List<Node> selectByExampleWithBLOBs(NodeExample example);

    List<Node> selectByExample(NodeExample example);

    Node selectByPrimaryKey(String nodeId);

    int updateByExampleSelective(@Param("record") Node record, @Param("example") NodeExample example);

    int updateByExampleWithBLOBs(@Param("record") Node record, @Param("example") NodeExample example);

    int updateByExample(@Param("record") Node record, @Param("example") NodeExample example);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKeyWithBLOBs(Node record);

    int updateByPrimaryKey(Node record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table node
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<Node> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table node
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<Node> list, @Param("selective") Node.Column ... selective);

    int insertOrUpdateNode(StakeCreate stakeCreate);
}