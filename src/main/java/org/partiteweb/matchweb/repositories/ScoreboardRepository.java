package org.partiteweb.matchweb.repositories;

import org.partiteweb.matchweb.classes.myUsers.ScoreboardUser;
import org.partiteweb.matchweb.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class ScoreboardRepository {

    private final JdbcTemplate jdbcTemplate;
    private ArrayList<ScoreboardUser> scoreboard;

    @Autowired
    ScoreboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        refreshScoreboard();
    }

    public void initUserScore(String username) {
        String sql = "INSERT INTO SCOREBOARD VALUES (DEFAULT, ?, 0, 0)";
        jdbcTemplate.update(sql, username);
        System.out.println("[INFO]: Scoreboard initialized for " + username);
    }

    public int getPositionOf(String username) {
        for (int i = 0; i < scoreboard.size(); i++) {
            if (scoreboard.get(i).hasEqualUsername(username)) {
                return i + 1;
            }
        }
        return -1;
    }

    public void initGiornate(String username) {
        String sql = "INSERT INTO GIORNATE VALUES (DEFAULT, ?, 0, ?)";
        jdbcTemplate.update(sql, username, LocalDate.parse("1920-01-01"));
        System.out.println("[INFO]: Giornate initialized for " + username);
    }

    public void incrementUserScore(String username, int score) {
        if(score > 0) {
            String sql = "UPDATE SCOREBOARD SET SCORE = SCORE + ? WHERE USERNAME = ?";
            jdbcTemplate.update(sql, score, username);
            System.out.println("[INFO]: Incremented user score");
        }
    }

    public ArrayList<ScoreboardUser> getScoreboard() {
        if(scoreboard == null) {
            refreshScoreboard();
        }
        return scoreboard;
    }

    private void refreshScoreboard() {
        String sql = """
            SELECT U.FIRSTNAME, U.LASTNAME, U.USERNAME, S.SCORE, S.PRIZESWON, A.AUTHORITY, G.COUNTER
            FROM USERDATA U
            JOIN SCOREBOARD S ON U.ID = S.ID
            JOIN AUTHORITIES A ON U.ID = A.ID
            JOIN GIORNATE G ON U.ID = G.ID
            WHERE A.AUTHORITY != ?
        """;
        RowMapper<ScoreboardUser> userRowMapper = (r, i) -> new ScoreboardUser(
                r.getString("FIRSTNAME"),
                r.getString("LASTNAME"),
                r.getString("USERNAME"),
                r.getString("AUTHORITY"),
                r.getInt("SCORE"),
                r.getInt("COUNTER"),
                r.getInt("PRIZESWON"));
        List<ScoreboardUser> new_scoreboard =  jdbcTemplate.query(sql, userRowMapper, "ROLE_" + SecurityConfig.ADMIN);
        new_scoreboard.sort(Comparator.comparing(ScoreboardUser::getScore).reversed());

        scoreboard = new ArrayList<>(new_scoreboard);

        System.out.println("[INFO]: Scoreboard refreshed");
    }

    public List<String> getPrizes() {
        String sql = "SELECT PRIZE FROM PRIZES";
        RowMapper<String> prizeRowMapper = (r, i) -> r.getString("PRIZE");
        return jdbcTemplate.query(sql, prizeRowMapper);
    }

    public void resetScoreboard() {
        String sql = "UPDATE SCOREBOARD SET SCORE = 0";
        jdbcTemplate.update(sql);
        refreshScoreboard();
        System.out.println("[INFO] Scoreboard reset");
    }

    public LocalDate getLastAccess(String username) {
        String sql = "SELECT LASTACCESS FROM GIORNATE WHERE USERNAME = ?";
        return jdbcTemplate.queryForObject(sql, LocalDate.class, username);
    }

    public void incrementPrizesWon (String username) {
        String sql = "UPDATE SCOREBOARD SET PRIZESWON = PRIZESWON + 1 WHERE USERNAME = ?";
        jdbcTemplate.update(sql, username);
        System.out.println("[INFO] Prizes won incremented for " + username);
    }

    public void incrementCounter (String username) {
        String sql = "UPDATE GIORNATE SET COUNTER = COUNTER + 1 WHERE USERNAME = ?";
        jdbcTemplate.update(sql, username);
        refreshScoreboard();
        System.out.println("[INFO] Counter incremented for " + username);
    }

    public void updateLastAccess(String username, LocalDate date) {
        String sql = "UPDATE GIORNATE SET LASTACCESS = ? WHERE USERNAME = ?";
        jdbcTemplate.update(sql, date, username);
        System.out.println("[INFO] Last access update for " + username);
    }
}
