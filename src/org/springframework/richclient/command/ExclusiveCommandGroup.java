/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.richclient.command;

import javax.swing.Icon;

import org.springframework.richclient.command.config.CommandFaceDescriptor;

public class ExclusiveCommandGroup extends CommandGroup {

    private ExclusiveCommandGroupController controller = new ExclusiveCommandGroupController();

    public ExclusiveCommandGroup() {
        super();
    }

    public ExclusiveCommandGroup(String groupId) {
        super(groupId);
    }

    public ExclusiveCommandGroup(String groupId, CommandFaceDescriptor face) {
        super(groupId, face);
    }

    public ExclusiveCommandGroup(String groupId, CommandRegistry commandRegistry) {
        super(groupId, commandRegistry);
    }

    public ExclusiveCommandGroup(String id, String encodedLabel) {
        super(id, encodedLabel);
    }

    public ExclusiveCommandGroup(String id, String encodedLabel, Icon icon,
            String caption) {
        super(id, encodedLabel, icon, caption);
    }

    protected ExclusiveCommandGroupController getController() {
        return controller;
    }

    public void setAllowsEmptySelection(boolean allowsEmptySelection) {
        controller.setAllowsEmptySelection(allowsEmptySelection);
    }

    public boolean getAllowsEmptySelection() {
        return controller.getAllowsEmptySelection();
    }

    public boolean isAllowedMember(AbstractCommand prospectiveMember) {
        return prospectiveMember instanceof ToggleCommand;
    }
}