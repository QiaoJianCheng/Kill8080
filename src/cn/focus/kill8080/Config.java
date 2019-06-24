package cn.focus.kill8080;

import com.intellij.ide.util.PropertiesComponent;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
class Config {
    private static final String KEY_PORT = "cn.focus.kill8080_KEY_PORT";
    private final PropertiesComponent propertiesComponent;

    Config() {
        propertiesComponent = PropertiesComponent.getInstance();
    }

    public String getPort() {
        return propertiesComponent.getValue(KEY_PORT, "8080");
    }

    public void setPort(String port) {
        propertiesComponent.setValue(KEY_PORT, port);
    }
}
