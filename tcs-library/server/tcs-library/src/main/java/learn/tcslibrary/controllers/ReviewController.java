package learn.tcslibrary.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.tcslibrary.data.ItemJdbcTemplateRepository;
import learn.tcslibrary.data.ItemRepository;
import learn.tcslibrary.data.ItemShelfRepository;
import learn.tcslibrary.domain.ItemService;
import learn.tcslibrary.domain.ItemShelfService;
import learn.tcslibrary.domain.Result;
import learn.tcslibrary.domain.ReviewService;
import learn.tcslibrary.models.AppUser;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.Review;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/tcslibrary")
@CrossOrigin
public class ReviewController {

    private ReviewService reviewService;
    private UserDetailsService userDetailsService;
    private ItemService itemService;


    public ReviewController(ReviewService reviewService, UserDetailsService userDetailsService, ItemService itemService) {
        this.reviewService = reviewService;
        this.userDetailsService = userDetailsService;
        this.itemService = itemService;
    }

    @GetMapping("/reviews/{identifier}")
    public ResponseEntity<?> getAllReviewsPerReadingItem(@PathVariable String identifier) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = (AppUser) userDetailsService.loadUserByUsername(username);

        Item item = itemService.findByIdentifier(identifier);

        // grabbing reviews from db
        List<Review> allReviews = reviewService.findReviewsByItemId(item.getItemId());

        if (allReviews != null) {
            return new ResponseEntity<>(allReviews, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/reviews/add-review/{identifier}")
    public ResponseEntity<?> addNewReview(@PathVariable String identifier, @RequestBody Review review) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = (AppUser) userDetailsService.loadUserByUsername(username);
        Item item = itemService.findByIdentifier(identifier);

        review.setItemId(item.getItemId());
        review.setAppUserId(user.getAppUserId());

        Result result = reviewService.add(review);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }

        return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
