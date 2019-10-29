package com.wangting.cms.service;

import java.util.List;

import com.wangting.cms.entity.Link;

public interface LinkService {
//获取友情链接
	List<Link> linklist();
	//获取友情添加
	int addlink(Link link);
	//友情链接的删除
	int deletelink(Integer id);

/*	int linkupdate(Integer id);*/

}
