package com.shoppingms.gatewayservice.configSecurity;

import com.nimbusds.jwt.JWTClaimNames;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter authoritiesConverter;
    private final JwtConverterConfig properties;
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        final Collection<GrantedAuthority> authorities = Stream.concat(
                Objects.requireNonNull(authoritiesConverter.convert(jwt)).stream(),
                extractRoles(jwt).stream()
        ).toList();

        return new JwtAuthenticationToken(jwt, authorities, getClaims(jwt));
    }
    private String getClaims(Jwt jwt){
        String claimName= JWTClaimNames.SUBJECT;
        if(properties.getResourceId()!= null){
            claimName= properties.getResourceId();
        }
        return claimName;
    }

    private Collection<? extends GrantedAuthority> extractRoles(Jwt jwt){
        Map<String, Object> accessResource= jwt.getClaims();
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if(accessResource== null
            || (resource= (Map<String, Object>) accessResource.get(properties.getResourceId()))== null
            || (resourceRoles= (Collection<String>) resource.get("role"))== null){

            return Set.of();
        }

        return resourceRoles.stream()
                .map(role-> new SimpleGrantedAuthority("ROLE_"+role))
                .toList();
    }
}
