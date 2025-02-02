package img.gen.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
        String username = request.getHeader("X-User-Name");
        if (username != null)
        {
            MDC.put("username", username);
        }

        log.info("[{}] {} | ResponseCode: {}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus()
        );
        MDC.remove("username");
    }
}
