package com.hanbit.cock.api.insert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanbit.cock.api.annotation.SignInRequired;
import com.hanbit.cock.api.insert.service.CockInsertService;
import com.hanbit.cock.api.vo.LocationVO;
import com.hanbit.cock.api.vo.MenuVO;
import com.hanbit.cock.api.vo.RestVO;

@RestController
@RequestMapping("/api/cock/insert")
public class CockInsertController {

	@Autowired
	private CockInsertService cockInsertService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("/{rid}")
	public RestVO getRest(@PathVariable(value="rid") int rid) {
		return cockInsertService.getRest(rid);
	}
	
	@RequestMapping("/{rid}/{articleId}")
	public RestVO getRest(@PathVariable(value="rid") int rid, @PathVariable(value="articleId") int articleId) {
		return cockInsertService.getArticle(rid, articleId);
	}

	@SignInRequired
	@PostMapping("/save")
	public Map setSave(@RequestParam("model") String model, @RequestParam("imgs") List<MultipartFile> images) throws Exception {
		RestVO rest = mapper.readValue(model, RestVO.class);
		
		Map result = cockInsertService.setRestAndArticleSave(rest, images);
		
		System.out.println(result.get("result"));
		
		return result;
	}

	@RequestMapping("/position/{lat},{lng}/")
	public List<LocationVO> getLocations(@PathVariable(value="lat") double lat, @PathVariable(value="lng") double lng) {
		LocationVO location = new LocationVO();
		
		location.setLat(lat);
		location.setLng(lng);
		
		List<LocationVO> locations = cockInsertService.getLocations(location);
		
		return locations;
	}
	
	@RequestMapping("/get/menu/{rid}/{text}/")
	public List<MenuVO> getMenu(@PathVariable("rid") int rid, @PathVariable(value="text", required = false) String text) {
		return cockInsertService.getMatchMenuList(rid, text);
	}

	@SignInRequired
	@RequestMapping("/delete/{rid}/{articleId}/{uid}")
	public Map removeArticle(@PathVariable("rid") int rid, @PathVariable("articleId") int articleId,
			@PathVariable("uid") String uid, HttpSession session) {
		Map map = new HashMap<>();
		
		if (!uid.equals(session.getAttribute("uid"))) {
			map.put("result", "not match");
			return map;
		}
		
		cockInsertService.removeArticle(rid, articleId, uid);
		
		map.put("result", "success");
		return map;
	}
}
