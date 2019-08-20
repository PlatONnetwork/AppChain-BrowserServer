package com.platon.browser.dao.mapper;

import com.platon.browser.dao.entity.NetworkStat;
import com.platon.browser.dao.entity.NetworkStatExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface NetworkStatMapper {
    long countByExample(NetworkStatExample example);

    int deleteByExample(NetworkStatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NetworkStat record);

    int insertSelective(NetworkStat record);

    List<NetworkStat> selectByExample(NetworkStatExample example);

    NetworkStat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NetworkStat record, @Param("example") NetworkStatExample example);

    int updateByExample(@Param("record") NetworkStat record, @Param("example") NetworkStatExample example);

    int updateByPrimaryKeySelective(NetworkStat record);

    int updateByPrimaryKey(NetworkStat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network_stat
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<NetworkStat> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network_stat
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<NetworkStat> list, @Param("selective") NetworkStat.Column ... selective);
}