package com.savoptik;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AccessibleJListItemTest extends JList {

    private static void showTest() {
        String[] NAMES = new String[] {"One", "Two", "Three", "Four", "Five"};
        JFrame frame = new JFrame("JList demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AccessibleJListItemTest list = new AccessibleJListItemTest();
        list.setModel(new ListModel() {
            private final String[] names = NAMES;

            @Override
            public int getSize() {
                return names.length;
            }

            @Override
            public Object getElementAt(int index) {
                return (index >= 0) && (index < names.length) ? names[index] : null;
            }

            @Override
            public void addListDataListener(ListDataListener l) {

            }

            @Override
            public void removeListDataListener(ListDataListener l) {

            }
        });
        frame.setLayout(new FlowLayout());
        frame.add(list);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(AccessibleJListItemTest::showTest);
    }

    @Override
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleListTest();
        }
        return accessibleContext;
    }


    private class AccessibleListTest extends AccessibleJList {

        @Override
        public Accessible getAccessibleChild(int i) {
            if (i >= getModel().getSize()) {
                return null;
            } else {
                return new AccessibleItemTest(AccessibleJListItemTest.this, i);
            }
        }

        private class AccessibleItemTest extends AccessibleJListChild {

            AccessibleItemTest(JList parent, int indexInParent) {
                super(parent, indexInParent);
            }

            @Override
            public String getAccessibleName() {
                return "This item " + super.getAccessibleName();
            }
        }
    }
}
