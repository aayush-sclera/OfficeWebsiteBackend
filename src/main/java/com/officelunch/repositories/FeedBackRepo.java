package com.officelunch.repositories;

import com.officelunch.model.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepo extends JpaRepository<FeedBack,Integer> {
    @Query(value = "select * from officeLunch.feed_back where is_deleted=false order by id desc",nativeQuery = true)
    List<FeedBack> showNotDeletedFeedBack();
}
