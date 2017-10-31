package com.hanbit.cock.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.cock.api.service.MainService;
import com.hanbit.cock.api.vo.MainVO;
import com.hanbit.cock.api.vo.TagVO;
import com.hanbit.cock.api.vo.TopFourVO;

@RestController
@RequestMapping("/api/cock/rest")
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	@RequestMapping("/latest")
	public List<MainVO> latestCockRest() {
		return mainService.latestCockRest();
	}
	
	@RequestMapping("/article")
	public List<MainVO> articleCockRest() {
		return mainService.articleCockRest();
	}
	
	@RequestMapping("/tags")
	public List<TagVO> listRestTags() {
		return mainService.listRestTags();
	}
	
	@RequestMapping("/top4")
	public List<TopFourVO> listCockTopFour() {
		return mainService.listCockTopFour();
	}

}