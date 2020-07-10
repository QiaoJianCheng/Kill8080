package cn.focus.sohu.kill8080;

import com.intellij.openapi.project.*;
import com.intellij.openapi.wm.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by QiaoJianCheng on 2019-06-24.
 */
public class KillWidget implements CustomStatusBarWidget {
    private final Config config;
    private final JLabel label;
    private final Project project;
    private String text;
    private StatusBar statusBar;

    public KillWidget(Project project) {
        this.project = project;
        config = new Config(project);
        label = new JLabel();
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KillService.notify(project, text == null ?
                        ("Nothing runs on " + config.getPort() + " port.") :
                        "Process running: port=" + config.getPort() + ", pid=" + text + ".");
            }
        });
    }

    @NotNull
    @Override
    public String ID() {
        return project.getName() + KillWidget.class.getName();
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
    }

    public void addTo(@NotNull StatusBar statusBar) {
        this.statusBar = statusBar;
        statusBar.addWidget(this, this);
    }

    @Override
    public void dispose() {
        if (statusBar != null) statusBar.removeWidget(ID());
    }

    public void update(String text) {
        if (statusBar != null) {
            this.text = text;
            label.setText("P:" + config.getPort() + "|" + (text == null ? "NULL" : text));
            label.setToolTipText(text == null ?
                    ("Nothing runs on " + config.getPort() + " port.") :
                    "Process running: port=" + config.getPort() + ", pid=" + text + ".");
            statusBar.updateWidget(ID());
        }
    }

    @Override
    public JComponent getComponent() {
        return label;
    }

    public Config getConfig() {
        return config;
    }
}
