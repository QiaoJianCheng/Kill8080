package cn.focus.sohu.kill8080;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class KillAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        String port = new Config().getPort();
        try {
            Process process = Runtime.getRuntime().exec("lsof -i tcp:" + port + " -t");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String pid = in.readLine();
            String err = error.readLine();
            if (pid == null || pid.trim().length() == 0) {
                if (err == null || err.length() == 0) {
                    notify(anActionEvent, "Nothing runs on " + port + " port.");
                } else {
                    notify(anActionEvent, err);
                }
            } else {
                Process process1 = Runtime.getRuntime().exec("kill " + pid);
                error = new BufferedReader(new InputStreamReader(process1.getErrorStream()));
                err = error.readLine();
                if (err == null || err.length() == 0) {
                    notify(anActionEvent, "Process killed: port=" + port + ", pid=" + pid + ".");
                } else {
                    notify(anActionEvent, err);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notify(@NotNull AnActionEvent anActionEvent, String msg) {
        Notifications.Bus.notify(new Notification("kill8080",
                        "Kill8080",
                        msg,
                        NotificationType.INFORMATION),
                getEventProject(anActionEvent));
    }
}
