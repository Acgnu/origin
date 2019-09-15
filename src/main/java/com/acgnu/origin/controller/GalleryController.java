package com.acgnu.origin.controller;

import com.acgnu.origin.entity.Image;
import com.acgnu.origin.entity.ImgBrand;
import com.acgnu.origin.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 图床控制器
 */
@RestController
@RequestMapping("/img")
public class GalleryController extends BaseController{
    @Autowired
    private GalleryService galleryService;

    @GetMapping("/lib/brand/all")
    public List<ImgBrand> getAllBrands(){
        return galleryService.findAllImgBrand();
    }

    @PostMapping("/lib/all")
    public List<Image> getAllImageBeta(){
        return galleryService.findAllImage();
    }

    @GetMapping("/info/{id}")
    public Image getImageInfo(@PathVariable Long id){
        return galleryService.getImageInfo(id);
    }

    @PostMapping("/info/add")
    public Image getImageInfo(HttpServletRequest request, HttpServletResponse response, Image image){
        return image;
    }
}
