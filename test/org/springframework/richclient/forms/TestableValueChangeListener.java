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
package org.springframework.richclient.forms;

import org.springframework.binding.value.ValueChangeListener;

/**
 * A value change listener that counts the number of events received.
 * 
 * @author  oliverh
 */
public class TestableValueChangeListener implements ValueChangeListener {

    private int eventCount = 0;

    /**
     * Resets the event count back to zero.
     */
    public void reset() {
        eventCount = 0;
    }

    /**
     * Gets the number of events this listener has reveived.
     */
    public int getEventCount() {
        return eventCount;
    }

    public void valueChanged() {
        eventCount++;
    }
}