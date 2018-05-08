package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.EmpMapper;
import com.util.EmpsCondition;



public class Test1 {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		EmpMapper emps = (EmpMapper) ctx.getBean("empMapper");
		EmpsCondition empsCondition = new EmpsCondition();
		empsCondition.setStr("S");
		System.out.println(emps.getEmpByfqname("S").size());
	}

	}


