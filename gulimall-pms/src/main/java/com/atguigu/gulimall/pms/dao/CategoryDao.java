package com.atguigu.gulimall.pms.dao;

import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author hongweijie
 * @email 995765791@qq.com
 * @date 2020-06-10 15:11:19
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
