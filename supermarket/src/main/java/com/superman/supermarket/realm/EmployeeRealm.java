package com.superman.supermarket.realm;

import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.service.EmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * 自定义realm
 * @author 23785
 *
 */
public class EmployeeRealm extends AuthorizingRealm{
	
	@Resource
	private EmployeeService employeeService;
	
	//oriza是为当前登录用户授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		//获取账户
		String empAccount = (String) principals.getPrimaryPrincipal();
		//创建AuthorizationInfo对象
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//授予权限
		/*authorizationInfo.setRoles(employeeService.getRolesByUserName(userName));*/
		//授予角色
		//authorizationInfo.setStringPermissions(employeeService.getPermissionsByUserName(userName));
		//返回authorizationInfo对象
		return authorizationInfo;
	}


	//entica是为;当前用户进行身份验证的
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String empAccount = (String) token.getPrincipal();//获取账户
		//调用service方法查询
		Employee employee = employeeService.getEmployeeByEmpAccount(empAccount);
		if (employee != null) {
			//创建AuthenticationInfo对象、进行身份验证
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(employee.getEmpAccount(),employee.getEmpPwd(),"empRealm");
			//	判断用户是否正常
			if (employee.getEmpStatus() == 0){
				//验证成功则返回
				return authenticationInfo;
			}
		}
		//验证失败
		return null;
	}

}
