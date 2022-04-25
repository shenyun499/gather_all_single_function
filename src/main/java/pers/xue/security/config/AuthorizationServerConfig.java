package pers.xue.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author huangzhixue
 * @date 2022/4/24 10:00 下午
 * @Description
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置client id
                .withClient("admin")
                // 配置client secret
                .secret(passwordEncoder.encode("112233"))
                // 配置token有效期
                .accessTokenValiditySeconds(3600)
                // 配置重定向内容，授权成功后跳转
                .redirectUris("http://www.baidu.com")
                // 配置申请权限的范围
                .scopes("all")
                // 配置grant type 授权码模式
                .authorizedGrantTypes("authorization_code");
    }
}
