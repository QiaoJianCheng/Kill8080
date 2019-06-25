package cn.focus.kill8080;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.CustomStatusBarWidget;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.StatusBar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by QiaoJianCheng on 2019-06-24.
 */
public class KillStatus implements CustomStatusBarWidget {
    private final IdeFrame ideFrame;
    private final Config config;
    private final JLabel label;
    private String text;

    public KillStatus(IdeFrame ideFrame) {
        this.ideFrame = ideFrame;
        config = new Config();
        label = new JLabel();
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KillStatus.this.notify(ideFrame.getProject(), text == null ?
                        ("Nothing runs on " + config.getPort() + " port.") :
                        "Process running: port=" + config.getPort() + ", pid=" + text + ".");
            }
        });
    }

    @NotNull
    @Override
    public String ID() {
        return KillStatus.class.getName();
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation(@NotNull PlatformType platformType) {
        return null;
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
    }

    @Override
    public void dispose() {
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
        label.setText("P:" + config.getPort() + "|" + (text == null ? "NULL" : text));
        label.setToolTipText(text == null ?
                ("Nothing runs on " + config.getPort() + " port.") :
                "Process running: port=" + config.getPort() + ", pid=" + text + ".");
        ideFrame.getStatusBar().updateWidget(ID());
    }


    private void notify(Project project, String msg) {
        Notifications.Bus.notify(new Notification("Kill8080",
                "Kill8080",
                msg,
                NotificationType.INFORMATION), project
        );
    }

    @Override
    public JComponent getComponent() {
        return label;
    }
}
