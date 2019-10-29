package com.wangting.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wangting.cms.entity.Article;
import com.wangting.cms.service.ChannelService;

@Controller
public class SearchController {

//首页模糊查询
	@Autowired
	ChannelService chnlService;
	
	@RequestMapping("search")
	public String sreach(HttpServletRequest request, String title) {
		List<Article> sreach = chnlService.getSreach(title);
		System.out.println("---------------------"+sreach);
		if(sreach!=null) {
			request.setAttribute("sreach", sreach);
			return "my/sreach";
		}
		return "my/index";
	}
}
