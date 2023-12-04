package com.Tienda;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import com.Tienda.service.UsuarioDetailsService;

@Configuration //anotaciones siempre van pegadas a la linea siguiente
public class ProjectConfig implements WebMvcConfigurer {

    // estos métodos son para la implementación de la internacionalización
    
    //localeResolver se usa para crear una sesion de cambio de idioma
    @Bean
    public LocaleContextResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }
    
    // LocaleChangeInterceptor se usa para crear un interceptor de cambio de idioma
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    //Bean para poder acceder a los Messages.properties en codio JAVA
    @Bean("messageSource")
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registro/nuevo").setViewName("/registro/nuevo");
    }
    
    /* El siguiente método se utiliza para completar la clase no es 
    realmente funcional, la próxima semana se reemplaza con usuarios de BD */    
//    @Bean
//    public UserDetailsService users() {
//        //Usuario Admin
//        UserDetails admin = User.builder()
//                .username("juan")
//                .password("{noop}123")// {noop}contraseña no se encripta 
//                .roles("USER", "VENDEDOR", "ADMIN")
//                .build();
//        //Usuario Vendedor
//        UserDetails sales = User.builder()
//                .username("rebeca")
//                .password("{noop}456")
//                .roles("USER", "VENDEDOR")
//                .build();
//        //Usuario User
//        UserDetails user = User.builder()
//                .username("pedro")
//                .password("{noop}789")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, sales, admin);
//    }
    
    @Autowired
    private UserDetailsService userDetailsService ;
    
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
            
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> request
                .requestMatchers("/","/index","/errores/**", "/error", "/error/***",
                        "/carrito/**","/pruebas/**","/reportes/**",
                        "/registro/**","/js/**","/webjars/**","/refrescarBoton")
                        .permitAll()
                .requestMatchers(
                        "/producto/nuevo","/producto/guardar",
                        "/producto/modificar/**","/producto/eliminar/**",
                        "/categoria/nuevo","/categoria/guardar",
                        "/categoria/modificar/**","/categoria/eliminar/**",
                        "/usuario/nuevo","/usuario/guardar",
                        "/usuario/modificar/**","/usuario/eliminar/**",
                        "/reportes/**"
                ).hasRole("ADMIN") //hasRole == Solo si tiene ese rol 
                .requestMatchers(
                        "/producto/listado",
                        "/categoria/listado",
                        "/usuario/listado"
                ).hasRole( "VENDEDOR")
                .requestMatchers("/facturar/carrito")
                .hasRole("USER")
                )
                .formLogin(
                        (form) -> form
                .loginPage("/login").permitAll())
                .logout((logout) -> logout.permitAll()
                );
        return http.build();
    }

}
