package com.lightcone.debuger.entity;

import com.lightcone.debuger.common.ResultSetCustom;
import lombok.Data;
import org.gzy.db.ObjectBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: huang xiao xian
 * @Date: 2020/3/3
 * @Des:
 */
@Data
public class DuchengTest {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

    public static ObjectBuilder<DuchengTest> BUILDER = new ObjectBuilder<DuchengTest>() {
        @Override
        public DuchengTest build(ResultSet rs) throws SQLException {
            ResultSetCustom rsc = new ResultSetCustom(rs);
            DuchengTest duchengTest = new DuchengTest();
            if (rsc.isExistColumn("id")) {
                duchengTest.setId(rs.getLong("id"));
            }
            if (rsc.isExistColumn("username")) {
                duchengTest.setUsername(rs.getString("username"));
            }
            if (rsc.isExistColumn("dish_name")) {
                duchengTest.setDishname(rs.getString("dish_name"));
            }
            if (rsc.isExistColumn("price")) {
                duchengTest.setPrice(rs.getInt("price"));
            }
            if (rsc.isExistColumn("gmt_update")) {
                Date date = rs.getTimestamp("gmt_update");
                duchengTest.setGmtCreate(dateFormat.format(date));
            }
            if (rsc.isExistColumn("total_count")) {
                duchengTest.setEachDishTotalCount(rs.getInt("total_count"));
            }
            if (rsc.isExistColumn("total_price")) {
                duchengTest.setEachDishTotalPrice(rs.getInt("total_price"));
            }
            return duchengTest;
        }
    };
    
    private Long id;
    private String username;
    private String dishname;
    private Integer price;
    private String gmtCreate;

    // 扩展字段
    private Integer eachDishTotalCount;
    private Integer eachDishTotalPrice;
    private Long channelId;

}
