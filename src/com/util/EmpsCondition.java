package com.util;

import java.util.Date;

public class EmpsCondition {
	// ������ѯ
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	// -----------------------------------------------
	// �����ҳ
	private int pageno = 1;// ��ǰҳ��
	private int pagesize = 2;

	public int getPageno() {
		if (pageno < 1) {
			this.pageno = 1;
		}
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

}
