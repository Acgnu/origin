package com.acgnu.origin.controller;

import com.acgnu.origin.model.Image;
import com.acgnu.origin.model.ImgBrand;
import com.acgnu.origin.service.ImageLibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 图床控制器
 */
@RestController
@RequestMapping("/img")
public class ImageLibController {
    @Autowired
    private ImageLibService imageLibService;

    @GetMapping("/lib/brand/all")
    public List<ImgBrand> getAllBrands(){
        return imageLibService.findAllImgBrand();
    }

    @GetMapping("/info/{id}")
    public Image getImageInfo(@PathVariable Long id){
        if (null == id) {
            return null;
        }
        return imageLibService.getImageInfo(id);
    }
}
