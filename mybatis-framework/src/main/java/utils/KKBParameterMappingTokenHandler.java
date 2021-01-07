package utils;

import sqlsource.model.KKBParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class KKBParameterMappingTokenHandler implements KKBTokenHandler {
    private List<KKBParameterMapping> parameterMappings = new ArrayList<>();

    // content是参数名称
    // content 就是#{}中的内容
    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private KKBParameterMapping buildParameterMapping(String content) {
        KKBParameterMapping parameterMapping = new KKBParameterMapping(content);
        return parameterMapping;
    }

    public List<KKBParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<KKBParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

}
