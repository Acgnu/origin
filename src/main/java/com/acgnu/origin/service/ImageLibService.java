package com.acgnu.origin.service;

import com.acgnu.origin.mapper.ImageLibMapper;
import com.acgnu.origin.model.Image;
import com.acgnu.origin.model.ImgBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageLibService {
    @Autowired
    private ImageLibMapper imageLibMapper;

    public List<ImgBrand> findAllImgBrand(){
        return null;
    }

    public List<Image> findAllImageBeta(){
        return imageLibMapper.selectList(null);
    }

    public Image getImageInfo(long id) {
        return imageLibMapper.selectById(id);
//        return imageLibMapper.getImageInfo(id);
    }
}
