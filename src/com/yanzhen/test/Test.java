package com.yanzhen.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yanzhen.util.C3P0Utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
//        JdbcTemplate jt = new JdbcTemplate();

//        Connection conn = C3P0Utils.getConnection();

        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        try {

            ds.setJdbcUrl("jdbc:mysql://localhost:3306/wineshop");
            ds.setUser("root");
            ds.setPassword("1234");
            Connection conn = ds.getConnection();
            String s = conn.getSchema();
//            PreparedStatement ps = conn.prepareStatement("select * from admin");
//            ResultSet res = ps.executeQuery();
            System.out.println(s);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
