package com.digipower.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.digipower.sercurity.filter.JWTLoginFilter;
import com.digipower.sercurity.filter.JWTValidFilter;
import com.digipower.sercurity.filter.VerificationCodeLoginFilter;
import com.digipower.sercurity.provider.VerificationCodeProvider;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("userDetailServiceImpl")
	private UserDetailsService userDetailService;
	
	
	
	/**
	 * 自定义Provider
	 */
	@Autowired
	private VerificationCodeProvider verificationCodeProvider;
	

	
	 /**
     * 认证
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(new PasswordEncoder() {

            // 对密码未加密
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            // 判断密码是否正确, rawPassword 用户输入的密码,  encodedPassword 数据库DB的密码,当 userDetailService的loadUserByUsername方法执行完后执行
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
            	return rawPassword.toString().equalsIgnoreCase(encodedPassword);
            }
        });
        return authenticationProvider;
    }
    

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authenticationProvider());
		auth.authenticationProvider(verificationCodeProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 
		
		http.addFilter(new JWTValidFilter(authenticationManager()));
        http.addFilter(new JWTLoginFilter(authenticationManager())).csrf().disable();
        
        VerificationCodeLoginFilter verificationCodeLoginFilter = new VerificationCodeLoginFilter("/auth/code",authenticationManager());
        http.addFilterAfter(verificationCodeLoginFilter, UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			// 系统默认登入入口
			.antMatchers("/auth/login").permitAll()
			.antMatchers("/auth/code").permitAll()
			// swagger 2不需要鉴权
			.antMatchers("/swagger-ui.html/**").permitAll()
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/v2/api-docs/**").permitAll()
			// 验证码不需要鉴权
			.antMatchers("/defaultKaptcha").permitAll()
			.anyRequest().authenticated(); // 任何请求,登录后可以访问
			
		 // 开启跨域访问
        http.cors().disable();
        // 开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
        
        
        
        
	}

	@Bean
	public CorsFilter corsFilter() {
		//1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
          //放行哪些原始域
          config.addAllowedOrigin("*");
          //是否发送Cookie信息
          config.setAllowCredentials(true);
          //放行哪些原始域(请求方式)
          config.addAllowedMethod("*");
          //放行哪些原始域(头部信息)
          config.addAllowedHeader("*");
          //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
          // config.addExposedHeader("*");
          
          config.addExposedHeader("Content-Type");
          config.addExposedHeader( "X-Requested-With");
          config.addExposedHeader("accept");
          config.addExposedHeader("Origin");
          config.addExposedHeader( "Access-Control-Request-Method");
          config.addExposedHeader("Access-Control-Request-Headers");


        //2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);

	}
}
