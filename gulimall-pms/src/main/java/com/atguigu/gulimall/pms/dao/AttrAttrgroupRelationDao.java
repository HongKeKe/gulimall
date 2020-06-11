package com.atguigu.gulimall.pms.dao;

import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 属性&属性分组关联
 * 
 * @author hongweijie
 * @email 995765791@qq.com
 * @date 2020-06-10 16:29:36
 */
@Repository
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {
	
}
