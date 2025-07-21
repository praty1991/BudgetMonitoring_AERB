package com.aerb.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    public String authenticate(String username, String password) {
        String userDn = "uid=" + username + ",ou=People,dc=aerb,dc=gov,dc=in";
        try {
            ldapTemplate.getContextSource().getContext(userDn, password);
            List<String> uids = ldapTemplate.search(
                "",
                "(uid=" + username + ")",
                (AttributesMapper<String>) attrs -> (String) attrs.get("uid").get()
            );
            return uids.isEmpty() ? null : uids.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
