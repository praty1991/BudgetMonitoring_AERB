package com.aerb.budget;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
    LdapAutoConfiguration.class
})
public class TestConfig {
}
