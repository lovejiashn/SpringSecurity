package com.jiashn.springsecurity.day01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @Author: jiangjs
 * @Description: 自定义UserDetailsService配置类，实现自定义的UserDetailsManager
 * @Date: 2022/1/6 11:50
 **/
@Configuration
public class UserDetailsServiceConfiguration {

    @Bean
    public UserDetailsRepository userDetailsRepository(){
        UserDetailsRepository userDetailsRepository = new UserDetailsRepository();
        UserDetails userDetails = User.withUsername("zhangSan").password("{noop}12345")
                .authorities(AuthorityUtils.NO_AUTHORITIES).build();
        userDetailsRepository.createUser(userDetails);
        return userDetailsRepository;
    }

    @Bean
    public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository){
        return new UserDetailsManager() {
            @Override
            public void createUser(UserDetails user) {
                userDetailsRepository.createUser(user);
            }

            @Override
            public void updateUser(UserDetails user) {
                userDetailsRepository.updateUser(user);
            }

            @Override
            public void deleteUser(String username) {
                userDetailsRepository.deleteUser(username);
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                userDetailsRepository.changePassword(oldPassword,newPassword);
            }

            @Override
            public boolean userExists(String username) {
                return userDetailsRepository.userExists(username);
            }

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDetailsRepository.loadUserByUsername(username);
            }
        };
    }
}