package com.platon.browser.dao.mapper;

import com.platon.browser.dao.entity.UnDelegation;
import com.platon.browser.dao.entity.UnDelegationExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UnDelegationMapper {
    long countByExample(UnDelegationExample example);

    int deleteByExample(UnDelegationExample example);

    int deleteByPrimaryKey(String hash);

    int insert(UnDelegation record);

    int insertSelective(UnDelegation record);

    List<UnDelegation> selectByExample(UnDelegationExample example);

    UnDelegation selectByPrimaryKey(String hash);

    int updateByExampleSelective(@Param("record") UnDelegation record, @Param("example") UnDelegationExample example);

    int updateByExample(@Param("record") UnDelegation record, @Param("example") UnDelegationExample example);

    int updateByPrimaryKeySelective(UnDelegation record);

    int updateByPrimaryKey(UnDelegation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table un_delegation
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<UnDelegation> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table un_delegation
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<UnDelegation> list, @Param("selective") UnDelegation.Column ... selective);
}