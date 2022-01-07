package uk.sky.annotations.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthEntryPoint extends BasicAuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> customExceptionResponse;

    public AuthEntryPoint() {
        customExceptionResponse = Map.of("description", "Security failure");
        setRealmName("Security_Realm");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (authException != null) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().println(objectMapper.writeValueAsString(customExceptionResponse));
        }
    }
}

