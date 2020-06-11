package com.atguigu.gulimall.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.wms.entity.WareOrderTaskEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;


/**
 * 库存工作单
 *
 * @author hongweijie
 * @email 995765791@qq.com
 * @date 2020-06-10 18:14:17
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageVo queryPage(QueryCondition params);
}
