package com.github.prgrms.apitest.orders;

import com.github.prgrms.apitest.products.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Review> findByUserSeqAndProductSeq(Long userSeq, Long productSeq) {
        checkNotNull(userSeq, "userSeq must be provided");
        checkNotNull(productSeq, "productSeq must be provided");

        return reviewRepository.findByUserSeqAndProductSeq(userSeq, productSeq);
    }

    @Transactional(readOnly = false)
    public Optional<Review> save(Review review){
        Optional<Review> reviewOp = this.findByUserSeqAndProductSeq(review.getUserSeq(), review.getProductSeq());
        if(reviewOp.isPresent()) throw new RuntimeException("Could not write review for order 4 because have already written");

        return reviewRepository.save(review);
    }
}
