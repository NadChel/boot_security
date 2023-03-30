package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.service.UserRoleService;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final UserRoleService service;

    public WebMvcConfig(UserRoleService service) {
        this.service = service;
    }

    @Bean
    public Formatter<Set<Role>> roleSetFormatter() {
        return new Formatter<>() {
            @Override
            public Set<Role> parse(String text, Locale locale) {
                Set<Role> roleSet = new HashSet<>();
                String[] roles = text.split("^\\[|\\]$|(?<=\\]),\\s?");
                for (String roleString : roles) {
                    String authority =
                            roleString.substring(roleString.lastIndexOf("=") + 1,
                                    roleString.indexOf("]") - 1);
                    roleSet.add(service.getRoleByName(authority));
                }
                return roleSet;
            }

            @Override
            public String print(Set<Role> object, Locale locale) {
                return null;
            }

        };
    }

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new Formatter<Set<Role>>() {
//
//            @Override
//            public Set<Role> parse(String text, Locale locale) {
//                Set<Role> roleSet = new HashSet<>();
//                String[] roles = text.split("^\\[|\\]$|(?<=\\]),\\s?");
//                for (String roleString : roles) {
//                    String authority =
//                            roleString.substring(roleString.lastIndexOf("=") + 1,
//                                    roleString.indexOf("]"));
//                    roleSet.add(service.getRoleByName(authority));
//                }
//                return roleSet;
//            }
//
//            @Override
//            public String print(Set<Role> object, Locale locale) {
//                return null;
//            }
//        });
//    }
}
