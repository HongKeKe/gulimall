package com.atguigu.gulimall.pms.service;

import com.atguigu.gulimall.commons.to.SkuInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.pms.entity.SkuInfoEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;


/**
 * sku信息
 *
 * @author hongweijie
 * @email 995765791@qq.com
 * @date 2020-06-10 15:11:19
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageVo queryPage(QueryCondition params);


    SkuInfoVo getSkuVo(Long skuId);

}

