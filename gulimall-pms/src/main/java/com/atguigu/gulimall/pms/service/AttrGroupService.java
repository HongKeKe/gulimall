package com.atguigu.gulimall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;


/**
 * 属性分组
 *
 * @author hongweijie
 * @email 995765791@qq.com
 * @date 2020-06-10 16:29:36
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryPageAttrGroupsByCatId(QueryCondition queryCondition, Long catId);

    AttrGroupEntity getGroupByAttrId(Long attrId);

}

