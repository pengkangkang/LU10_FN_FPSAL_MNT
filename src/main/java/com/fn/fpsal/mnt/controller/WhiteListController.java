package com.fn.fpsal.mnt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fn.fpsal.mnt.bean.FndEmbNcard;
import com.fn.fpsal.mnt.bean.Result;
import com.fn.fpsal.mnt.service.IWhiteListService;
import com.fn.fpsal.mnt.utils.ResultUtils;

@RestController
@RequestMapping("/")
public class WhiteListController {

	@Autowired
	private IWhiteListService whiteListService;
	
	/**
	 * 添加白名单
	 * @param cardList
	 * @return
	 */
	@RequestMapping(path = "/whiteList",method = RequestMethod.POST)
	public Result<Object> addFndEmbNcard(@RequestBody List<FndEmbNcard> cardList){
		try {
			whiteListService.addWhiteList(cardList);
		} catch (Exception e) {
			return ResultUtils.error(e.getMessage());
		}
		return ResultUtils.success();
	}
}
