package com.dbms.dbms_project_backend.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
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

    public static RowMapper<User> rowMapper = (ResultSet rs, int rowNum) -> {
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

        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
        if (users.isEmpty()) {
            return Optional.empty();
        }

        User user = users.get(0);
        user.setRoles(userRolesRepository.getRolesByUser(user));

        return Optional.of(user);
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
        user.setId(newUserId);

        for (Role role : user.getRoles()) {
            userRolesRepository.addRoleByUser(user, role);
        }

        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getId());

        return findById(user.getId()).get();
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
        User user = jdbcTemplate.queryForObject(sql, rowMapper, email);
        if (user != null) {
            Set<Role> roles = userRolesRepository.getRolesByUser(user);
            user.setRoles(roles);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email) > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        String sql = "SELECT COUNT(*) FROM users WHERE phone = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, phone) > 0;
    }

    @Override
    public List<User> findAllBySchoolId(Long id) {
        String sql = """
                SELECT * FROM users
                WHERE id IN (
                    SELECT id FROM employee WHERE school_id = ?
                    UNION
                    SELECT teacher_id FROM teacher_req WHERE school_id = ?
                    UNION
                    SELECT student_id FROM enrollment
                    WHERE section_id IN (
                        SELECT id FROM sections WHERE school_id = ?
                    )
                )
                """;

        List<User> users = jdbcTemplate.query(sql, rowMapper, id, id, id);
        users.forEach(user -> user.setRoles(userRolesRepository.getRolesByUser(user)));

        return users;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<User> findAllByRole(Role role) {
        String sql = "SELECT * FROM users WHERE id IN (SELECT user_id FROM user_roles WHERE role = ?)";
        List<User> users = jdbcTemplate.query(sql, rowMapper, role.name());
        users.forEach(user -> user.setRoles(userRolesRepository.getRolesByUser(user)));
        return users;
    }

}
