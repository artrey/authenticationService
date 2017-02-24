package authenticationserver.ao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
