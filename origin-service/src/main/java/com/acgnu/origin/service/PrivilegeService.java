package com.acgnu.origin.service;

import com.acgnu.origin.entity.Privilege;
import com.acgnu.origin.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<Privilege> findAll() {
        return privilegeRepository.findAll();
    }
}
