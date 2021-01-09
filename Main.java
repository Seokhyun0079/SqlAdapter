package main;

import java.sql.*;

public class Main {
    public static void main(String[] args)  {
        Connection connection = connectionDB();
        SqlAdapter sqlAdapter = new SqlAdapter();
        String where =        "        WHERE\n" +
                "            article_no = "+sqlAdapter.getParameterCode("5")+"";
        String sql ="        SELECT\n" +
                "            article_no,\n" +
                "            "+sqlAdapter.getParameterCode("A")+" AS a,\n" +
                "            "+sqlAdapter.getParameterCode("B")+" AS b,\n" +
                "            "+sqlAdapter.getParameterCode("C")+" AS c\n" +
                "        FROM\n" +
                "            freeboard_article\n"+where;
        String where2 = "WHERE\n" +
                "    comment_id = "+sqlAdapter.getParameterCode("1");
        String sql2 = "SELECT\n" +
                "    comment_id,\n" +
                "    article_no,\n" +
                "    writer_id,\n" +
                "    regdate,\n" +
                "    moddate,\n" +
                "    comment_content,\n" +
                "    a,\n" +
                "    b,\n" +
                "    c\n" +
                "FROM\n" +
                "    ("+sql+")\n" +
                "    JOIN freeboard_article_comment USING ( article_no )\n" +where2 ;
                ;
        sqlAdapter.setQuery(sql2);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlAdapter.preparedStatement(connection);
            ResultSet rs= preparedStatement.executeQuery();
            System.out.println(sqlAdapter.getQuery());
            int cnt = 1;
            while (rs.next()){
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
                System.out.print(rs.getObject(cnt++)+"  ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static Connection connectionDB() {
        Connection con = null;

        String user = "supersalmon";
        String password = "supersalmon";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (SQLException e) {
            e.printStackTrace(); //database connection failed
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
