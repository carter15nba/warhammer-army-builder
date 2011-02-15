/*
 *  Copyright (C) 2011 Glenn Rune Strandbråten 
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.Warhammer.UI;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class SelectUtilUI extends javax.swing.JFrame{
    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel unitLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JComboBox unitBox;
    private CreateRaceXMLUI parent;
    private final String TITLE = "Add utility unit";

    /**
     * Constructor to create a new UI object.
     * @param units String[] with all the eligible units in the table.
     * @param parent CreateRaceXMLUI Reference to parent UI.
     */
    public SelectUtilUI(String[] units, CreateRaceXMLUI parent){
        //setUndecorated(true);
        initComponents(units);
        this.parent = parent;
        this.parent.setEnabled(false);
        setLocation(parent.getX()+180, parent.getY()+130);
        setVisible(true);
    }

    /**
     * Initializes the UI form and its components.
     * @param units  String[] with all the eligible units in the table, used to populate the units combo box.
     */
    private void initComponents(String[] units) {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        unitLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        unitBox = new javax.swing.JComboBox();

        setTitle(TITLE);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });


        okButton.setText("Ok");
        okButton.setMnemonic(java.awt.event.KeyEvent.VK_O);
        okButton.setEnabled(false);
        okButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setMnemonic(java.awt.event.KeyEvent.VK_C);
        cancelButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        unitLabel.setText("Select unit:");

        nameLabel.setText("Name: ");

        unitBox.setModel(new javax.swing.DefaultComboBoxModel(units));
        unitBox.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(unitLabel)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton))
                    .addComponent(nameField)
                    .addComponent(unitBox, 0, 166, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitLabel)
                    .addComponent(unitBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );
        pack();
    }

    /**
     * Method triggered by a click on the ok button in the UI.
     * @param evt The ActionEvent triggering the method.
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt){
        String parentName = unitBox.getSelectedItem().toString();
        String unitName = "-"+nameField.getText();
        setVisible(false);
        parent.setEnabled(true);
        parent.addUtilityUnit(parentName, unitName);
        dispose();
    }

    /**
     * Method triggered by a click on the cancel button in the UI.
     * @param evt The ActionEvent triggering the method.
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt){
        parent.setEnabled(true);
        dispose();
    }

    /**
     * Method triggered by selecting a item in the combo box in the UI.
     * @param evt The ActionEvent triggering the method.
     */
    private void unitBoxActionPerformed(java.awt.event.ActionEvent evt){
        int index = unitBox.getSelectedIndex();
        if(index==0)
            okButton.setEnabled(false);
        else
            okButton.setEnabled(true);
    }

    /**
     * Method triggered by a WindowEvent.
     * @param evt The WindowEvent triggering the method.
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt){
        parent.setEnabled(true);
        dispose();
    }
}
