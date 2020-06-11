# 项目文档 
## Nacos使用
自己参照文档下载解压运行

- 1、导入nacos服务注册发现功能的jar包
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>${nacos.version}</version>
        </dependency>
```
- 2、开启服务注册发现功能
```java
@Configuration
@EnableDiscoveryClient
public class PmsCloudConfig {
}
```
- 3、 编写application.properties配置，说明nacos的地址即可
```properties
spring.application.name=gulimall-pms
#指定注册中心地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
```
- 4、测试远程调用
- 1、导入feign进行远程调用功能
```xml
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>
```
- 2、消费端的写法
配置类
```java
@Configuration
@EnableDiscoveryClient //开启服务注册发现功能
@EnableFeignClients(basePackages = "com.atguigu.gulimall.oms.feign") //开启feign的远程调用功能，一定要配置feign接口所在的包不然扫描不到
public class OmsCloudConfig {
}
```
```java
@RestController
public class HelloController {
    @Autowired
    WorldService worldService;

    /**
     * feign声明式调用
     * @return
     */
    @GetMapping("/hello")
    public String Hello(){
        String msg = worldService.world();
        return "hello"+msg;
    }
}
```

### 3、Nacos作为配置中心
配置中心：
    集中管理配置，配置动态更新，回滚配置。。。。
    
- 1、导包
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>${nacos.version}</version>
        </dependency>
```

- 2、写配置
创建一个 bootstrap.properties; 里面的配置优先于application.properties执行
```properties
spring.application.name=gulimall-oms
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
```
- 3、默认规则

- 4、想要动态获取配置，一个注解
@RefreshScope