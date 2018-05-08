package com.service;

import java.util.List;

import com.entity.Emp;
import com.util.EmpsCondition;
import com.util.Page;



public interface EmpService {

	List<Emp> getAllEmp();
	int delEmpBy(Integer empno);
	List<Emp> getEmpByfqname(String ename);
	int addEmp(Emp emp);
	int updateEmp(Emp emp);
	Emp getEmpByid(Integer id);
	
	//条件分页查询
		Page getEmpsByEmpsCondition(EmpsCondition  empsCondition ,Page page);
	
	

	
}
