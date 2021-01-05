package com.victor.videotech.services;

import com.victor.videotech.exception.VideoNotFoundException;
import com.victor.videotech.models.Video;
import com.victor.videotech.repository.VideoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    final
    VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    public Optional<Video> findById(Integer id) {
        return videoRepository.findById(id);
    }

    public void save(Video video) {
        videoRepository.save(video);
    }

    public void update(Integer id, Video video) throws VideoNotFoundException {
        Optional<Video> videoOptional = videoRepository.findById(id);
        if(videoOptional.isPresent()) {
            Video temp = videoOptional.get();
            temp.setTitle(video.getTitle());
            temp.setGenre(video.getGenre());
            temp.setDescription(video.getDescription());
            temp.setYear(video.getYear());
            videoRepository.save(temp);
        }
        else {
            throw new VideoNotFoundException("Impossible de mettre à jour la video.");
        }
    }

    public void deleteById(Integer id) {
        videoRepository.deleteById(id);
    }

}
