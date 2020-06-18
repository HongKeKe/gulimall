package com.atguigu.gulimall.wms.dao;

import com.atguigu.gulimall.wms.entity.WareSkuEntity;
import com.atguigu.gulimall.wms.vo.SkuLock;
import com.atguigu.gulimall.wms.vo.SkuLockVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品库存
 * 
 * @author hongweijie
 * @email 995765791@qq.com
 * @date 2020-06-10 18:14:17
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void unLockSku(SkuLock skuLock);

    List<WareSkuEntity> getAllWareCanLocked(SkuLockVo skuId);

    long lockSku(SkuLockVo skuId, Long wareId);
}


