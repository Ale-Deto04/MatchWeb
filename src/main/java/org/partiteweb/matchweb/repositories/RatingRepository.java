package org.partiteweb.matchweb.repositories;

import org.partiteweb.matchweb.classes.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addRating(Rating rating) {
        String sql = "INSERT INTO RATINGS VALUES (DEFAULT, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                rating.getAuthor(),
                rating.getRate(),
                java.sql.Date.valueOf(rating.getDate()),
                rating.getText()
        );
        System.out.println("[INFO]: Rating added");
    }

    public List<Rating> getRatings() {
        String sql = "SELECT USERNAME, RATE, DATE, TEXT FROM RATINGS";
        RowMapper<Rating> ratingRowMapper = (r, i) -> new Rating(
            r.getInt("RATE"),
            r.getString("USERNAME"),
            r.getDate("DATE").toString(),
            r.getString("TEXT")
        );
        return jdbcTemplate.query(sql, ratingRowMapper);
    }
}
