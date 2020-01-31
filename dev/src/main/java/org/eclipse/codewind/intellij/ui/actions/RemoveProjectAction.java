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

package org.eclipse.codewind.intellij.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.PairFunction;
import org.eclipse.codewind.intellij.core.CodewindApplication;
import org.eclipse.codewind.intellij.core.Logger;
import org.eclipse.codewind.intellij.ui.tasks.RemoveProjectTask;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreePath;

import static com.intellij.openapi.actionSystem.PlatformDataKeys.CONTEXT_COMPONENT;
import static org.eclipse.codewind.intellij.ui.messages.CodewindUIBundle.message;

public class RemoveProjectAction extends AnAction {

    public RemoveProjectAction() {
        super(message("UnbindActionLabel"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Object data = e.getData(CONTEXT_COMPONENT);
        if (!(data instanceof Tree)) {
            Logger.log("unrecognized component for RemoveProjectAction: " + data);
            System.out.println("*** unrecognized component for RemoveProjectAction: " + data);
            return;
        }

        Tree tree = (Tree) data;
        TreePath treePath = tree.getSelectionPath();
        if (treePath == null) {
            Logger.log("no selection for RemoveProjectAction: " + data);
            System.out.println("*** no selection for RemoveProjectAction: " + data);
            return;
        }

        Object node = treePath.getLastPathComponent();
        if (!(node instanceof CodewindApplication)) {
            Logger.log("selection for RemoveProjectAction is not a project: " + data);
            System.out.println("*** selection for RemoveProjectAction is not a project: " + data);
            return;
        }

        CodewindApplication application = (CodewindApplication) node;

        String title = message("UnbindActionTitle");
        String message = message("UnbindActionMessage", application.name);
        String checkboxText = "Close project and delete from filesystem?";

        final int CANCEL = 0;
        final int OK_AND_DELETE = 1;
        final int OK_NO_DELETE = 2;

        PairFunction<? super Integer, ? super JCheckBox, Integer> exitFn = (exitCode, cb) -> {
            System.out.println("*** exitCode: " + exitCode);
            if (exitCode == -1)
                // closed via close button in dialog title
                return CANCEL;
            if (exitCode == 1)
                // closed via cancel button
                return CANCEL;
            if (cb.isSelected())
                return OK_AND_DELETE;
            return OK_NO_DELETE;
        };

        int response = Messages.showCheckboxMessageDialog(message, title,
                new String[]{Messages.OK_BUTTON, Messages.CANCEL_BUTTON},
                checkboxText,
                false,
                1,
                1,
                Messages.getQuestionIcon(),
                exitFn);

        System.out.println("*** response: " + response);
        if (response == CANCEL)
            return;
        // ProgressManager.getInstance().run(new RemoveProjectTask(application))
    }
}
