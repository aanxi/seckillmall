package com.empirefree.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.common.utils.Query;
import com.empirefree.gulimall.product.dao.CategoryDao;
import com.empirefree.gulimall.product.entity.CategoryEntity;
import com.empirefree.gulimall.product.service.CategoryBrandRelationService;
import com.empirefree.gulimall.product.service.CategoryService;
import com.empirefree.gulimall.product.vo.Catalog3Vo;
import com.empirefree.gulimall.product.vo.Catelog2Vo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        return baseMapper.selectList(null);
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 检查当前节点是否被别的地方引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCateLogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        paths = findParentPath(catelogId, paths);
        // 收集的时候是顺序 前端是逆序显示的 所以用集合工具类给它逆序一下
        Collections.reverse(paths);
        return paths.toArray(new Long[paths.size()]);
    }

    /**
     * 级联更新所有数据			[分区名默认是就是缓存的前缀] SpringCache: 不加锁
     *
     * @CacheEvict: 缓存失效模式        --- 页面一修改 然后就清除这两个缓存
     * key = "'getLevel1Categorys'" : 记得加单引号 [子解析字符串]
     * @Caching: 同时进行多种缓存操作
     * @CacheEvict(value = {"category"}, allEntries = true) : 删除这个分区所有数据
     * @CachePut: 这次查询操作写入缓存
     */
//	@Caching(evict = {
//			@CacheEvict(value = {"category"}, key = "'getLevel1Categorys'"),
//			@CacheEvict(value = {"category"}, key = "'getCatelogJson'")
//	})
    @CacheEvict(value = {"category"}, allEntries = true)
//	@CachePut
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    /**
     * @Cacheable: 当前方法的结果需要缓存 并指定缓存名字
     * 缓存的value值 默认使用jdk序列化
     * 默认ttl时间 -1
     * key: 里面默认会解析表达式 字符串用 ''
     * <p>
     * 自定义:
     * 1.指定生成缓存使用的key
     * 2.指定缓存数据存活时间	[配置文件中修改]
     * 3.将数据保存为json格式
     * <p>
     * sync = true: --- 开启同步锁
     */
    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("cat_level", 1));
        // 测试能否缓存null值
//		return null;
    }

    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        List<CategoryEntity> entityList = baseMapper.selectList(null);
        // 查询所有一级分类
        List<CategoryEntity> level1 = getCategoryEntities(entityList, 0L);
        Map<String, List<Catelog2Vo>> parent_cid = level1.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 拿到每一个一级分类 然后查询他们的二级分类
            List<CategoryEntity> entities = getCategoryEntities(entityList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (entities != null) {
                catelog2Vos = entities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getName(), l2.getCatId().toString(), null);
                    // 找当前二级分类的三级分类
                    List<CategoryEntity> level3 = getCategoryEntities(entityList, l2.getCatId());
                    // 三级分类有数据的情况下
                    if (level3 != null) {
                        List<Catalog3Vo> catalog3Vos = level3.stream().map(l3 -> new Catalog3Vo(l3.getCatId().toString(), l3.getName(), l2.getCatId().toString())).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return parent_cid;
    }


    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson2() {
        String catelogJSON = stringRedisTemplate.opsForValue().get("catelogJSON");
        if (StringUtils.isEmpty(catelogJSON)){
            Map<String, List<Catelog2Vo>> catelogJsonFromDb = getCatelogJsonFromDb();
            String s = JSON.toJSONString(catelogJsonFromDb);
            stringRedisTemplate.opsForValue().set("catelogJSON", s);
        }
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>(){});
        return result;
    }


    /**
     * redis无缓存 查询数据库
     */
    private Map<String, List<Catelog2Vo>> getCatelogJsonFromDb() {
        String catelogJSON = stringRedisTemplate.opsForValue().get("catelogJSON");
        if (!StringUtils.isEmpty(catelogJSON)) {
            System.out.println("缓存命中，查询redis");
            return JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
        }
        System.out.println("缓存不命中，查询mysql");

        // 优化：将查询变为一次
        List<CategoryEntity> entityList = baseMapper.selectList(null);

        // 查询所有一级分类
        List<CategoryEntity> level1 = getCategoryEntities(entityList, 0L);
        Map<String, List<Catelog2Vo>> parent_cid = level1.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 拿到每一个一级分类 然后查询他们的二级分类
            List<CategoryEntity> entities = getCategoryEntities(entityList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (entities != null) {
                catelog2Vos = entities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getName(), l2.getCatId().toString(), null);
                    // 找当前二级分类的三级分类
                    List<CategoryEntity> level3 = getCategoryEntities(entityList, l2.getCatId());
                    // 三级分类有数据的情况下
                    if (level3 != null) {
                        List<Catalog3Vo> catalog3Vos = level3.stream().map(l3 -> new Catalog3Vo(l3.getCatId().toString(), l3.getName(), l2.getCatId().toString())).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        stringRedisTemplate.opsForValue().set("catelogJSON", JSON.toJSONString(parent_cid), 1, TimeUnit.DAYS);
        return parent_cid;
    }





    /**
     * 第一次查询的所有 CategoryEntity 然后根据 parent_cid去这里找
     */
    private List<CategoryEntity> getCategoryEntities(List<CategoryEntity> entityList, Long parent_cid) {

        return entityList.stream().filter(item -> item.getParentCid() == parent_cid).collect(Collectors.toList());
    }

    /**
     * 递归收集所有节点
     */
    private List<Long> findParentPath(Long catlogId, List<Long> paths) {
        // 1、收集当前节点id
        paths.add(catlogId);
        CategoryEntity byId = this.getById(catlogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }







    /**
     * redis没有数据 查询DB [本地锁解决方案]
     */
    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithLocalLock() {
        synchronized (this){
            return getCatelogJsonFromDb();
        }
    }




    /**
     * Author: HuYuQiao
     * Description: redis--设置、删除分布式锁--保证原子性
     */
    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithRedisLock(){
        // 1、占用分布式锁，设置30秒自动删除
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);

        if (lock){
            System.out.println("获取分布式锁成功..");
            Map<String, List<Catelog2Vo>> data = null;
            try {
                data = getCatelogJsonFromDb();
                System.out.println("查库成功");
            } finally {
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                Long lockResult = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                        Arrays.asList("lock"), uuid);
                System.out.println(lockResult);
            }
            System.out.println("返回成功");
            return data;
        } else {
            System.out.println("获取分布式锁失败..");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCatelogJsonFromDBWithRedisLock();
        }
    }
}