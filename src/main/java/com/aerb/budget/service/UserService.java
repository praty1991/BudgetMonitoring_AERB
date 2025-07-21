package com.aerb.budget.service;

import com.aerb.budget.entity.User;
import com.aerb.budget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUid(String uid) {
        return userRepository.findByUid(uid).orElse(null);
    }

    public User getUserByEmpId(String empId) {
        return userRepository.findByEmpId(empId).orElse(null);
    }
}
