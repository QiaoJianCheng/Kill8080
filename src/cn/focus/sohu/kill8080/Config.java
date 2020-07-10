package cn.focus.sohu.kill8080;

import com.intellij.ide.util.*;
import com.intellij.openapi.project.*;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
class Config {
    private final String KEY_PORT;
    private final String KEY_SHOW;
    private final PropertiesComponent propertiesComponent;

    Config(Project project) {
        propertiesComponent = PropertiesComponent.getInstance();
        KEY_PORT = "cn.focus.kill8080_KEY_PORT" + project.getName();
        KEY_SHOW = "cn.focus.kill8080_KEY_SHOW" + project.getName();
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
