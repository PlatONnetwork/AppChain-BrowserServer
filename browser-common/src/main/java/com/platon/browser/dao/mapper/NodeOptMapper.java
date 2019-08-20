package com.platon.browser.dao.mapper;

import com.platon.browser.dao.entity.NodeOpt;
import com.platon.browser.dao.entity.NodeOptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NodeOptMapper {
    long countByExample(NodeOptExample example);

    int deleteByExample(NodeOptExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NodeOpt record);

    int insertSelective(NodeOpt record);

    List<NodeOpt> selectByExample(NodeOptExample example);

    NodeOpt selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NodeOpt record, @Param("example") NodeOptExample example);

    int updateByExample(@Param("record") NodeOpt record, @Param("example") NodeOptExample example);

    int updateByPrimaryKeySelective(NodeOpt record);

    int updateByPrimaryKey(NodeOpt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table node_opt
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<NodeOpt> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table node_opt
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<NodeOpt> list, @Param("selective") NodeOpt.Column ... selective);
}