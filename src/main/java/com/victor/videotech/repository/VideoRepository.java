package com.victor.videotech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.victor.videotech.models.Video;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

/*    List<Video> findAll();
    Optional<Video> findById(Integer id);*/

}
