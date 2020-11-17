package cn.com.partical.development.system.developmentservice.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * MybatisPlusConfig：数据库配置
 * @author：Alex
 * @create：2019-08-19 19:01
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {MybatisPlusConfig.BASE_DAO_PACKAGES})
public class MybatisPlusConfig {

    public static final String BASE_DAO_PACKAGES = "cn.com.partical.development.developmentservice";

    /**
     * SQL执行效率插件
     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        // SQL 执行最大时长，超过自动停止运行
//        // SQL是否格式化 默认false
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

    /**
     * paginationInterceptor：
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链 阻止恶意的全表更新删除
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }

    /**
     * optimisticLockerInterceptor：乐观锁配置
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
