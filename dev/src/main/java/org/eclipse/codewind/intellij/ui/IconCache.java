/*******************************************************************************
 * Copyright (c) 2019, 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.codewind.intellij.ui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.ResourceUtil;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IconCache {

    public static final String ICONS_THEMED_LOCAL_PROJECTS_SVG = "/icons/themed/local_projects.svg";
    public static final String ICONS_THEMELESS_CODEWIND_SVG = "/icons/themeless/codewind.svg";
    public static final String ICONS_THEMELESS_CODEWIND_GREY_SVG = "/icons/themeless/codewind-grey.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_GENERIC_SVG = "/icons/themeless/project-types/generic.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_GO_SVG = "/icons/themeless/project-types/go.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_JAVA_SVG = "/icons/themeless/project-types/java.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_MICROPROFILE_SVG = "/icons/themeless/project-types/microprofile.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_NODEJS_SVG = "/icons/themeless/project-types/nodejs.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_NODEJS_SVG1 = "/icons/themeless/project-types/nodejs.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_PYTHON_SVG = "/icons/themeless/project-types/python.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_SPRING_SVG = "/icons/themeless/project-types/spring.svg";
    public static final String ICONS_THEMELESS_PROJECT_TYPES_SWIFT_SVG = "/icons/themeless/project-types/swift.svg";
    public static final String ICONS_CODEWIND_13PX_SVG = "/META-INF/pluginIcon13x13.svg";

    private static final Map<String, Icon> iconCache = new HashMap<>();

    public static Icon getCachedIcon(String name) {
        synchronized (iconCache) {
            Icon icon = iconCache.get(name);
            if (icon == null) {
                icon = IconLoader.getIcon(name);
                iconCache.put(name, icon);
            }
            return icon;
        }
    }

    public static URL getUrl(String path) {
        int pos = path.lastIndexOf('/');
        String basePath = path.substring(0, pos);
        String fileName = path.substring(pos + 1);
        return ResourceUtil.getResource(IconCache.class, basePath, fileName);
    }
}
