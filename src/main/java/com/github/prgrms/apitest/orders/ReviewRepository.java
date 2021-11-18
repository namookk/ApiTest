package com.github.prgrms.apitest.orders;

import com.github.prgrms.apitest.products.Product;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Optional<Review> save(Review review);
    Optional<Review> findById(Long seq);
    Optional<Review> findByUserSeqAndProductSeq(Long userSeq, Long productSeq);
}