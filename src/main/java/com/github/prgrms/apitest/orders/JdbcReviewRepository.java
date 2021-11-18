package com.github.prgrms.apitest.orders;

import com.github.prgrms.apitest.products.Product;
import com.github.prgrms.apitest.products.ProductRepository;
import com.github.prgrms.apitest.users.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static com.github.prgrms.apitest.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Review> save(Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        java.sql.Date createAt = java.sql.Date.valueOf(review.getCreateAt().toLocalDate());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO reviews(user_seq, product_seq, content, create_at) value(?,?,?,?)");
            ps.setLong(1, review.getUserSeq());
            ps.setLong(2, review.getProductSeq());
            ps.setString(3, review.getContent().get());
            ps.setDate(4, createAt);
            return ps;
        }, keyHolder);

        return this.findById((Long)keyHolder.getKey());
    }

    @Override
    public Optional<Review> findById(Long seq) {
        List<Review> results = jdbcTemplate.query(
                "SELECT * FROM reviews WHERE seq=?",
                mapper,
                seq
        );

        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Optional<Review> findByUserSeqAndProductSeq(Long userSeq, Long productSeq) {
        List<Review> results = jdbcTemplate.query(
                "SELECT * FROM reviews WHERE user_seq=? and product_seq=?",
                mapper,
                userSeq,
                productSeq
        );

        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    static RowMapper<Review> mapper = (rs, rowNum) ->
            new Review.Builder()
                    .seq(rs.getLong("seq"))
                    .userSeq(rs.getLong("user_seq"))
                    .productSeq(rs.getLong("product_seq"))
                    .content(rs.getString("content"))
                    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
                    .build();
}