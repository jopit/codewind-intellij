/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.codewind.intellij.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.ContentFactory;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.eclipse.codewind.intellij.core.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class CodewindWebView extends JBPanel<CodewindWebView> {

    public static class Factory implements ToolWindowFactory {

        @Override
        public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
            CodewindWebView window = new CodewindWebView(project);
            ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
            toolWindow.getContentManager().addContent(contentFactory.createContent(window, "", false));
        }
    }

    public class ClickHandler {
        public void onClick() {
            System.out.println("Link clicked, project = " + project.getName());
        }
    }

    private final Project project;

    public CodewindWebView(Project project) {
        this.project = project;

        this.setLayout(new BorderLayout());
        JFXPanel panel = new JFXPanel();
        this.add(panel, BorderLayout.CENTER);

        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            try {
                WebView webView = new WebView();
                WebEngine engine = webView.getEngine();

                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("handler", new ClickHandler());

                panel.setScene(new Scene(webView));
                engine.loadContent("<html><a href=\"\" onclick=\"handler.onClick()\">Click here to test</a></html>");

                // engine.load("https://www.ibm.com/");
            } catch (Exception e) {
                Logger.logWarning(e);
            }
        });
    }
}
