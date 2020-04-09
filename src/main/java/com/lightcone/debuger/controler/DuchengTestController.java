package com.lightcone.debuger.controler;

import com.lightcone.debuger.dao.DuchengTestDao;
import com.lightcone.debuger.entity.DishChannelTest;
import com.lightcone.debuger.entity.DuchengMenuTest;
import com.lightcone.debuger.entity.DuchengTest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: huang xiao xian
 * @Date: 2020/3/3
 * @Des:
 */
@RestController
@RequestMapping("dish")
public class DuchengTestController {

    @Autowired
    private DuchengTestDao duchengTestDao;

    @PostMapping("add")
    public ResponseEntity<Integer> addDish(DuchengTest d) {
        DishChannelTest channel = duchengTestDao.getChannelById(d.getChannelId());
        if (channel == null || channel.getState() != 1) {
            return ResponseEntity.ok(-2);
        }

        if (StringUtils.isEmpty(d.getUsername()) || StringUtils.isEmpty(d.getDishname())) {
            return ResponseEntity.ok(3);
        }

        int res = 0;
        try {
            res = duchengTestDao.insertDish(d.getUsername(), d.getDishname(), d.getPrice(), d.getChannelId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("list")
    public ResponseEntity<List<DuchengTest>> listDish() {
        DishChannelTest channel = duchengTestDao.getWorkingChannel();
        if (channel == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<DuchengTest> duchengTests = duchengTestDao.listDish(channel.getChannelId());
        return ResponseEntity.ok(duchengTests);
    }

    @GetMapping("list/total")
    public ResponseEntity<List<DuchengTest>> listTotalDish() {
        DishChannelTest channel = duchengTestDao.getWorkingChannel();
        if (channel == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<DuchengTest> duchengTests = duchengTestDao.listTotalDish(channel.getChannelId());
        return ResponseEntity.ok(duchengTests);
    }


    @GetMapping("list/channel")
    public ResponseEntity<List<DishChannelTest>> listWorkingChannel() {
        List<DishChannelTest> dishChannelTests = duchengTestDao.listWorkingChannel();
        return ResponseEntity.ok(dishChannelTests);
    }

    @GetMapping("get/channel")
    public ResponseEntity<DishChannelTest> getWorkingChannel() {
        DishChannelTest dishChannelTest = duchengTestDao.getWorkingChannel();
        return ResponseEntity.ok(dishChannelTest);
    }

    @PostMapping("add/channel")
    public ResponseEntity<Integer> addChannel(String title) {
        DishChannelTest workingChannel = duchengTestDao.getWorkingChannel();
        if (workingChannel != null) {
            // 有正在进行的通道，不可新增
            return ResponseEntity.ok(-2);
        }
        int res = duchengTestDao.insertChannel(title);
        return ResponseEntity.ok(res);
    }

    @PostMapping("update/channel")
    public ResponseEntity<Integer> updateChannel(Long channelId, Integer state) {
        if (state == 1) {
            DishChannelTest workingChannel = duchengTestDao.getWorkingChannel();
            if (workingChannel != null) {
                // 有正在进行的通道，不可开启
                return ResponseEntity.ok(-2);
            }
        }
        int res = duchengTestDao.updateChannelState(channelId, state);
        return ResponseEntity.ok(res);
    }


    @GetMapping("get/dish/menu")
    public ResponseEntity<Map<String, List<DuchengMenuTest>>> listDishMenu() {
        List<DuchengMenuTest> menus = duchengTestDao.listDishMenu();
        Map<String, List<DuchengMenuTest>> map = new LinkedHashMap<>();
        if (menus != null && !menus.isEmpty()) {
            for (DuchengMenuTest m : menus) {
                List<DuchengMenuTest> list = null;
                String typeName = m.getTypeName();
                if (map.containsKey(typeName)) {
                    list = map.get(typeName);
                    list.add(m);
                } else {
                    list = new ArrayList<>();
                    list.add(m);
                    map.put(typeName, list);
                }
            }
        }

        return ResponseEntity.ok(map);
    }
}
