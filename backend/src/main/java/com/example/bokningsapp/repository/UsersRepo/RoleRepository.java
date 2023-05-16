package com.example.bokningsapp.repository.UsersRepo;

import com.example.bokningsapp.enums.ERole;
import com.example.bokningsapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
    Role findRoleByName(ERole name);
}