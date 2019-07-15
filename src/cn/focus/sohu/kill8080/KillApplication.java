package cn.focus.sohu.kill8080;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.WindowManagerListener;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class KillApplication implements BaseComponent {

    public static final int PERIOD = 1000;
    private Timer timer;
    private Config config;
    private List<KillStatus> statusList;
    private WindowManagerListener windowManagerListener = new WindowManagerListener() {
        @Override
        public void frameCreated(@NotNull IdeFrame ideFrame) {
            KillStatus killStatus = new KillStatus(ideFrame);
            statusList.add(killStatus);
            if (config.showStatus()) {
                killStatus.add();
            }
        }

        @Override
        public void beforeFrameReleased(@NotNull IdeFrame ideFrame) {
            Iterator<KillStatus> iterator = statusList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getIdeFrame() == ideFrame) {
                    iterator.remove();
                    break;
                }
            }
        }
    };
    private TimerTask timerTask;

    @Override
    public void initComponent() {
        statusList = new ArrayList<>();
        config = new Config();
        timer = new Timer();
        WindowManager.getInstance().addListener(windowManagerListener);
        updateConfig();
    }

    public void updateConfig() {
        if (config.showStatus()) {
            if (timerTask == null) {
                for (KillStatus killStatus : statusList) {
                    killStatus.add();
                }
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Process process = Runtime.getRuntime().exec("lsof -i tcp:" + config.getPort());
                            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            HashSet<String> pids = new HashSet<>();
                            String line;
                            while ((line = in.readLine()) != null) {
                                if (line.startsWith("java")) {
                                    pids.add(line.substring(line.indexOf("java") + 4, line.indexOf("sohu")).trim());
                                }
                            }
                            if (pids.size() == 0) pids.add("NULL");
                            updateStatusBar(pids.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                timer.schedule(timerTask, 0, PERIOD);
            }
        } else {
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            for (KillStatus killStatus : statusList) {
                killStatus.remove();
            }
        }
    }

    private void updateStatusBar(String text) {
        for (KillStatus killStatus : statusList) {
            killStatus.update(text);
        }
    }

    @Override
    public void disposeComponent() {
        if (timer != null)
            timer.cancel();
        WindowManager.getInstance().removeListener(windowManagerListener);
    }
}
