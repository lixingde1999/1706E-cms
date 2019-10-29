package com.wangting.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.wangting.cms.comon.ConstClass;
import com.wangting.cms.entity.Article;
import com.wangting.cms.entity.User;
import com.wangting.cms.service.ArticleService;
import com.wangting.cms.service.ChannelService;
import com.wangting.cms.service.UserService;
import com.wangting.cms.web.PageUtils;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	
	
	@Autowired
	ArticleService articleService;
	
	
	
    //@PostMapping// 只能接受post方法的请求
	//@RequestMapping(value = "register",method=RequestMethod.GET)
	@GetMapping("register")  // 只接受get的请求
	public String register() {
		return "user/register";
	}
	
	
	
	
	@RequestMapping("index")
	public String index() {
		return "user/index";
	}
	

	/**
	 * 判断用户名是否已经被占用
	 * @param username
	 * @return
	 */
	@RequestMapping("checkExist")
	@ResponseBody
	public boolean checkExist(String username) {
		return !userService.checkExist(username);
	}
	
	@PostMapping("register")  // 只接受POst的请求\
	public String register(HttpServletRequest request,
			@Validated User user,
			BindingResult errorResult) {
		if(errorResult.hasErrors()) {
			return "user/register";
		}
		
		int result = userService.register(user);
		if(result>0) {
			return "redirect:login";
		}else {
			request.setAttribute("errorMsg", "系统错误，请稍后重试");
			return "user/register";
		}

	}
	
	
	@RequestMapping(value = "login",method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute(ConstClass.SESSION_USER_KEY);
		return "user/login";
	}
	
	@PostMapping("login")  // 只接受POst的请求
	public String login(HttpServletRequest request,
			@Validated User user,
			BindingResult errorResult) {
		
		if(errorResult.hasErrors()) {
			return "login";
		}
		
		//登录
		User loginUser = userService.login(user);
		System.out.println("loginUser"+loginUser);
		if(loginUser==null) {
			request.setAttribute("errorMsg", "用户名密码错误");
			return "user/login";
		}else {
			//用户信息保存在session当中
			request.getSession().setAttribute(ConstClass.SESSION_USER_KEY, loginUser);
		//	System.out.println("-----------------------"+loginUser.getRole());
			//普通注册用户
			if(loginUser.getRole()==ConstClass.USER_ROLE_GENERAL) {
				System.out.println("0000000000000000000");
				if(loginUser.getLocked()==ConstClass.USER_ROLE_GENERAL) {
					return "redirect:home";	
				}
				
				return "user/login/login";	
				
			//管理员用户	
			}else if(loginUser.getRole()==ConstClass.USER_ROLE_ADMIN){
				System.out.println("111111111111111111111");
				return "redirect:../admin/index";	
			}else {
				// 其他情况
				return "user/login";
			}
		}
		
	}
	
	
	/**
	 * 进入个人中心(普通注册用户)
	 * @param request
	 * @return
	 */
/*	@RequestMapping("home")
	public String home1(HttpServletRequest request,String title) {
		
		List<Article> list = articleService.search(title);
		request.setAttribute("listsearch", list);
		return "my/";
	}*/
	@RequestMapping("home")
	public String home(HttpServletRequest request) {
		
		return "my/home";
	}
	
	/**
	 * 删除用户自己的文章
	 * @param id 文章id
	 * @return
	 */
	@RequestMapping("delArticle")
	@ResponseBody
	public boolean delArticle(Integer id) {
		return articleService.remove(id)>0;
	}
	
	/**
	 * 进入个人中心 获取我的文章
	 * @param request
	 * @return
	 */
	@RequestMapping("myarticlelist")
	public String myarticles(HttpServletRequest request,
			@RequestParam(defaultValue="1") Integer page) {
		
		User loginUser =(User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		PageInfo<Article>  pageArticles = articleService.listArticleByUserId(loginUser.getId(),page);
		PageUtils.page(request, "/user/myarticlelist", 10, pageArticles.getList(), (long)pageArticles.getSize(), pageArticles.getPageNum());
		request.setAttribute("pageArticles", pageArticles);
		return "/my/list";
	}
	
	

	/**
	 * 跳转到上传页面
	 */
	@GetMapping("toAddhead_picture")
	public String toAddhead_picture() {
		return "my/addhead_picture";
	}

	
	@PostMapping("addhead_picture")
	public String addHead_picture(HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		User user = (User)request.getSession().getAttribute("SESSION_USER_KEY");
		System.out.println("112323423121233");
		System.out.println("user----------"+user);
		processFile(file,user);
		
		 userService.addHead_picture(user);
		return "redirect:home";
		
	}
	
	/**
	 * 处理接收到的文件
	 */
	
	private void processFile(MultipartFile file,User user) throws IllegalStateException, IOException {


		// 原来的文件名称
		System.out.println("file.isEmpty() :" + file.isEmpty()  );
		System.out.println("file.name :" + file.getOriginalFilename());
		
		if(file.isEmpty()||"".equals(file.getOriginalFilename()) || file.getOriginalFilename().lastIndexOf('.')<0 ) {
			user.setHead_picture("");
			return;
		}
			
		String originName = file.getOriginalFilename();
		String suffixName = originName.substring(originName.lastIndexOf('.'));
		SimpleDateFormat sdf=  new SimpleDateFormat("yyyyMMdd");
		String path = "d:/pic/" + sdf.format(new Date());
		File pathFile = new File(path);
		if(!pathFile.exists()) {
			pathFile.mkdir();
		}
		String destFileName = 		path + "/" +  UUID.randomUUID().toString() + suffixName;
		File distFile = new File( destFileName);
		file.transferTo(distFile);//文件另存到这个目录下边
		user.setHead_picture(destFileName.substring(7));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
