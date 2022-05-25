package bjtu.pt.easycontracts.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <Description> WebConfig 配置拦截器拦截哪些请求
 *
 * @author 26802
 * @version 1.0
 * @ClassName WebConfig
 * @taskId
 * @see bjtu.pt.easycontracts.interceptor
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/toLogin","/toRegister","/",
                                     "/css/**","/js/**","/img/**",
                                     "/lib/**","/error/**","/user/login/**",
                                     "/user/register/**");

    }
}
