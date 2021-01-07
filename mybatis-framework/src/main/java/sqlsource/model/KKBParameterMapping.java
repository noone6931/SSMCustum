package sqlsource.model;
/**
 * 封装#{}解析出来的参数信息
 */
public class KKBParameterMapping {
    // #{}中的参数名称
    private Class type;
    // #{}对于的参数的参数类型
    private String name;

    public KKBParameterMapping(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
