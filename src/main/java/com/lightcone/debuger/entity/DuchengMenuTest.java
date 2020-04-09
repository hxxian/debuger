package com.lightcone.debuger.entity;

import com.lightcone.debuger.common.ResultSetCustom;
import lombok.Data;
import org.gzy.db.ObjectBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: huang xiao xian
 * @Date: 2020/4/1
 * @Des:
 */
@Data
public class DuchengMenuTest {

    public static ObjectBuilder<DuchengMenuTest> BUILDER = new ObjectBuilder<DuchengMenuTest>() {
        @Override
        public DuchengMenuTest build(ResultSet rs) throws SQLException {
            ResultSetCustom rsc = new ResultSetCustom(rs);
            DuchengMenuTest menuTest = new DuchengMenuTest();
            if (rsc.isExistColumn("mid")) {
                menuTest.setMid(rs.getLong("mid"));
            }
            if (rsc.isExistColumn("type_name")) {
                menuTest.setTypeName(rs.getString("type_name"));
            }
            if (rsc.isExistColumn("dish_name")) {
                menuTest.setDishName(rs.getString("dish_name"));
            }
            if (rsc.isExistColumn("dish_price")) {
                menuTest.setDishPrice(rs.getInt("dish_price"));
            }
            return menuTest;
        }
    };


    private Long mid;
    private String typeName;
    private String dishName;
    private Integer dishPrice;
    private Boolean state = false;

}
