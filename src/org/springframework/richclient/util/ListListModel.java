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
package org.springframework.richclient.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractListModel;

/**
 * @author Keith Donald
 */
public class ListListModel extends AbstractListModel implements List {
    private List items;

    private Comparator sorter;

    public ListListModel() {
        this(new ArrayList());
    }

    public ListListModel(List items) {
        this.items = new ArrayList(items);
    }

    public ListListModel(List items, Comparator sorter) {
        this.items = new ArrayList(items);
        setComparator(sorter);
        sort();
    }

    public void setComparator(Comparator sorter) {
        this.sorter = sorter;
    }

    public void sort() {
        if (sorter != null) {
            Collections.sort(items, sorter);
            fireContentsChanged(items, -1, -1);
        }
    }

    protected List getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }
    
    public Object getElementAt(int index) {
        return items.get(index);
    }

    public void add(int index, Object o) {
        items.add(index, o);
        fireIntervalAdded(this, index, index);
    }

    public boolean add(Object o) {
        boolean result = items.add(o);
        if (result) {
            int end = items.size() - 1;
            fireIntervalAdded(this, end, end);
        }
        return result;

    }

    public boolean addAll(Collection c) {
        int firstIndex = items.size() > 0 ? items.size() - 1 : 0;
        boolean result = items.addAll(c);
        if (result) {
            int lastIndex = items.size() - 1;
            fireIntervalAdded(this, firstIndex, lastIndex);
        }
        return result;
    }

    public boolean addAll(int index, Collection c) {
        boolean result = items.addAll(index, c);
        if (result) {
            fireIntervalAdded(this, index, index + c.size() - 1);
        }
        return result;
    }

    public void clear() {
        if (items.size() > 0) {
            int firstIndex = 0;
            int lastIndex = items.size() - 1;
            items.clear();
            fireIntervalRemoved(this, firstIndex, lastIndex);
        }
    }

    public boolean contains(Object o) {
        return items.contains(o);
    }

    public boolean containsAll(Collection c) {
        return items.containsAll(c);
    }

    public Object get(int index) {
        return items.get(index);
    }

    public int indexOf(Object o) {
        return items.indexOf(o);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Iterator iterator() {
        return items.iterator();
    }

    public int lastIndexOf(Object o) {
        return items.lastIndexOf(o);
    }

    public ListIterator listIterator() {
        return items.listIterator();
    }

    public ListIterator listIterator(int index) {
        return items.listIterator(index);
    }

    public Object remove(int index) {
        Object o = items.remove(index);
        fireIntervalRemoved(this, index, index);
        return o;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    public boolean removeAll(Collection c) {
        boolean b = items.removeAll(c);
        if (b) {
            fireContentsChanged(this, -1, -1);
        }
        return b;
    }

    public boolean retainAll(Collection c) {
        return items.retainAll(c);
    }

    public Object set(int index, Object element) {
        return items.set(index, element);
    }

    public int size() {
        return items.size();
    }

    public List subList(int fromIndex, int toIndex) {
        return items.subList(fromIndex, toIndex);
    }

    public Object[] toArray() {
        return items.toArray();
    }

    public Object[] toArray(Object[] a) {
        return items.toArray(a);
    }

}