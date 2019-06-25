package cn.focus.sohu.kill8080;

import com.intellij.ide.util.PropertiesComponent;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
class Config {
    private static final String KEY_PORT = "cn.focus.kill8080_KEY_PORT";
    private static final String KEY_SHOW = "cn.focus.kill8080_KEY_SHOW";
    private final PropertiesComponent propertiesComponent;

    Config() {
        propertiesComponent = PropertiesComponent.getInstance();
    }

    public String getPort() {
        return propertiesComponent.getValue(KEY_PORT, "8080");
    }

    public boolean showStatus() {
        return propertiesComponent.getBoolean(KEY_SHOW, true);
    }

    public void setPort(String port) {
        propertiesComponent.setValue(KEY_PORT, port);
    }

    public void showStatus(boolean show) {
        propertiesComponent.setValue(KEY_SHOW, String.valueOf(show));
    }
}
