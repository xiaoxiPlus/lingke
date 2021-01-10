package com.fh.springSecurity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.springSecurity.filter.JwtAuthenticationTokenFilter;
import com.fh.springSecurity.filter.RestAuthenticationEntryPoint;
import com.fh.springSecurity.filter.RestfulAccessDeniedHandler;
import com.fh.springSecurity.vo.AdminDetails;
import com.fh.umsAdmin.entity.UmsAdmin;
import com.fh.umsAdmin.service.IUmsAdminService;
import com.fh.umsResource.entity.UmsResource;
import com.fh.umsResource.service.IUmsResourceService;
import com.fh.umsRole.entity.UmsRole;
import com.fh.umsRole.service.IUmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IUmsAdminService adminService;
    @Resource
    private IUmsRoleService roleService;
    @Resource
    private IUmsResourceService resourceService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()//禁用csrf
                .disable()
                .sessionManagement()//关闭session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //放过的请求，当使用一体的项目时 需要放过静态资源
                .antMatchers("/", "/loginController/**","/login","/socket").permitAll()
                //放过options请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                //拦截其他所有请求
                .anyRequest()
                .authenticated();
        //禁用缓存
        http.headers().cacheControl();
        //加载自定义的拦截器
        http.addFilterBefore(jwtAuthenticationTokenFilter(),UsernamePasswordAuthenticationFilter.class);
        //自定义的未登录和没有权限的返回结果               没有权限
        http.exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler)
                                    //未登录
                .authenticationEntryPoint(restAuthenticationEntryPoint);

    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用户登陆认证和权限授权
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 根据用户名获取用户的名称和权限
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {

            UmsAdmin umsAdmin = adminService.queryUserByName(username);
            if (umsAdmin != null) {
                List<UmsRole> roleList = roleService.queryRoleByAdmin(umsAdmin.getId(),umsAdmin.getUsername());
                List<UmsResource> resourceList = resourceService.queryResourceByAdmin(umsAdmin.getId(),umsAdmin.getUsername());
                return new AdminDetails(umsAdmin,roleList,resourceList);
            }
            throw new UsernameNotFoundException("该用户不存在");
        };
    }

}
