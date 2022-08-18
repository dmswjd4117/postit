package com.spring.boot.security;

import com.spring.boot.connection.application.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.function.Function;

import static org.apache.commons.lang3.ClassUtils.isAssignable;

public class ConnectionBasedVoter implements AccessDecisionVoter<FilterInvocation> {

    private final RequestMatcher authorizationRequestMatcher;
    private ConnectionService connectionService;
    private final Function<String, Long> targetMemberIdExtractor;

    public ConnectionBasedVoter(RequestMatcher authorizationRequestMatcher, Function<String, Long> targetMemberIdExtractor) {
        this.authorizationRequestMatcher = authorizationRequestMatcher;
        this.targetMemberIdExtractor = targetMemberIdExtractor;
    }

    @Autowired
    public void setConnectionService(ConnectionService connectionService){
        this.connectionService = connectionService;
    }
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        HttpServletRequest request = filterInvocation.getRequest();
        if(!isRequestedAuthorization(request)){
            return ACCESS_GRANTED;
        }

        if(!isAssignable(FormAuthenticationToken.class, authentication.getClass())){
            return ACCESS_ABSTAIN;
        }

        FormAuthentication formAuth = (FormAuthentication) authentication.getPrincipal();
        Long memberId = formAuth.id;
        Long targetMemberId = getTargetMemberId(request);

        if(memberId.equals(targetMemberId)){
            return ACCESS_GRANTED;
        }

        try {
            if(connectionService.checkMemberFollowsTargetMember(memberId, targetMemberId)){
                return ACCESS_GRANTED;
            }
        }catch (Exception exception){
            throw new AccessDeniedException(exception.getMessage());
        }

        return ACCESS_DENIED;
    }

    private Long getTargetMemberId(HttpServletRequest request) {
        return targetMemberIdExtractor.apply(request.getRequestURI());
    }

    private boolean isRequestedAuthorization(HttpServletRequest request) {
        return authorizationRequestMatcher.matches(request);
    }
}
