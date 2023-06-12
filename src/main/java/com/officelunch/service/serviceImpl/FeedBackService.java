package com.officelunch.service.serviceImpl;

import com.officelunch.model.FeedBack;
import com.officelunch.repositories.FeedBackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackService {
    @Autowired
    FeedBackRepo feedBackRepo;

    public String saveFeedBack(FeedBack feedBack) {

        feedBackRepo.save(feedBack);
        return "Save Success";
    }

    public List<FeedBack> showAllFeedback() {
        return feedBackRepo.showNotDeletedFeedBack();
    }

    public void deleteFeedback(int id) {
        FeedBack feedBack=feedBackRepo.findById(id).get();
        feedBack.setId(id);
        feedBack.setIsDeleted(true);
        feedBackRepo.save(feedBack);
    }
}
