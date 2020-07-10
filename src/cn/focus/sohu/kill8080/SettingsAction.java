package cn.focus.sohu.kill8080;

import com.intellij.openapi.actionSystem.*;
import org.jetbrains.annotations.*;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class SettingsAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        new SettingsDialog(anActionEvent.getProject()).show();
    }
}
