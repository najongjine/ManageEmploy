package com.open.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.open.cmmn.model.CmmnDefaultVO;

/**
 * Content VO 클래스
 * @author 공통서비스 개발팀 yd.go
 * @since 2016.09.08
 * @version 1.0
 * @see
 *  
 
 */
@Alias("employVO")
public class EmployVO  {
	/**
	 * 
	 */
	private String seq;
	private String subSeq;
	private String empname;
	private String empdept;
	private String emprank;
	private String hp;
	
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSubSeq() {
		return subSeq;
	}
	public void setSubSeq(String subSeq) {
		this.subSeq = subSeq;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpdept() {
		return empdept;
	}
	public void setEmpdept(String empdept) {
		this.empdept = empdept;
	}
	public String getEmprank() {
		return emprank;
	}
	public void setEmprank(String emprank) {
		this.emprank = emprank;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	
}