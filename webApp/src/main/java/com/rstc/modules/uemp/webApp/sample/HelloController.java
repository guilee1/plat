package com.rstc.modules.uemp.webApp.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping(value="/hello")
	public ModelAndView hello() {
		// 1、收集参数、验证参数
		// 2、绑定参数到命令对象
		// 3、将命令对象传入业务对象进行业务处理
		// 4、选择下一个页面
		ModelAndView mv = new ModelAndView();
		// 添加模型数据 可以是任意的POJO对象
		mv.addObject("message", "Hello World!");
		// 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("hello");
		return mv;
	}
	
	HttpServletRequest getPageRequest(){
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		return request;
	}

	@RequestMapping(value="/request")
	public ModelAndView getRequest(){
		String ip = getPageRequest().getRemoteAddr();
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", ip);
		mv.setViewName("hello");
		return mv;
	}
	
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv = new ModelAndView();
//		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("system/admin/login");
//		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login(HttpSession session)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "";
		String keyData =  getPageRequest().getParameter("KEYDATA");
		String KEYDATA[] = keyData.split(",");
		
		if(null != KEYDATA && KEYDATA.length == 3){
			String userName = KEYDATA[0];
			String pwd = KEYDATA[1];
			String code = KEYDATA[2];
			if("admin".equals(userName) && "admin".equals(pwd)){
				errInfo = "success";
				session.setAttribute("user", "admin");
			}
			else
				errInfo = "failed";
		}else{
			errInfo = "error";	//缺少参数
		}
		map.put("result", errInfo);
		return  map;
	}
	
	/**
	 * 访问系统首页
	 */
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu,HttpSession session){
		
		ModelAndView mv = new ModelAndView();
		if(session.getAttribute("user")==null){
			mv.setViewName("redirect:/login_toLogin");
		}else{
			mv.setViewName("system/admin/index");
			mv.addObject("user", "admin");
			List<Menu> menuList = makeMenu();
			
			mv.addObject("menuList", menuList);
		}
		return mv;
	}
	
	List<Menu> makeMenu(){
		
		Menu _menu = new Menu();
		_menu.setHasMenu(true);
		_menu.setMENU_ICON("default.png");
		_menu.setMENU_ID("1");
		_menu.setMENU_NAME("总菜单");
		_menu.setMENU_ORDER("1");
		_menu.setMENU_URL("tool/ztree");
		_menu.setPARENT_ID("0");
		List<Menu> subMenuList = new ArrayList<Menu>();
		for(int i=0;i<10;++i){
			Menu subMenu = new Menu();
			subMenu.setHasMenu(true);
			subMenu.setMENU_ICON("default.png");
			subMenu.setMENU_ID(String.valueOf(i));
			subMenu.setMENU_NAME("总菜单"+i);
			subMenu.setMENU_ORDER("1");
			subMenu.setMENU_URL("tool/ztree");
			subMenu.setPARENT_ID("1");
			subMenu.setParentMenu(_menu);
			subMenuList.add(subMenu);
		}
		_menu.setSubMenu(subMenuList);
		
		//------------------------------------
		Menu _menu2 = new Menu();
		_menu2.setHasMenu(true);
		_menu2.setMENU_ICON("default.png");
		_menu2.setMENU_ID("2");
		_menu2.setMENU_NAME("地图管理");
		_menu2.setMENU_ORDER("1");
		_menu2.setMENU_URL("tool/map");
		_menu2.setPARENT_ID("0");
		List<Menu> subMenuList2 = new ArrayList<Menu>();
		for(int i=0;i<10;++i){
			Menu subMenu = new Menu();
			subMenu.setHasMenu(true);
			subMenu.setMENU_ICON("default.png");
			subMenu.setMENU_ID(String.valueOf(i));
			subMenu.setMENU_NAME("地图管理"+i);
			subMenu.setMENU_ORDER("1");
			subMenu.setMENU_URL("tool/map");
			subMenu.setPARENT_ID("2");
			subMenu.setParentMenu(_menu2);
			subMenuList2.add(subMenu);
		}
		_menu2.setSubMenu(subMenuList2);
		
		List<Menu> all = new ArrayList<Menu>();
		all.add(_menu);
		all.add(_menu2);
		return all;
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/admin/tab";
	}
	
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public String defaultPage(){
		return "system/admin/default";
	}
	
	
	/**
	 * 多级别树页面
	 */
	@RequestMapping(value="/tool/ztree")
	public ModelAndView ztree() throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/tools/ztree");
		return mv;
	}
	
	/**
	 * 地图页面
	 */
	@RequestMapping(value="/tool/map")
	public ModelAndView map() throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/tools/map");
		return mv;
	}
}
