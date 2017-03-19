package authenticationserver.ao;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by igor on 24.02.17.
 */
@Configuration
public class AOConfiguration {

    @Bean
    public SessionAO sessionAO()
    {
        return new SessionAO();
    }

    @Bean
    public UserAO userAO() {
        return new UserAO();
    }

    @Bean
    public OrganizationAO organizationAO()
    {
        return new OrganizationAO();
    }

    @Bean
    public DomainAO domainAO()
    {
        return new DomainAO();
    }

    @Bean
    public UserRoleAO userRoleAO()
    {
        return new UserRoleAO();
    }

    @Bean
    public DomainParticipantAO domainParticipantAO()
    {
        return new DomainParticipantAO();
    }

    @Bean
    public ParticipantAuthenticationAO participantAuthenticationAO()
    {
        return new ParticipantAuthenticationAO();
    }

    @Bean
    public WhiteOrganizationAO whiteOrganizationAO()
    {
        return new WhiteOrganizationAO();
    }
}
