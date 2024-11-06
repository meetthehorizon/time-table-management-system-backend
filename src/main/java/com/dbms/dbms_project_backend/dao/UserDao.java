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

    @SuppressWarnings("deprecation")
    @Override
    public List<User> findAllBySchoolId(Long id) {
        String sql = """
                SELECT u.*
                FROM users u
                JOIN (
                    -- Fetch employees associated with the given school_id
                    SELECT id FROM employee WHERE school_id = ?
                    UNION
                    -- Fetch teachers associated with the given school_id through the teacher_req table
                    SELECT t.id
                    FROM teacher t
                    JOIN teacher_req tr ON t.id = tr.teacher_id
                    WHERE tr.school_id = ?
                    UNION
                    -- Fetch students associated with the given school_id
                    SELECT s.id
                    FROM student s
                    JOIN parent p ON s.parent_id = p.id
                    JOIN employee e ON p.id = e.id
                    WHERE e.school_id = ?
                ) AS user_ids ON u.id = user_ids.id;
                """;

        return jdbcTemplate.query(sql, new Object[] { id, id, id }, rowMapper);
    }
}
