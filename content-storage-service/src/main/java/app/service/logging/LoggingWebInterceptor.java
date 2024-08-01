package app.service.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoggingWebInterceptor implements HandlerInterceptor
{
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception
    {
        log.info("[{}] {} | ResponseCode: {}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus()
        );
    }
}
