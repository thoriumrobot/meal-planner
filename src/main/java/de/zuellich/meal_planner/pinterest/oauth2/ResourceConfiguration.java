package de.zuellich.meal_planner.pinterest.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Collections;

@Configuration
@EnableOAuth2Client
@PropertySource("classpath:meal_planner.properties")
public class ResourceConfiguration {

    @Value("${meal_planner.oauth2.clientId}")
    private String clientId;

    @Value("${meal_planner.oauth2.clientSecret}")
    private String clientSecret;

    @Value("${meal_planner.oauth2.accessTokenUri}")
    private String accessTokenUri;

    @Value("${meal_planner.oauth2.authorizationUri}")
    private String authorizationUri;

    /**
     * Configure a OAuth2 interface for Pinterest.
     * @return The configuration.
     */
    @Bean
    public OAuth2ProtectedResourceDetails pinterestOAuth2Configuration() {
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setUserAuthorizationUri(authorizationUri);
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.query);
        resourceDetails.setScope(Collections.singletonList("read_public"));
        resourceDetails.setId("pinterest");
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.query);

        return resourceDetails;
    }

    /**
     * Get a new rest template with OAuth2 support for Pinterest.
     * @param clientContext The context that stores information for the oauth2 requests.
     * @return A new rest template that can be used to make requests to protected resources.
     */
    @Bean
    public OAuth2RestTemplate pinterestRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(pinterestOAuth2Configuration(), clientContext);
    }

}
