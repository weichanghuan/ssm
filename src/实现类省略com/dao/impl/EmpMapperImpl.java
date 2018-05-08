/*package 实现类省略com.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.dao.EmpMapper;
import com.entity.Emp;
import com.service.EmpService;
import com.service.impl.EmpServiceImpl;

@Repository(value="empMapper")
public class EmpMapperImpl implements EmpMapper {
	//使用SQLSessionTemplate对象简化mybatis开发
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Emp> getAllEmp() {
		return sqlSessionTemplate.selectList("com.dao.EmpMapper.getAllEmp");
	}

	@Override
	public int delEmpBy(Integer empno) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("com.dao.EmpMapper.delEmpBy", empno);
	}
	

	@Override
	public List<Emp> getEmpByfqname(String ename) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("com.dao.EmpMapper.getEmpByfqname", ename);
	}
	

	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		EmpMapperImpl emps = (EmpMapperImpl) ctx.getBean("empMapper");
		System.out.println(emps.getAllEmp().size());
	}

	
	
}
*/