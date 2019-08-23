package com.hylamobile.promotion.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import com.hylamobile.haas.oauth2.client.HaasAccessTokenConverter;



/**
 * Secures API endpoints with Bearer token
 */
@EnableResourceServer
@Configuration
@Profile("!test")
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.resource.token-info-uri}")
    private String checkTokenUrl;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**/*").permitAll()
                .antMatchers("/v2/api-docs/**/*").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/**/springfox-swagger-ui/**/*").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();

    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(remoteTokenService());

    }

    public RemoteTokenServices remoteTokenService() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();

        remoteTokenServices.setAccessTokenConverter(hylaAccessTokenConverter());
        remoteTokenServices.setClientId(clientId);
        remoteTokenServices.setClientSecret(clientSecret);
        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
        return remoteTokenServices;
    }

    public AccessTokenConverter hylaAccessTokenConverter() {
        DefaultAccessTokenConverter accessTokenConverter = new HaasAccessTokenConverter();
        accessTokenConverter.setIncludeGrantType(true);
        UserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
        accessTokenConverter.setUserTokenConverter(userTokenConverter);
        return accessTokenConverter;
    }

}

