package com.acgnu.origin.controller;

import com.acgnu.origin.model.Image;
import com.acgnu.origin.model.ImgBrand;
import com.acgnu.origin.service.ImageLibService;
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
public class ImageLibController extends BaseController{
    @Autowired
    private ImageLibService imageLibService;

    @GetMapping("/lib/brand/all")
    public List<ImgBrand> getAllBrands(){
        return imageLibService.findAllImgBrand();
    }
    @PostMapping("/lib/all")
    public List<Image> getAllImageBeta(){
        return imageLibService.findAllImageBeta();
    }

    @GetMapping("/info/{id}")
    public Image getImageInfo(@PathVariable Long id){
        if (null == id) {
            return null;
        }
        return imageLibService.getImageInfo(id);
    }

    @PostMapping("/info/add")
    public Image getImageInfo(HttpServletRequest request, HttpServletResponse response, Image image){
        return image;
    }
}
