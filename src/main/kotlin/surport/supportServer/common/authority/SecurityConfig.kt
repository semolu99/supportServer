package surport.supportServer.common.authority

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val entryPoint: JwtAuthenticationEntryPoint
) {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests{
                it.requestMatchers(HttpMethod.GET, "/schedule/*").permitAll()
                it.requestMatchers(
                    "/member/signup",
                    "/member/login",
                    "/member/mail",
                    "/member/mailcheck",
                    "/member/mail/find",
                    "/member/test",
                    "/member/token/refresh"
                ).anonymous()
                    .requestMatchers("/member/**").hasAnyRole("MEMBER","ADMIN")
                    .requestMatchers(
                        "/notification/add",
                        "/notification/edit",
                        "/notification/*",
                        "/schedule/*",
                        "/schedule/add",
                    ).hasRole("ADMIN")
                    .anyRequest().permitAll()

            }
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

}