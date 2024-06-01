package com.alioth4j.chatgpt3p5o.mapper;

import com.alioth4j.chatgpt3p5o.pojo.entity.History;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 搜索历史记录Mapper
 * @author Alioth4J
 */
@Mapper
public interface HistoryMapper {

    @Insert("INSERT INTO `history` (user_id, question, answer, create_time)" +
            "VALUES (#{userId}, #{question}, #{answer}, now())")
    void insert(Long userId, String question, String answer);

    @Select("SELECT * FROM `history` WHERE id = #{id}")
    History list(Long id);

    @Select("SELECT * FROM `history` WHERE user_id = #{userId}")
    List<History> listAll(Long userId);

    @Delete("DELETE FROM `history` WHERE id = #{id}")
    void delete(Long id);
    
    @Delete("DELETE FROM `history` WHERE user_id = #{userId}")
    void deleteAll(Long userId);

}
