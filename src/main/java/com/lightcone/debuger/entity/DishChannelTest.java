package com.lightcone.debuger.entity;

import com.lightcone.debuger.common.ResultSetCustom;
import lombok.Data;
import org.gzy.db.ObjectBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: huang xiao xian
 * @Date: 2020/3/4
 * @Des:
 */
@Data
public class DishChannelTest {

    public static ObjectBuilder<DishChannelTest> BUILDER = new ObjectBuilder<DishChannelTest>() {
        @Override
        public DishChannelTest build(ResultSet rs) throws SQLException {
            ResultSetCustom rsc = new ResultSetCustom(rs);
            DishChannelTest dishChannelTest = new DishChannelTest();
            if (rsc.isExistColumn("channel_id")) {
                dishChannelTest.setChannelId(rs.getLong("channel_id"));
            }
            if (rsc.isExistColumn("title")) {
                dishChannelTest.setTitle(rs.getString("title"));
            }
            if (rsc.isExistColumn("state")) {
                dishChannelTest.setState(rs.getInt("state"));
            }
            return dishChannelTest;
        }
    };

    private Long channelId;
    private String title;
    private Integer state;

}
