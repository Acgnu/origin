package com.acgnu.origin.service;

import com.acgnu.origin.entity.Image;
import com.acgnu.origin.entity.ImgBrand;
import com.acgnu.origin.repository.ImageRepository;
import com.acgnu.origin.repository.ImgBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GalleryService {
    @Autowired
    private ImgBrandRepository imgBrandRepository;
    @Autowired
    private ImageRepository imageRepository;

    public List<ImgBrand> findAllImgBrand(){
        return imgBrandRepository.findAll();
    }

    public List<Image> findAllImage(){
        return imageRepository.findAll();
    }

    public Image getImageInfo(long id) {
        var optional = imageRepository.findById(id);
        return optional.orElseGet(Image::new);
    }
}
