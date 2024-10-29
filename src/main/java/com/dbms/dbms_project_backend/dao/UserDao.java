package com.dbms.dbms_project_backend.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.repository.UserRepository;
import com.dbms.dbms_project_backend.repository.UserRolesRepository;

import jakarta.transaction.Transactional;

@Repository
public class UserDao implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRolesRepository userRolesRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserRolesDao.class);

    private RowMapper<User> rowMapper = (ResultSet rs, int rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setPassword(rs.getString("password"));

        return user;
    };

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            User user = Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id))
                    .orElseThrow(() -> new EmptyResultDataAccessException("No user found with id: " + id, 1));
            Set<Role> roles = userRolesRepository.getRolesByUser(user);
            user.setRoles(roles);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("[WARN] No user found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, rowMapper);
        for (User user : users) {
            user.setRoles(userRolesRepository.getRolesByUser(user));
        }
        return users;
    }

    @Override
    @Transactional
    public User save(User user) {
        String sql = "INSERT INTO users (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhone(), user.getAddress(),
                user.getPassword());

        Long newUserId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        if (newUserId != null) {
            user.setId(newUserId);
        } else {
            user = findByEmail(user.getEmail()).orElse(null);
        }

        for (Role role : user.getRoles()) {
            userRolesRepository.addRoleByUser(user, role);
        }

        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, phone = ?, address = ?, WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhone(),
                user.getAddress(), user.getId());

        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected > 0) {
            logger.info("[INFO] User with id: {} deleted successfully.", id);
        } else {
            logger.warn("[WARN] No user found to delete with id: {}", id);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper, email);
            if (user != null) {
                Set<Role> roles = userRolesRepository.getRolesByUser(user);
                user.setRoles(roles);
            }
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("[WARN] No user found with email: {}", email);
            return Optional.empty();
        }
    }

    @Override
    public List<User> findUsersByRole(Role role) {
        String sql = "SELECT u.* FROM users u INNER JOIN user_roles ur ON u.id = ur.user_id WHERE ur.role = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, role.name());
        for (User user : users) {
            user.setRoles(userRolesRepository.getRolesByUser(user));
        }
        return users;
    }
}
