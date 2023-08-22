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

    @GetMapping("/reviews/{internetArchiveIdentifier}")
    public ResponseEntity<?> getAllReviewsPerReadingItem(@PathVariable String internetArchiveIdentifier) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = (AppUser) userDetailsService.loadUserByUsername(username);
//        how to add user to review so we can display username

        Item item = itemService.findByInternetArchiveId(internetArchiveIdentifier);

        // grabbing reviews from db
        List<Review> allReviews = reviewService.findReviewsByItemId(item.getItemId());

        if (allReviews != null) {
            return new ResponseEntity<>(allReviews, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/reviews/add-review/{internetArchiveId}")
    public ResponseEntity<?> addNewReview(@PathVariable String internetArchiveId, @RequestBody Review review) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = (AppUser) userDetailsService.loadUserByUsername(username);

        Result result = reviewService.add(review);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
