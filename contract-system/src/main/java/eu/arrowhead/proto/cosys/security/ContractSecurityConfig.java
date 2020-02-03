package eu.arrowhead.proto.cosys.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import eu.arrowhead.client.library.config.DefaultSecurityConfig;
import eu.arrowhead.client.library.util.ClientCommonConstants;

@Configuration
@EnableWebSecurity
public class ContractSecurityConfig extends DefaultSecurityConfig {

    //=================================================================================================
    // members

    @Value(ClientCommonConstants.$TOKEN_SECURITY_FILTER_ENABLED_WD)
    private boolean tokenSecurityFilterEnabled;

    private ContractTokenSecurityFilter tokenSecurityFilter;

    //=================================================================================================
    // methods

    //-------------------------------------------------------------------------------------------------
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        super.configure(http);
        if (tokenSecurityFilterEnabled) {
            tokenSecurityFilter = new ContractTokenSecurityFilter();
            http.addFilterAfter(tokenSecurityFilter, SecurityContextHolderAwareRequestFilter.class);
        }
    }

    //-------------------------------------------------------------------------------------------------
    public ContractTokenSecurityFilter getTokenSecurityFilter() {
        return tokenSecurityFilter;
    }
}

