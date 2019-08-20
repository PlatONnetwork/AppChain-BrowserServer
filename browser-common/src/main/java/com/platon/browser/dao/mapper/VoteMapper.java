package com.platon.browser.dao.mapper;

import com.platon.browser.dao.entity.Vote;
import com.platon.browser.dao.entity.VoteExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoteMapper extends CustomVoteMapper{
    long countByExample(VoteExample example);

    int deleteByExample(VoteExample example);

    int deleteByPrimaryKey(String hash);

    int insert(Vote record);

    int insertSelective(Vote record);

    List<Vote> selectByExample(VoteExample example);

    Vote selectByPrimaryKey(String hash);

    int updateByExampleSelective(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByExample(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByPrimaryKeySelective(Vote record);

    int updateByPrimaryKey(Vote record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<Vote> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vote
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<Vote> list, @Param("selective") Vote.Column ... selective);
}