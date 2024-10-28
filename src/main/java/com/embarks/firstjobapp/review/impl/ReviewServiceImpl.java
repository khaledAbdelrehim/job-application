package com.embarks.firstjobapp.review.impl;

import com.embarks.firstjobapp.company.Company;
import com.embarks.firstjobapp.company.CompanyService;
import com.embarks.firstjobapp.review.Review;
import com.embarks.firstjobapp.review.ReviewRepository;
import com.embarks.firstjobapp.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CompanyService companyService;

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);

        if(company != null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        return getReviewByCompanyId(companyId, reviewId);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {

        if(getReviewByCompanyId(companyId, reviewId) != null){
            updatedReview.setCompany(companyService.getCompanyById(companyId));
            updatedReview.setId(reviewId);
            reviewRepository.save(updatedReview);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {

        if(getReviewByCompanyId(companyId, reviewId) != null){
            reviewRepository.deleteById(reviewId);
            return true;
        }else{
            return false;
        }
    }

    private Review getReviewByCompanyId(Long companyId, Long reviewId){
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }
}
