package com.dao;

import java.util.List;

import com.entity.Emp;
import com.util.EmpsCondition;

public interface EmpMapper {

	List<Emp> getAllEmp();

	int delEmpBy(Integer empno);

	List<Emp> getEmpByfqname(String ename);

	int addEmp(Emp emp);

	int updateEmp(Emp emp);

	/*
	 * 根据id查询emp
	 */
	Emp getEmpByid(Integer id);

	// 条件分页查询
	List<Emp> getEmpsByEmpsCondition(EmpsCondition empsCondition);

	// 返回总消息数
	int getEmpsrecourd(EmpsCondition empsCondition);
}
