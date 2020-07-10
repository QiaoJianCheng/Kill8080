package cn.focus.sohu.kill8080;

import com.intellij.notification.*;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.wm.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

/**
 * Created by QiaoJianCheng on 2020/06/30.
 */
@Service
public class KillService {

    private final Project project;
    private final KillWidget killWidget;
    private final Timer timer;
    private TimerTask task;

    public KillService(@NotNull Project project) {
        this.project = project;
        killWidget = new KillWidget(project);
        timer = new Timer();
    }

    public static void notify(Project project, String msg) {
        Notifications.Bus.notify(new Notification("Kill8080",
                "Kill8080", msg, NotificationType.INFORMATION), project);
    }

    public void show() {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
        if (killWidget.getConfig().showStatus() && statusBar.getWidget(killWidget.ID()) == null) {
            killWidget.addTo(statusBar);
        }
        if (task != null) task.cancel();
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec("lsof -i tcp:" + killWidget.getConfig().getPort() + " -a -c java -t");
                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    HashSet<String> pids = new HashSet<>();
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.matches("\\d*")) {
                            pids.add(line);
                        }
                    }
                    killWidget.update(pids.isEmpty() ? null : pids.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    public void hide() {
        if (task != null) task.cancel();
        killWidget.dispose();
    }
}
