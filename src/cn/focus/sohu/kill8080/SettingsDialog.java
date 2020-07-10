package cn.focus.sohu.kill8080;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.ui.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class SettingsDialog extends DialogWrapper {

    private final Config config;
    private final Project project;
    private JTextField textField;
    private JCheckBox checkBox;

    SettingsDialog(Project project) {
        super(project);
        this.project = project;
        setTitle("Kill8080 Settings");
        config = new Config(project);

        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        Box box = Box.createVerticalBox();
        box.setAlignmentY(Component.TOP_ALIGNMENT);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setAlignmentY(Component.TOP_ALIGNMENT);
        horizontalBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel("Port you want to listen: ");
        textField = new JTextField(config.getPort());
        horizontalBox.add(label);
        horizontalBox.add(textField);

        box.add(horizontalBox);

        checkBox = new JCheckBox("Show on status bar", config.showStatus());
        box.add(checkBox);

        return box;
    }

    @Override
    protected void doOKAction() {
        if (isOK()) {
            config.setPort(textField.getText());
            config.showStatus(checkBox.isSelected());
            if (checkBox.isSelected()) {
                ServiceManager.getService(project, KillService.class).show();
            } else {
                ServiceManager.getService(project, KillService.class).hide();
            }
            super.doOKAction();
        } else {
            setErrorText("Are you serious?");
        }
    }

    @Override
    public boolean isOK() {
        String text = textField.getText();
        return text.matches("\\d{2,5}") && Integer.parseInt(text) < 65536;
    }
}
