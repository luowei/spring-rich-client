/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.richclient.command.config;

import java.awt.Image;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.springframework.binding.value.support.AbstractPropertyChangePublisher;
import org.springframework.core.style.ToStringCreator;
import org.springframework.richclient.command.AbstractCommand;
import org.springframework.richclient.core.DescribedElement;
import org.springframework.richclient.core.DescriptionConfigurable;
import org.springframework.richclient.core.VisualizedElement;
import org.springframework.richclient.factory.LabelInfoFactory;
import org.springframework.util.Assert;

/**
 * @author Keith Donald
 */
public class CommandFaceDescriptor extends AbstractPropertyChangePublisher implements DescribedElement,
        VisualizedElement, CommandLabelConfigurable, DescriptionConfigurable, CommandIconConfigurable {

    public static final String BUTTON_LABEL_INFO_PROPERTY = "buttonLabelInfo";

    public static final String BUTTON_ICON_INFO_PROPERTY = "iconInfo";

    public static final String BUTTON_LARGE_ICON_INFO_PROPETY = "largeIconInfo";

    public static final CommandFaceDescriptor BLANK_FACE_DESCRIPTOR = new CommandFaceDescriptor();

    private String caption;

    private String description;

    private CommandButtonLabelInfo labelInfo;

    private CommandButtonIconInfo iconInfo = CommandButtonIconInfo.BLANK_ICON_INFO;

    private CommandButtonIconInfo largeIconInfo = CommandButtonIconInfo.BLANK_ICON_INFO;

    public CommandFaceDescriptor(String encodedLabel) {
        this(encodedLabel, null, null);
    }

    public CommandFaceDescriptor(String encodedLabel, Icon icon, String caption) {
        this.labelInfo = LabelInfoFactory.createButtonLabelInfo(encodedLabel);
        if (icon != null) {
            this.iconInfo = new CommandButtonIconInfo(icon);
        }
        this.caption = caption;
    }

    public CommandFaceDescriptor() {
        this(LabelInfoFactory.BLANK_BUTTON_LABEL);
    }

    public CommandFaceDescriptor(CommandButtonLabelInfo labelInfo) {
        Assert.notNull(labelInfo, "The labelInfo property is required");
        this.labelInfo = labelInfo;
    }

    public boolean isBlank() {
        return labelInfo == LabelInfoFactory.BLANK_BUTTON_LABEL;
    }

    public String getText() {
        return labelInfo.getText();
    }

    public String getDisplayName() {
        return getText();
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public int getMnemonic() {
        return labelInfo.getMnemonic();
    }

    public int getMnemonicIndex() {
        return labelInfo.getMnemonicIndex();
    }

    public Image getImage() {
        return iconInfo.getImage();
    }

    public Icon getIcon() {
        return iconInfo.getIcon();
    }

    public Icon getLargeIcon() {
        return largeIconInfo.getIcon();
    }

    public KeyStroke getAccelerator() {
        return labelInfo.getAccelerator();
    }

    protected CommandButtonLabelInfo getButtonLabelInfo() {
        return labelInfo;
    }

    protected CommandButtonIconInfo getButtonIconInfo() {
        return iconInfo;
    }

    public void setCaption(String shortDescription) {
        String old = this.caption;
        this.caption = shortDescription;
        firePropertyChange(DescribedElement.CAPTION_PROPERTY, old, this.caption);
    }

    public void setDescription(String longDescription) {
        String old = this.description;
        this.description = longDescription;
        firePropertyChange(DescribedElement.DESCRIPTION_PROPERTY, old, this.description);
    }

    public void setButtonLabelInfo(String encodedLabelInfo) {
        setLabelInfo(LabelInfoFactory.createButtonLabelInfo(encodedLabelInfo));
    }

    public void setLabelInfo(CommandButtonLabelInfo labelInfo) {
        if (labelInfo == null) {
            labelInfo = LabelInfoFactory.BLANK_BUTTON_LABEL;
        }
        CommandButtonLabelInfo old = this.labelInfo;
        this.labelInfo = labelInfo;
        firePropertyChange(BUTTON_LABEL_INFO_PROPERTY, old, this.labelInfo);
    }

    public void setIconInfo(CommandButtonIconInfo iconInfo) {
        if (iconInfo == null) {
            iconInfo = CommandButtonIconInfo.BLANK_ICON_INFO;
        }
        CommandButtonIconInfo old = this.iconInfo;
        this.iconInfo = iconInfo;
        firePropertyChange(BUTTON_ICON_INFO_PROPERTY, old, this.iconInfo);
    }

    public void setLargeIconInfo(CommandButtonIconInfo largeIconInfo) {
        if (largeIconInfo == null) {
            iconInfo = CommandButtonIconInfo.BLANK_ICON_INFO;
        }
        CommandButtonIconInfo old = this.largeIconInfo;
        this.largeIconInfo = largeIconInfo;
        firePropertyChange(BUTTON_ICON_INFO_PROPERTY, old, this.iconInfo);
    }

    public void setIcon(Icon icon) {
        if (iconInfo == CommandButtonIconInfo.BLANK_ICON_INFO) {
            if (icon != null) {
                setIconInfo(new CommandButtonIconInfo(icon));
            }
        }
        else {
            Icon old = iconInfo.getIcon();
            this.iconInfo.setIcon(icon);
            firePropertyChange(BUTTON_ICON_INFO_PROPERTY, old, icon);
        }
    }

    public void setLargeIcon(Icon icon) {
        if (largeIconInfo == CommandButtonIconInfo.BLANK_ICON_INFO) {
            if (icon != null) {
                setLargeIconInfo(new CommandButtonIconInfo(icon));
            }
        }
        else {
            Icon old = largeIconInfo.getIcon();
            this.largeIconInfo.setIcon(icon);
            firePropertyChange(BUTTON_ICON_INFO_PROPERTY, old, icon);
        }
    }

    public void configureLabel(AbstractButton button) {
        labelInfo.configure(button);
    }

    public void configureIcon(AbstractButton button) {
        configureIconInfo(button, false);
    }

    public void configureIconInfo(AbstractButton button, boolean useLargeIcons) {
        if (useLargeIcons) {
            largeIconInfo.configure(button);
        }
        else {
            iconInfo.configure(button);
        }
    }

    public void configure(AbstractButton button, AbstractCommand command, CommandButtonConfigurer strategy) {
        Assert.notNull(strategy, "The button configurer strategy is required");
        strategy.configure(button, command, this);
    }

    public void configure(Action action) {
        Assert.notNull(action, "The swing action to configure is required");
        action.putValue(AbstractAction.NAME, getText());
        action.putValue(AbstractAction.MNEMONIC_KEY, new Integer(getMnemonic()));
        action.putValue(AbstractAction.SMALL_ICON, getIcon());
        action.putValue("LargeIcon", getLargeIcon());
        action.putValue(AbstractAction.ACCELERATOR_KEY, getAccelerator());
        action.putValue(AbstractAction.SHORT_DESCRIPTION, caption);
        action.putValue(AbstractAction.LONG_DESCRIPTION, description);
    }

    public String toString() {
        return new ToStringCreator(this).append("caption", caption).append("description", description).append(
                "buttonLabelInfo", labelInfo).append("buttonIconInfo", iconInfo).toString();
    }

}