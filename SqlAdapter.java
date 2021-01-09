package main;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class SqlAdapter {
    private List<SqlItem> parameterList;
    private List<SqlItem> parameterSortList;
    private String query;
    private Integer parameterCnt;
    private String parameterPrefix;
    private String parameterCode;
    public SqlAdapter() {
        this.parameterList = new ArrayList<>();
        parameterSortList = new ArrayList<SqlItem>();
        this.parameterCnt = 1;
        this.parameterPrefix =":pramCode";
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        System.out.println(query);
        this.query = query;
    }

    private String getParameterCode() {
        return parameterCode+parameterCnt;
    }

    private void setParameterCode() {
        this.parameterCode = parameterPrefix+parameterCnt;
    }

    public String getParameterCode(Object obj) {
        setParameterCode();
        setParameter(this.parameterCode, obj);
        parameterCnt++;
        return parameterCode;
    }

    private void setParameter(String key, Object obj){
        parameterList.add(new SqlItem(key, obj));
    }

    public PreparedStatement preparedStatement(Connection connection) throws SQLException {
        transSqlForPreparedStatement();
        return setParams(connection);
    }

    private void transSqlForPreparedStatement(){
        List<SqlItem> copyList = new ArrayList<SqlItem>();
        for(int i = 0; i < parameterList.size(); i++){
            copyList.add(parameterList.get(i));
        }
        for(int firstIndex = query.indexOf(parameterPrefix); firstIndex>-1; firstIndex=query.indexOf(parameterPrefix)){
                for(int i = copyList.size()-1; i > -1; i--){
                SqlItem sqlItem = copyList.get(i);
                int itemIndex = query.indexOf(sqlItem.getParameterCode());
                if(itemIndex==firstIndex){
                    query = query.replace(sqlItem.getParameterCode(), "?");
                    parameterSortList.add(copyList.get(i));
                    copyList.remove(i);
                }
            }
        }
    }

    private PreparedStatement setParams(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);
        int cnt =1;
        for(SqlItem item: parameterSortList){
            setPreparedStatementParameterByInstanceOf(cnt++, item.getValue(), pstmt);
        }
        return pstmt;
    }
    private void setPreparedStatementParameterByInstanceOf(Integer index, Object o, PreparedStatement pstmt) throws SQLException{
        if(o instanceof String){
            pstmt.setString(index, (String)o);
        }else if(o instanceof Integer){
            pstmt.setInt(index, (Integer)o);
        }else if(o instanceof Short){
            pstmt.setShort(index, (Short)o);
        }else if(o instanceof BigDecimal){
            pstmt.setBigDecimal(index, (BigDecimal) o);
        }else{
            throw new RuntimeException("unexpected type parameter index = " +index +", value = "+o);
        }
    }
}
