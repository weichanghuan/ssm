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
	 * ����id��ѯemp
	 */
	Emp getEmpByid(Integer id);

	// ������ҳ��ѯ
	List<Emp> getEmpsByEmpsCondition(EmpsCondition empsCondition);

	// ��������Ϣ��
	int getEmpsrecourd(EmpsCondition empsCondition);
}
