package cn.focus.kill8080;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by QiaoJianCheng on 2019-06-21.
 */
public class SettingsDialog extends DialogWrapper {

    private final Config config;
    private JTextField textField;

    SettingsDialog(@Nullable Project project) {
        super(project);
        setTitle("Kill8080 Settings");
        config = new Config();

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
        return box;
    }

    @Override
    protected void doOKAction() {
        if (isOK()) {
            super.doOKAction();
            config.setPort(textField.getText());
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
