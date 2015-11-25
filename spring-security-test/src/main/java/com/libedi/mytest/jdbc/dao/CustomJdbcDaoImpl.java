package com.libedi.mytest.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.libedi.mytest.vo.MemberInfo;

public class CustomJdbcDaoImpl extends JdbcDaoImpl {
	private final Log logger = LogFactory.getLog(CustomJdbcDaoImpl.class);

	/**
	 * UserDetailService 인터페이스에 정의된 메서드.<br/>
	 * SpringSecurity가 실행하는 메서드.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<UserDetails> users = this.loadUsersByUsername(username);	// 권한을 제외한 사용자 정보 조회
		
		if(users.size() == 0){
			logger.debug("Query returned no results for user '" + username + "'");
			logger.debug(messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
			throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
		}
		
		MemberInfo user = (MemberInfo)users.get(0);		// contains no GrantedAuthority[]
		
		Set<GrantedAuthority> dbAuthSet = new HashSet<GrantedAuthority>();
		
		if(this.getEnableAuthorities()){
			dbAuthSet.addAll(this.loadUserAuthorities(user.getUsername()));		// 사용자 권한 조회
		}
		if(this.getEnableGroups()){
			dbAuthSet.addAll(this.loadGroupAuthorities(user.getUsername()));	// 사용자 그룹 권한 조회
		}
		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthSet);
		user.setAuthorities(dbAuths);		// 권한 셋팅
		
		if(dbAuths.size() == 0){
			logger.debug("'User '" + username + "' has no authorities and will be treated as 'not found'");
			throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.noAuthority", new Object[]{username}, "Username {0} has no GrantedAuthority"));
		}
		
		return user;
	}

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		// TODO Auto-generated method stub
		return this.getJdbcTemplate().query(this.getUsersByUsernameQuery(), new String[]{username}, new RowMapper<UserDetails>(){
			public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
				String username = rs.getString(1);
				String password = rs.getString(2);
				String name = rs.getString(3);
				return new MemberInfo(username, password, name, AuthorityUtils.NO_AUTHORITIES);
			}
		});
	}

	@Override
	protected List<GrantedAuthority> loadUserAuthorities(String username) {
		// TODO Auto-generated method stub
		return this.getJdbcTemplate().query(this.getAuthoritiesByUsernameQuery(), new String[]{username}, new RowMapper<GrantedAuthority>(){
			public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException{
				/*
				 * rolePrefix 는 기본적으로 ROLE_ 이라고 셋팅되어있다.
				 * 빈 문자열("")로 셋팅해주는게 좋다.
				 */
				String roleName = getRolePrefix() + rs.getString(1);
				return new SimpleGrantedAuthority(roleName);
			}
		});
	}

	@Override
	protected List<GrantedAuthority> loadGroupAuthorities(String username) {
		// TODO Auto-generated method stub
		return super.loadGroupAuthorities(username);
	}
	
}
