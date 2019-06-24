package cn.focus.kill8080;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class SettingsAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        new SettingsDialog(anActionEvent.getProject()).show();
    }
}
