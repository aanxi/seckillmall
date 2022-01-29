package com.empirefree.gulimall.product.web;


import com.empirefree.gulimall.product.entity.CategoryEntity;
import com.empirefree.gulimall.product.service.CategoryService;
import com.empirefree.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: IndexController</p>
 * Description：
 * date：2020/6/9 14:01
 */
@Controller
public class IndexController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;



	@GetMapping("/lockDoor")
	@ResponseBody
	public String lockDoor() throws InterruptedException {
		RCountDownLatch door = redissonClient.getCountDownLatch("door");
		door.trySetCount(5);
		door.await();

		return "放假了..";
	}

	@GetMapping("/gogogo/{id}")
	@ResponseBody
	public String gogaogao(@PathVariable("id") Long id) throws InterruptedException {
		RCountDownLatch door = redissonClient.getCountDownLatch("door");
		door.countDown();

		return id + "走了....";
	}



	@GetMapping("/park")
	@ResponseBody
	public String park() throws InterruptedException {
		RSemaphore park = redissonClient.getSemaphore("park");
//		park.acquire();			//阻塞等待
		boolean b = park.tryAcquire();     //非阻塞，直接返回false

		return "ok" + b;
	}

	@GetMapping("/go")
	@ResponseBody
	public String go(){
		RSemaphore park = redissonClient.getSemaphore("park");
		park.release();

		return "ok";
	}



	@GetMapping("/write")
	@ResponseBody
	public String writeValue(){
		RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
		String s = "";
		RLock rLock = lock.writeLock();
		try {
			rLock.lock();
			s = UUID.randomUUID().toString();
			TimeUnit.SECONDS.sleep(30);
			stringRedisTemplate.opsForValue().set("writeValue", s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}
		return s;
	}

	@GetMapping("/read")
	@ResponseBody
	public String  readValue(){
		RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
		String s = "";
		RLock rLock = lock.readLock();
		rLock.lock();
		try {
			s = stringRedisTemplate.opsForValue().get("writeValue");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}

		return s;
	}







	@ResponseBody
	@RequestMapping("/hello")
	public String hello(){
		RLock lock = redissonClient.getLock("my-lock");
		lock.lock();
		try {
			System.out.println("加锁成功...." + Thread.currentThread().getId());
			TimeUnit.SECONDS.sleep(30);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("释放锁..." + Thread.currentThread().getId());
			lock.unlock();
		}

		return "hello";
	}






	@RequestMapping({"/", "index", "/index.html"})
	public String indexPage(Model model) {
		// 获取一级分类所有缓存
		List<CategoryEntity> categorys = categoryService.getLevel1Categorys();
		model.addAttribute("categorys", categorys);
		return "index";
	}

	@Cacheable(value = {"category"}, key = "#root.method.name")
	@ResponseBody
	@RequestMapping("/index/catalog.json")
	public Map<String, List<Catelog2Vo>> getCatlogJson() {

		Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson();
		return map;
	}

	@ResponseBody
	@RequestMapping("/index/catalog2.json")
	public Map<String, List<Catelog2Vo>> getCatlogJson2() {

		Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson2();
		return map;
	}


	@ResponseBody
	@RequestMapping("/index/catelogJsonFromDBWithLocalLock.json")
	public Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithLocalLock() {

		Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJsonFromDBWithLocalLock();
		return map;
	}

	@ResponseBody
	@RequestMapping("/index/catelogJsonFromDBWithRedisLock.json")
	public Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithRedisLock() {

		Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJsonFromDBWithRedisLock();
		return map;
	}

}
