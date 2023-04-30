package com.yanzhen.util;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
/**
 * 通用的增删改查对象
 */
public class DbUtils {

    /**
     * 获取记录数
     */
    public int getCount(String sql) throws SQLException {
        Connection connection = C3P0Utils.getConnection();
        Statement st= connection.createStatement();
        ResultSet rs=st.executeQuery(sql);
        int nums=0;
        while(rs.next()){
            nums++;
        }
        return nums;
    }

    /**
     * 通用查询
     *
     * @param classes 类
     * @param sql     sql语句
     * @param args    查询参数
     * @param <T>     泛型
     * @return 泛型对象
     */
    public static <T> T query(Class<T> classes, String sql, Object... args) {
        T object = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            connection = C3P0Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
//            for (int i = 0; i < args.length; i++) {
//                preparedStatement.setObject(i + 1, args[i]);
//            }
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            Map<String, Object> map = new HashMap<>(16);
            if (resultSet.next()) {
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    String columnname = resultSetMetaData.getColumnLabel(i + 1);
                    Object obj = resultSet.getObject(i + 1);
                    map.put(columnname, obj);
                }
            }
            if (map.size() > 0) {
                object = classes.newInstance();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String fieldName = entry.getKey();
                    Object value = entry.getValue();
                    ReflectionUtils.setFieldValue(object, fieldName, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Utils.release(connection, preparedStatement, resultSet);

        }
        return object;
    }


    /**
     * 通用集合查询
     *
     * @param classes 类
     * @param sql     sql语句
     * @param args    查询参数
     * @param <T>     泛型
     * @return 泛型集合
     */
    public static <T> List<T> list(Class<T> classes, String sql, Object... args) {
        List<T> objects = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            connection = C3P0Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            if(args!=null){
                for (int i = 0; i < args.length; i++) {
                    if(args[i]!=null){
                        preparedStatement.setObject(i + 1, args[i]);
                    }

                }
            }
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            Map<String, Object> map = new HashMap<>(16);
            while (resultSet.next()) {
                T object = null;
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    String columnname = resultSetMetaData.getColumnLabel(i + 1);
                    Object obj = resultSet.getObject(i + 1);
                    map.put(columnname, obj);
                }
                if (map.size() > 0) {
                    object = classes.newInstance();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String fieldName = entry.getKey();
                        Object value = entry.getValue();
                        ReflectionUtils.setFieldValue(object, fieldName, value);
                    }
                }
                objects.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Utils.release(connection, preparedStatement, resultSet);

        }
        return objects;
    }

    /**
     * 通用插入
     *
     * @param sql  sql语句
     * @param args 查询参数
     * @return ture/fasle
     */
    public static boolean save(String sql, Object... args) {
        boolean state = true;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = C3P0Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.execute();
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        } finally {
            C3P0Utils.release(connection, preparedStatement, resultSet);
        }
        return state;
    }

    /**
     * 通用删除
     *
     * @param sql  sql语句
     * @param args 查询参数
     * @return ture/fasle
     */
    public static boolean remove(String sql, Object... args) {
        boolean state = true;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = C3P0Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
//            if(args!=null){
//                for (int i = 0; i < args.length; i++) {
//                    preparedStatement.setObject(i + 1, args[i]);
//                }
//            }
            preparedStatement.execute();
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        } finally {
            C3P0Utils.release(connection, preparedStatement, resultSet);
        }
        return state;
    }

    /**
     * 通用更新
     *
     * @param sql  sql语句
     * @param args 查询参数
     * @return ture/fasle
     */
    public static boolean update(String sql, Object... args) {
        boolean state = true;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = C3P0Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.execute();
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        } finally {
            C3P0Utils.release(connection, preparedStatement, resultSet);
        }
        return state;
    }
}
