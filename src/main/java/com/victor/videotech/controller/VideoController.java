package com.victor.videotech.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.victor.videotech.models.Video;
import com.victor.videotech.services.VideoService;
import com.victor.videotech.exception.VideoNotFoundException;

import javax.validation.UnexpectedTypeException;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/video/")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("list")
    public String getAllVideos(Model model)
    {
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        return "video/list";
    }

    @GetMapping("edit/{id}")
    public String findVideoById(@PathVariable(value = "id") Integer id, Model model) {
        Optional videoOptional = videoService.findById(id);
        if(videoOptional.isPresent()) {
            Video video =(Video) videoOptional.get();
            model.addAttribute("video", video);
            return "video/details";
        }
        else {
            List<Video> videos = videoService.findAll();
            model.addAttribute("videos", videos);
            model.addAttribute("message", "Pas de video avec l'Id" + " " + id);
            return "redirect:video/list";
        }
    }

    @GetMapping("create")
    private String setupForm(Model model){
        Video video = new Video();
        model.addAttribute("video", video);
        return "video/create";
    }

    @PostMapping("create")
    private String insertVideo(Video video, Model model){

        /*if (video.getId() == null) {
            videoService.save(video);
            String message  = "La video est deja presente dans votre gestionnaire";
            model.addAttribute("message", message);
            return "video/create";
        }else{
            List<Video> videos = videoService.findAll();
            model.addAttribute("videos", videos);
            return "video/list";
        }*/

        try {
            if (video.getId() == null) {
                videoService.save(video);
            }

        }catch(TransactionSystemException | UnexpectedTypeException e){
            String message = "Vérifier l'année saisie";
            model.addAttribute("message", message);
            return "video/create";

        } catch(DataIntegrityViolationException e) {
            String message = "La vidéo est déjà présente dans votre gestionnaire";
            model.addAttribute("message", message);
            return "video/create";
        }
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        return "video/list";

        /*            JdbcSQLIntegrityConstraintViolationException
            ConstraintViolationException
            DataIntegrityViolationException

            TransactionSystemException
            RollbackException
            UnexpectedTypeException

            */
    }

    @GetMapping("delete/{id}")
    public String deleteVideo(@PathVariable(value = "id") Integer id, Model model) throws VideoNotFoundException {
        Optional videoOptional = videoService.findById(id);
        if(videoOptional.isPresent()) {
            Video video = (Video)videoOptional.get();
            videoService.deleteById(video.getId());
        }
        else {
            throw new VideoNotFoundException("La vidéo avec l'id " + id + " n'a pas été trouvée.");
        }
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);

        return "redirect:/video/list";
    }

    @PostMapping("update/{id}")
    private String updateVideo(Video video, Model model) throws VideoNotFoundException {
        if(video.getId() != null) {
            videoService.update(video.getId(),video);
            List<Video> videos = videoService.findAll();
            model.addAttribute("videos", videos);
        }
        return "redirect:/video/list";
    }
}
