package  com.util;

import java.util.List;

public class Page {
	private int pageno;//��ǰҳ��
	
	private int totalpage;//��ҳ��
	
	private int pagesize=5;//ÿҳ��ʾ����������
	
	private List list;//��ǰҳ��ʾ�����ݼ���

	public int getPageno() {
		if(pageno<1){
			this.pageno=1;
		}
		else if(pageno>totalpage){
			this.pageno=totalpage;
		}
		return this.pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		if(totalpage%pagesize==0){
			this.totalpage=totalpage/pagesize;
		}
		else{
			this.totalpage=totalpage/pagesize+1;
		}
		
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}


	
	
}
