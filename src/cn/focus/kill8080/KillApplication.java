package cn.focus.kill8080;

import com.intellij.openapi.components.ApplicationComponent;

import java.util.Timer;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class KillApplication implements ApplicationComponent {

    public static final int PERIOD = 5000;
    private Timer timer;

    @Override
    public void initComponent() {
        String port = new Config().getPort();
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    Process process = Runtime.getRuntime().exec("lsof -i tcp:" + port + " -t");
//                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                    String pid = in.readLine();
//                    String x = "Running process: port=" + port + " pid=" + pid;
//                    System.out.println(x);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, PERIOD);
    }

    @Override
    public void disposeComponent() {
        if (timer != null)
            timer.cancel();
    }
}
