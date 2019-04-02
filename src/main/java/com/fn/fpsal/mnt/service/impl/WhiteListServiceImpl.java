package com.fn.fpsal.mnt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fn.fpsal.mnt.bean.FndEmbNcard;
import com.fn.fpsal.mnt.dao.WhiteListDao;
import com.fn.fpsal.mnt.service.IWhiteListService;

@Service
public class WhiteListServiceImpl implements IWhiteListService {

	@Autowired
	private WhiteListDao whiteListDao;
	
	@Override
	public List<FndEmbNcard> addWhiteList(List<FndEmbNcard> cardList) {
		return whiteListDao.save(cardList);
	}

}
