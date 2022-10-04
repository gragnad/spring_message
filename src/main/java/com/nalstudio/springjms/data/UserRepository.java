package com.nalstudio.springjms.data;

import com.nalstudio.springjms.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
