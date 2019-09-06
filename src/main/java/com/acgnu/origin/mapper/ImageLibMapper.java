package com.acgnu.origin.mapper;

import com.acgnu.origin.model.Image;
import com.acgnu.origin.model.ImgBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageLibMapper  extends BaseMapper<Image> {
    List<ImgBrand> findAllImgBrand();

    Image getImageInfo(long id);
}
