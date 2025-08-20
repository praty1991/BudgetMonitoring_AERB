package com.aerb.budget.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.aerb.budget.entity.User;
import com.aerb.budget.repository.UserRepository;
import com.aerb.budget.specification.UserSpecification;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmpId(String empId) {
        return userRepository.findByEmpId(empId);
    }

    public Optional<User> findByEmpIdOrEmail(String identifier) {
        return userRepository.findByEmpId(identifier)
                .or(() -> userRepository.findByEmail(identifier));
    }

    public Optional<User> getUserByEmpId(String empId) {
        return userRepository.findByEmpId(empId);
    }

    public Optional<User> getUserByUid(String uid) {
        return userRepository.findByUId(uid);
    }

    public List<User> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return userRepository.findAll();
        }
        Specification<User> spec = UserSpecification.containsKeyword(keyword);
        return userRepository.findAll(spec);
    }

    public Optional<User> getById(String empId) {
        return userRepository.findById(empId); // use JPA built-in
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(String empId) {
        userRepository.deleteById(empId);
    }
}

