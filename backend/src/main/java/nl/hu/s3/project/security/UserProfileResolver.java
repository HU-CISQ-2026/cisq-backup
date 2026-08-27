package nl.hu.s3.project.security;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

public class UserProfileResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserProfile.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        /*
        Dit is (natuurlijk) geen echte security, maar het werkt 'grotendeels' hetzelfde als wel nette-security.
        Bijna alle web-security methodes gebruiken een technisch tricky header om authenticatie-info in te stoppen.

        In dit geval gebruiken we gewoon een hele simpele header, namelijk iedereen mag claimen wie die is, en we geloven
        het gewoon.

        Op deze manier zorg je dat je iig. een deel van de techniek alvast tegenkomt zonder een ingewikkeld
        security framework in te moeten richten. Op deze manier zal het overstappen naar echte security zo min mogelijk
        invloed hebben op andere code.

        Uiteraard zullen we ook een goede demo van -wel- werkende security met jullie doorspreken tzt.
         */

        String username = webRequest.getHeader("X-User");
        if (username != null) {
            return new UserProfile(username);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user profile found");
        }
    }
}
