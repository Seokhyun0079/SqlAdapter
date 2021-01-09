package main;

public class SqlItem{
    private String parameterCode;
    private Object value;
    public SqlItem(String parameterCode, Object value){
        this.parameterCode = parameterCode;
        this.value = value;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}