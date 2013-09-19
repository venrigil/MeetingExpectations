/*
 * Copyright 2013 Muhammad Ichsan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http:
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intera.roostrap.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.intera.roostrap.domain.security.User;
import com.intera.roostrap.domain.security.UserRole;

/**
 * Load user and his/her roles using JPA
 *
 * @author '<a href="mailto:ichsan@gmail.com">Muhammad Ichsan</a>'
 *
 */
public class JpaUserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			TypedQuery<User> userQuery = User.findUsersByEmailAddress(username);
			User targetUser = userQuery.getSingleResult();

			List<GrantedAuthority> authorities = loadGrantedAuthorities(targetUser);

			return new org.springframework.security.core.userdetails.User(
					targetUser.getEmailAddress(),
					targetUser.getPassword(),
					targetUser.getEnabled(),
					true, 
					true, 
					!targetUser.getLocked(),
					authorities);

		} catch (EmptyResultDataAccessException e) {
			throw new UsernameNotFoundException("User " + username
					+ " is not found");
		} catch (NonUniqueResultException e) {
			throw new IllegalStateException("Non-unique user" + username
					+ ", contact administrator");
		}
	}

	private List<GrantedAuthority> loadGrantedAuthorities(User targetUser) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		TypedQuery<UserRole> roleQuery = UserRole
				.findUserRolesByUser(targetUser);
		List<UserRole> userRoles = roleQuery.getResultList();

		for (UserRole userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRole()
					.getName()));
		}

		return authorities;
	}

}
