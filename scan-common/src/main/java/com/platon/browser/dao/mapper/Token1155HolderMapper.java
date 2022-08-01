package com.platon.browser.dao.mapper;

import com.platon.browser.dao.entity.Token1155Holder;
import com.platon.browser.dao.entity.Token1155HolderExample;
import com.platon.browser.dao.entity.Token1155HolderKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Token1155HolderMapper {
    long countByExample(Token1155HolderExample example);

    int deleteByExample(Token1155HolderExample example);

    int deleteByPrimaryKey(Token1155HolderKey key);

    int insert(Token1155Holder record);

    int insertSelective(Token1155Holder record);

    List<Token1155Holder> selectByExample(Token1155HolderExample example);

    Token1155Holder selectByPrimaryKey(Token1155HolderKey key);

    int updateByExampleSelective(@Param("record") Token1155Holder record, @Param("example") Token1155HolderExample example);

    int updateByExample(@Param("record") Token1155Holder record, @Param("example") Token1155HolderExample example);

    int updateByPrimaryKeySelective(Token1155Holder record);

    int updateByPrimaryKey(Token1155Holder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_1155_holder
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<Token1155Holder> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_1155_holder
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<Token1155Holder> list, @Param("selective") Token1155Holder.Column ... selective);
}