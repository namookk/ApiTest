package com.github.prgrms.apitest.orders;

import com.github.prgrms.apitest.errors.NotFoundException;
import com.github.prgrms.apitest.orders.ReviewService;
import com.github.prgrms.apitest.products.ProductDto;
import com.github.prgrms.apitest.products.ProductService;
import com.github.prgrms.apitest.security.JwtAuthentication;
import com.github.prgrms.apitest.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.github.prgrms.apitest.utils.ApiUtils.success;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {
    // TODO review 메소드 구현이 필요합니다.
    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "{id}/review")
    public ApiUtils.ApiResult<ReviewDto> review(@PathVariable Long id, @RequestBody Map<String,String> body
            ,@AuthenticationPrincipal JwtAuthentication authentication
    ) {
        Review review = new Review(authentication.id, id, body.get("content"));
        return success(
                reviewService.save(review)
                        .map(ReviewDto::new).orElse(null)
        );
    }
}