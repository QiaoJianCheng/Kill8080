package cn.focus.sohu.kill8080;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.*;
import org.jetbrains.annotations.*;

/**
 * Created by QiaoJianCheng on 2020/06/30.
 */
public class ProjectListener implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        KillService service = ServiceManager.getService(project, KillService.class);
        service.show();
    }

    @Override
    public void projectClosed(@NotNull Project project) {
        KillService service = ServiceManager.getService(project, KillService.class);
        service.hide();
    }
}
