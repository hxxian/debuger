package com.lightcone.debuger.dao;

import com.lightcone.commons.base.repository.DbEngine;
import com.lightcone.debuger.entity.DishChannelTest;
import com.lightcone.debuger.entity.DuchengMenuTest;
import com.lightcone.debuger.entity.DuchengTest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @Author: huang xiao xian
 * @Date: 2020/3/3
 * @Des:
 */
@Repository
public class DuchengTestDao {

    private static final String INSERT =
            "insert into test_ducheng (channel_id, username, dish_name, price, gmt_create, gmt_update) " +
                    "values(?, ?, ?, ?, now(), now()) on duplicate key update dish_name = ?, price = ?, gmt_update = now()";

    private static final String LIST_DISH =
            "select * from test_ducheng where channel_id = ? and gmt_create >= ? and gmt_create <= ? order by gmt_create desc";

    private static final String LIST_TOTAL_DISH =
            "SELECT dish_name, price, COUNT(*) total_count, SUM(price) total_price FROM test_ducheng WHERE channel_id = ? GROUP BY dish_name";

    public Integer insertDish(String username, String dishname, Integer price, Long channelId) {
        return DbEngine.getInstance().doWorkDBExecuteUpdate(INSERT, new Object[]{channelId, username, dishname, price, dishname, price});
    }

    public List<DuchengTest> listTotalDish(Long channelId) {
        return DbEngine.getInstance()
                .doWorkDBExecuteQuery(LIST_TOTAL_DISH, new Object[]{channelId}, DuchengTest.BUILDER);
    }

    public List<DuchengTest> listDish(Long channel_id) {
        LocalDateTime now = LocalDateTime.now();
        Date s = new Date();
        Date e = new Date();
        int hour = now.getHour();
        if (hour < 12) {
            LocalDateTime start = now.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime end = now.withHour(12).withMinute(0).withSecond(0);
            long ls = start.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
            long le = end.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
            s.setTime(ls);
            e.setTime(le);
        } else {
            LocalDateTime start = now.withHour(12).withMinute(0).withSecond(0);
            LocalDateTime end = now.withHour(23).withMinute(59).withSecond(59);
            long ls = start.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
            long le = end.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
            s.setTime(ls);
            e.setTime(le);
        }
        return DbEngine.getInstance().doWorkDBExecuteQuery(LIST_DISH,
                new Object[]{channel_id, s, e}, DuchengTest.BUILDER);
    }


    /*****************************************************************************/

    private static final String INSERT_CHANNEL = "insert into test_dish_channel (title, gmt_create, gmt_update) values (?, now(), now())";
    private static final String LIST_WORKING_CHANNEL = "select * from test_dish_channel where show_flag = 1 order by channel_id desc";
    private static final String GET_WORKING_CHANNEL = "select * from test_dish_channel where state = 1 or state = 2 order by channel_id desc limit 1";
    private static final String UPDATE_CHANNEL_STATE = "update test_dish_channel set state = ?, show_flag = ? where channel_id = ?";
    private static final String GET_CHANNEL_BY_ID = "select * from test_dish_channel where channel_id = ?";

    public Integer insertChannel(String title) {
        return DbEngine.getInstance()
                .doWorkDBExecuteUpdate(INSERT_CHANNEL, new Object[]{title});
    }

    public List<DishChannelTest> listWorkingChannel() {
        return DbEngine.getInstance()
                .doWorkDBExecuteQuery(LIST_WORKING_CHANNEL, DishChannelTest.BUILDER);
    }

    public DishChannelTest getWorkingChannel() {
        return DbEngine.getInstance()
                .doWorkDBExecuteScalarObject(GET_WORKING_CHANNEL, DishChannelTest.BUILDER)
                .orElse(null);
    }

    public DishChannelTest getChannelById(Long channelId) {
        return DbEngine.getInstance()
                .doWorkDBExecuteScalarObject(GET_CHANNEL_BY_ID, new Object[]{channelId}, DishChannelTest.BUILDER)
                .orElse(null);
    }

    public Integer updateChannelState(Long channelId, Integer state) {
        int show = 1;
        if (state == 3) {
            show = 0;
        }
        return DbEngine.getInstance()
                .doWorkDBExecuteUpdate(UPDATE_CHANNEL_STATE, new Object[]{state, show, channelId});
    }

    /***************************************************************************************/

    private static final String LIST_DISH_MENU = "select * from test_ducheng_menu where show_flag = 1 order by gmt_create";

    public List<DuchengMenuTest> listDishMenu() {
        return DbEngine.getInstance()
                .doWorkDBExecuteQuery(LIST_DISH_MENU, DuchengMenuTest.BUILDER);
    }

}
