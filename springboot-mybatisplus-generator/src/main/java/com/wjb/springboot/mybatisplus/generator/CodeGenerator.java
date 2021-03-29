package com.wjb.springboot.mybatisplus.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <b><code>CodeGenerator</code></b>
 * <p/>
 * Description 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 * <p/>
 * <b>Creation Time:</b> 2021/3/22 23:58.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String projectPath = System.getProperty("user.dir");

        /*
         1.全局策略配置
         */
        GlobalConfig gc = new GlobalConfig();
        // 生成文件的输出目录，默认值：D 盘根目录
        gc.setOutputDir(projectPath + "/springboot-mybatisplus-generator/src/main/java");
        // 是否覆盖已有文件，默认值：false
        gc.setFileOverride(true);
        // 是否打开输出目录，默认值：true
        gc.setOpen(false);
        // 是否在xml中添加二级缓存配置，默认值：false
        gc.setEnableCache(false);
        // 开发人员类型注释上@author，默认值：null
        gc.setAuthor("Arjun");
        // 开启 Kotlin 模式，默认值：false
        gc.setKotlin(false);
        // 开启 swagger2 模式，实体属性 Swagger2注解，默认值：false
        gc.setSwagger2(true);
        // 开启 ActiveRecord 模式，默认值：false
        gc.setActiveRecord(false);
        // 开启 BaseResultMap，默认值：false
        gc.setBaseResultMap(false);
        // 开启 baseColumnList，默认值：false
        gc.setBaseColumnList(false);
        // 时间类型对应策略，默认值：TIME_PACK
        // ONLY_DATE - 只使用 java.util.date 代替
        // SQL_PACK - 使用 java.sql 包下的
        // TIME_PACK - 使用 java.time 包下的 java8 新的时间类型
        gc.setDateType(DateType.ONLY_DATE);
        // 实体命名方式，默认值：null 例如：%sEntity 生成 UserEntity
        gc.setEntityName(null);
        // 如下配置 %s 为占位符
        // mapper 命名方式，默认值：null 例如：%sDao 生成 UserDao
        gc.setMapperName("%sMapper");
        // Mapper xml 命名方式，默认值：null 例如：%sDao 生成 UserDao.xml
        gc.setXmlName(null);
        // service 命名方式，默认值：null 例如：%sBusiness 生成 UserBusiness
        gc.setServiceName("%sService");
        // service impl 命名方式，默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setServiceImplName("%sServiceImpl");
        // controller 命名方式，默认值：null 例如：%sAction 生成 UserAction
        gc.setControllerName("%sController");
        // 指定生成的主键的ID类型，默认值：null
        gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);

        /*
         2.数据源配置，通过该配置，指定需要生成代码的具体数据库
         */
        DataSourceConfig dsc = new DataSourceConfig();
        // 数据库信息查询类 默认由 dbType 类型决定选择对应数据库内置实现 实现 IDbQuery 接口自定义数据库查询 SQL 语句 定制化返回自己需要的内容
//        dsc.setDbQuery();
        // 数据库类型 该类内置了常用的数据库类型【必须】，通过url自动获取
//        dsc.setDbType();
        // 数据库 schema name，例如 PostgreSQL 可指定为 public
        dsc.setSchemaName("public");
        // 类型转换，默认由 dbType 类型决定选择对应数据库内置实现
//        dsc.setTypeConvert();
        // 驱动连接的URL
        dsc.setUrl("jdbc:postgresql://192.168.108.24:31383/se_db?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // 驱动名称
        dsc.setDriverName("org.postgresql.Driver");
        // 数据库连接用户名
        dsc.setUsername("postgres");
        // 数据库连接密码
        dsc.setPassword("svs@2021!");
        mpg.setDataSource(dsc);

        /*
         3.注入配置，通过该配置，可注入自定义参数等操作以实现个性化操作
         自定义配置 injectionConfig
         */
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        /*
         4.包名配置，通过该配置，指定生成代码的包路径
         */
        PackageConfig pc = new PackageConfig();
        // 父包模块名
//        pc.setModuleName(scanner("模块名"));
        // 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent("com.richstonedt.svs.se.cs");
        // Entity包名，默认值：entity
        pc.setEntity("po");
        // Service包名，默认值：service
        pc.setService("service");
        // Service Impl包名，默认值：service.impl
        pc.setServiceImpl("service.impl");
        // Mapper包名，默认值：mapper
        pc.setMapper("mapper");
        // Mapper XML包名，默认值：mapper.xml
        pc.setXml("mapper.xml");
        // Controller包名，默认值：controller
        pc.setController("controller");
        // 路径配置信息
//        pc.setPathInfo();
        mpg.setPackageInfo(pc);

        /*
         5.模板配置，可自定义代码生成的模板，实现个性化操作，具体请查看
         默认值：null
         如果模板引擎是 velocity
         */
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/springboot-mybatisplus-generator/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        /*
         6.数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
         */
        StrategyConfig strategy = new StrategyConfig();
        // 是否大写命名，默认值为:false
//        strategy.setCapitalMode(false);
        // 是否跳过视图
//        strategy.setSkipView(false);
        // 数据库表映射到实体的命名策略,下划线转驼峰命名：underline_to_camel,不做任何改变，原样输出：no_change
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 表前缀
        strategy.setTablePrefix("se_");
        // 字段前缀
//        strategy.setFieldPrefix();
        // 自定义继承的Entity类全称，带包名
//        strategy.setSuperEntityClass("com.wjb.springboot.mybatisplus.generator.model.BaseEntity");
        // 自定义基础的Entity类，公共字段
//        strategy.setSuperEntityColumns("id", "created_time");
        // 自定义继承的Mapper类全称，带包名
//        strategy.setSuperMapperClass(),
        // 自定义继承的Service类全称，带包名
//        strategy.setSuperServiceClass();
        // 自定义继承的ServiceImpl类全称，带包名
//        strategy.setSuperServiceImplClass();
        // 自定义继承的Controller类全称，带包名
//        strategy.setSuperControllerClass();
        // 默认激活进行sql模糊表名匹配, 默认值为true
        //关闭之后likeTable与notLikeTable将失效，include和exclude将使用内存过滤
        //如果有sql语法兼容性问题的话，请手动设置为false
        //已知无法使用：MyCat中间件， 支持情况传送门
        strategy.setEnableSqlFilter(true);
        // 需要包含的表名，当enableSqlFilter为false时，允许正则表达式（与exclude二选一配置）
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // 自3.3.0起，模糊匹配表名（与notLikeTable二选一配置）
//        strategy.setLikeTable()
        // 需要排除的表名，当enableSqlFilter为false时，允许正则表达式
//        strategy.setExclude();
        // 自3.3.0起，模糊排除表名
//        strategy.setNotLikeTable();
        // 【实体】是否生成字段常量（默认 false）
//        strategy.setEntityColumnConstant(false);
        // 【实体】是否为链式模型（默认 false）
//        strategy.setChainModel(false);
        // 【实体】是否为lombok模型（默认 false） 3.3.2以下版本默认生成了链式模型，3.3.2以后，默认不生成，如有需要，请开启 chainModel
        strategy.setEntityLombokModel(true);
        // Boolean类型字段是否移除is前缀（默认 false）
//        strategy.setEntityBooleanColumnRemoveIsPrefix(false);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(false);
        // 是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 乐观锁属性名称
//        strategy.setVersionFieldName("version");
        // 逻辑删除属性名称
//        strategy.setLogicDeleteFieldName("delete");
        // 表填充字段
        strategy.setTableFillList(getTableFillList());

        mpg.setStrategy(strategy);

        // 执行
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    /**
     * 表填充字段
     */
    public static List<TableFill> getTableFillList(){
        return new ArrayList<TableFill>(){{
            add(new TableFill("created_time", FieldFill.INSERT));
            add(new TableFill("updated_time", FieldFill.INSERT_UPDATE));
        }};
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}