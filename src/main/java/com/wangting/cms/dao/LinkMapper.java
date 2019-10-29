package com.wangting.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.wangting.cms.entity.Link;

public interface LinkMapper {

	@Select("select * from cms_link")
	List<Link> linklist();
	
	@Insert("insert into cms_link (http,name) VALUES(#{http},#{name})" )
	int addlink(Link link);

	//友情链接的删除
	@Delete("delete from cms_link where id =#{id}")
	int deletelink(Integer id);

	
	
	/*int linkupdate(Integer id);*/

}
