package org.partiteweb.matchweb.repositories;

import org.partiteweb.matchweb.classes.SecurityUser;
import org.partiteweb.matchweb.classes.myUsers.FullUser;
import org.partiteweb.matchweb.classes.myUsers.InfoUser;
import org.partiteweb.matchweb.classes.myUsers.SignupUser;
import org.partiteweb.matchweb.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(String username) {return userDetailsManager.userExists(username);}

    public void addUser(SignupUser signupUser) {
        String sql = "INSERT INTO USERDATA VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                signupUser.getName(),
                signupUser.getSurname(),
                java.sql.Date.valueOf(signupUser.getBirthday()),
                signupUser.getEmail(),
                signupUser.getUsername(),
                signupUser.getSport(),
                signupUser.getTeam()
        );
        signupUser.setPassword(passwordEncoder.encode(signupUser.getPassword()));
        userDetailsManager.createUser(new SecurityUser(signupUser));
        System.out.println("[INFO]: User added successfully");
    }

    public List<InfoUser> getAllUsers() {
        String sql = "SELECT U.FIRSTNAME, U.LASTNAME, U.BIRTHDAY, U.EMAIL, U.USERNAME, A.AUTHORITY FROM USERDATA U JOIN AUTHORITIES A ON U.ID = A.ID";
        RowMapper<InfoUser> userRowMapper = (r, i) -> new InfoUser(
                r.getString("FIRSTNAME"),
                r.getString("LASTNAME"),
                r.getString("USERNAME"),
                r.getString("AUTHORITY"),
                r.getDate("BIRTHDAY").toLocalDate(),
                r.getString("EMAIL"));

        List<InfoUser> users =  jdbcTemplate.query(sql, userRowMapper);
        users.sort(null);
        return users;
    }

    public void promoteUser(String username) {
        String sql = "UPDATE AUTHORITIES SET AUTHORITY = ? WHERE USERNAME = ? AND AUTHORITY NOT IN (?, ?)";
        jdbcTemplate.update(sql, "ROLE_" + SecurityConfig.MODERATOR, username, "ROLE_" + SecurityConfig.ADMIN, "ROLE_" + SecurityConfig.MODERATOR);
        System.out.println("[INFO]: Promoted " + username + " to MODERATOR");
    }

    public void changePassword(String username, String password) {
        String sql = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?";
        jdbcTemplate.update(sql, passwordEncoder.encode(password), username);
        System.out.println("[INFO]: Changed password for " + username);
    }

    public FullUser showUser(String username) {
        String sql = """
            SELECT U.FIRSTNAME, U.LASTNAME, U.BIRTHDAY, U.EMAIL, U.USERNAME, A.AUTHORITY, S.SCORE, S.PRIZESWON, G.COUNTER
            FROM USERDATA U
            JOIN AUTHORITIES A ON U.ID = A.ID
            JOIN SCOREBOARD S ON U.ID = S.ID
            JOIN GIORNATE G ON U.ID = G.ID
            WHERE A.USERNAME = ?
            """;
        RowMapper<FullUser> userRowMapper = (r, i) -> new FullUser(
                r.getString("FIRSTNAME"),
                r.getString("LASTNAME"),
                r.getString("USERNAME"),
                r.getString("AUTHORITY"),
                r.getDate("BIRTHDAY").toLocalDate(),
                r.getString("EMAIL"),
                r.getInt("SCORE"),
                r.getInt("COUNTER"),
                r.getInt("PRIZESWON")
        );
        return jdbcTemplate.queryForObject(sql, userRowMapper, username);
    }
}
