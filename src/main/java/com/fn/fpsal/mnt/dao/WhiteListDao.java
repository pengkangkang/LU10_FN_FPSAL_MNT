package com.fn.fpsal.mnt.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fn.fpsal.mnt.bean.FndEmbNcard;

public interface WhiteListDao extends JpaRepository<FndEmbNcard, Serializable> {

}
