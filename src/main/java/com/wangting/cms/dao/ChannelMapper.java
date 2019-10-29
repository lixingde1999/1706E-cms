package com.wangting.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wangting.cms.entity.Article;
import com.wangting.cms.entity.Channel;

public interface ChannelMapper {

	/**
	 * 获取所有的频道
	 * @return
	 */
	@Select("select * from cms_channel order by id")
	List<Channel> listAll();

	/**
	 *  根据id获取对应的频道
	 * @param id
	 * @return
	 */
	/*@Select("SELECT * FROM cms_channel WHERE id = #{value} limit 1")*/
	Channel findById(Integer id);
// 首页模糊查询
	@Select("select * from cms_article where title like concat('%',#{title},'%') ")
	List<Article> sreach(@Param("title")String title);
	
	
}
