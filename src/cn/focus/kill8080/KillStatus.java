package cn.focus.kill8080;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.MouseEvent;

/**
 * Created by QiaoJianCheng on 2019-06-24.
 */
public class KillStatus implements StatusBarWidget, StatusBarWidget.TextPresentation {
    private final IdeFrame ideFrame;
    private final Config config;
    private String text;

    public KillStatus(IdeFrame ideFrame) {
        this.ideFrame = ideFrame;
        config = new Config();
    }

    @NotNull
    @Override
    public String ID() {
        return KillStatus.class.getName();
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation(@NotNull PlatformType platformType) {
        return this;
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
    }

    @Override
    public void dispose() {
    }

    @NotNull
    @Override
    public String getText() {
        return "P:" + config.getPort() + "|" + (text == null ? "NULL" : text);
    }

    @Override
    public float getAlignment() {
        return 0;
    }

    @Nullable
    @Override
    public String getTooltipText() {
        return null;
    }

    @NotNull
    @Override
    public String getMaxPossibleText() {
        return "";
    }

    @Nullable
    @Override
    public Consumer<MouseEvent> getClickConsumer() {
        return mouseEvent -> notify(ideFrame.getProject(), text == null ?
                ("Nothing runs on " + config.getPort() + " port.") :
                "Process running: port=" + config.getPort() + ", pid=" + text + ".");
    }

    public IdeFrame getIdeFrame() {
        return ideFrame;
    }

    public void add() {
        if (ideFrame.getStatusBar().getWidget(ID()) == null)
            ideFrame.getStatusBar().addWidget(this);
    }

    public void remove() {
        if (ideFrame.getStatusBar().getWidget(ID()) != null)
            ideFrame.getStatusBar().removeWidget(ID());
    }

    public void update(String text) {
        this.text = text;
        ideFrame.getStatusBar().updateWidget(ID());
    }


    private void notify(Project project, String msg) {
        Notifications.Bus.notify(new Notification("kill8080",
                "kill8080",
                msg,
                NotificationType.INFORMATION), project
        );
    }
}
