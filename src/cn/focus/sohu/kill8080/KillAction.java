package cn.focus.sohu.kill8080;

import com.intellij.openapi.actionSystem.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class KillAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        String port = new Config(anActionEvent.getProject()).getPort();
        try {
            Process process = Runtime.getRuntime().exec("lsof -i tcp:" + port + " -a -c java -t");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            HashSet<String> pids = new HashSet<>();
            String line;
            while ((line = in.readLine()) != null) {
                if (line.matches("\\d*")) {
                    pids.add(line);
                }
            }
            String err = error.readLine();
            if (pids.size() == 0) {
                if (err == null || err.length() == 0) {
                    KillService.notify(anActionEvent.getProject(), "Nothing runs on " + port + " port.");
                } else {
                    KillService.notify(anActionEvent.getProject(), err);
                }
            } else {
                for (String pid : pids) {
                    Process process1 = Runtime.getRuntime().exec("kill " + pid);
                    error = new BufferedReader(new InputStreamReader(process1.getErrorStream()));
                    err = error.readLine();
                    if (err == null || err.length() == 0) {
                        KillService.notify(anActionEvent.getProject(), "Process killed: port=" + port + ", pid=" + pid + ".");
                    } else {
                        KillService.notify(anActionEvent.getProject(), err);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
