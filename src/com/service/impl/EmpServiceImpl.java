package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.EmpMapper;
import com.entity.Emp;
import com.service.EmpService;
import com.util.EmpsCondition;
import com.util.Page;
@Service(value="empService1")
@Transactional
public class EmpServiceImpl implements EmpService {
	@Autowired
	private EmpMapper empMapper;

	/*有自动装配以后不需要getter/setter*/
	/* public EmpMapper getEmpMapper() {
		return empMapper;
	}

	public void setEmpMapper(EmpMapper empMapper) {
		this.empMapper = empMapper;
	}*/

	@Override
	public List<Emp> getAllEmp() {
		// TODO Auto-generated method stub
		
		return empMapper.getAllEmp();
	}

	@Override
	public int delEmpBy(Integer empno) {
		// TODO Auto-generated method stub
		return empMapper.delEmpBy(empno);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		EmpService empService =(EmpService) ctx.getBean("empService1");
		List<Emp> emps = empService.getEmpByfqname("S");
		System.out.println("长度:" + emps.size());
	}

	@Override
	public List<Emp> getEmpByfqname(String ename) {
		// TODO Auto-generated method stub
		return empMapper.getEmpByfqname(ename);
	}

	@Override
	public int addEmp(Emp emp) {
		// TODO Auto-generated method stub
		return empMapper.addEmp(emp);
	}

	@Override
	public int updateEmp(Emp emp) {
		// TODO Auto-generated method stub
		return empMapper.updateEmp(emp);
	}

	@Override
	public Emp getEmpByid(Integer id) {
		// TODO Auto-generated method stub
		return empMapper.getEmpByid(id);
	}

	@Override
	public Page getEmpsByEmpsCondition(EmpsCondition empsCondition, Page page) {
		// TODO Auto-generated method stub
		int count = this.empMapper.getEmpsrecourd(empsCondition);
		page.setTotalpage(count);
		empsCondition.setPageno(page.getPageno());
		empsCondition.setPagesize(page.getPagesize());
		List<Emp> lists = this.empMapper.getEmpsByEmpsCondition(empsCondition);	
		page.setList(lists);
		return page;
		
	}

}
