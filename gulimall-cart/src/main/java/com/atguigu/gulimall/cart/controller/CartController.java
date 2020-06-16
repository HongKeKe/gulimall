package com.atguigu.gulimall.cart.controller;


import com.atguigu.gulimall.cart.service.CartService;
import com.atguigu.gulimall.cart.vo.CartVo;
import com.atguigu.gulimall.cart.vo.ClearCartSkuVo;
import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.commons.utils.GuliJwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Api(tags = "购物车系统")
@RequestMapping("/cart")
@RestController
public class CartController {


    @Autowired
    CartService cartService;

    @Autowired
    @Qualifier("otherExecutor")
    ThreadPoolExecutor executor;

    @GetMapping("/clearCartSku")
    public Resp<Object> clearSkuIds(@RequestBody ClearCartSkuVo skuVo){

        cartService.clearSkuIds(skuVo);
        return Resp.ok(null);

    }


    @ApiOperation("返回购物车中所有选中的额商品以及总价格，优惠等信息")
    @GetMapping("/getItemsForOrder")
    public Resp<CartVo> getCartCheckItemsAndStatics(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Map<String, Object> jwtBody = GuliJwtUtils.getJwtBody(authorization);
        Long id = Long.parseLong(jwtBody.get("id").toString());


        CartVo cartVo = cartService.getCartForOrder(id);

        return Resp.ok(cartVo);
    }


    /**
     * 可以在业务运行期间通过运维控制平台，关闭一些资源，释放   关闭指定的（非核心）线程池
     * @return
     */
    @GetMapping("/stop/other")
    public Resp<Object> closeThreadPool(){

        int activeCount = executor.getActiveCount();  //当前有多少个活动线程
        int corePoolSize = executor.getCorePoolSize();  //核心数

        executor.shutdown();   //等正在线程池内执行的人执行完 还在排队的才不管了关掉

        Map<String,Object> map = new HashMap<>();
        map.put("closeQueue",executor.getQueue().size());  //释放了队列里排队的多少 统计数量
        map.put("waitActiveCount",executor.getActiveCount());  //还正在运行的活动线程数量
        return Resp.ok(map);
    }


    /**
     * @RequestParam("skuIds") Long[] skuId, 必须的
     * @RequestParam("status") Integer status,
     * String userKey,从请求参数中取，不是必须的 == @RequestParam(value="skuIds",required=false)
     *
     * 某个请求参数有多个值封装数组：
     *  传：skuId=1&skuId=2&skuId=3&skuId=4#
     *  封：@RequestParam("skuId") Long[] skuId,
     * @param skuId
     * @param status  0代表不选中   1代表选中
     * @param userKey
     * @param authorization
     * @return
     */
    @ApiOperation("选中/不选中购物车")
    @PostMapping("/check")
    public Resp<CartVo> checkCart(@RequestParam("skuIds") Long[] skuId,
                                  @RequestParam("status") Integer status,
                                  String userKey,
                                  @RequestHeader(name = "Authorization",required = false) String authorization){


        CartVo cartVo = cartService.checkCart(skuId,status,userKey,authorization);

        return Resp.ok(cartVo);
    }


    @ApiOperation("更新购物车商品数量")
    @PostMapping("/update")
    public Resp<CartVo> updateCart(@RequestParam(name = "skuId",required = true) Long skuId,
                                   @RequestParam(name = "num",defaultValue = "1") Integer num,
                                   String userKey,
                                   @RequestHeader(name = "Authorization",required = false) String authorization){

        CartVo cartVo = cartService.updateCart(skuId,num,userKey,authorization);

        return Resp.ok(cartVo);
    }


    /**
     * 以后购物车的所有操作，前端带两个令牌
     * 1）、登录以后的jwt放在请求头的 Authorization 字段（不一定带）
     * 2）、只要前端有收到服务器响应过一个userKey的东西，以后保存起来，访问所有请求都带上；（不一定）
     * @return
     */
    @ApiOperation("获取购物车中的数据")
    @GetMapping("/list")
    public Resp<CartVo> getCart(String userKey,
                                @RequestHeader(name = "Authorization",required = false)
                                        String authorization) throws ExecutionException, InterruptedException {


        CartVo cartVo =   cartService.getCart(userKey,authorization);

        return Resp.ok(cartVo);
    }


    /**
     *
     * @param skuId
     * @param num
     * @param userKey  临时用户的令牌。如果有就穿
     * @return
     */
    @ApiOperation("将某个sku添加到购物车")
    @PostMapping("/add")
    public Resp<Object> addToCart(@RequestParam(name = "skuId",required = true) Long skuId,
                                  @RequestParam(name = "num",defaultValue = "1") Integer num,
                                  String userKey,
                                  @RequestHeader(name = "Authorization",required = false) String authorization) throws ExecutionException, InterruptedException {

        //1、判断是否登录了
        CartVo cartVo = cartService.addToCart(skuId, num, userKey, authorization);

        Map<String,Object> map = new HashMap<>();
        map.put("userKey",cartVo.getUserKey());
        map.put("item",cartVo.getItems());
        return Resp.ok(map);
    }
}