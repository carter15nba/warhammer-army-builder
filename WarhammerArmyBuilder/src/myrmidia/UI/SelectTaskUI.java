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

/*
 * SelectTaskUI.java
 *
 * Created on 21.apr.2011, 12:35:48
 */

package myrmidia.UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import myrmidia.UI.Resources.InputParameters;

/**
 *
 * @author Glenn Rune Strandbråten
 */
public class SelectTaskUI extends javax.swing.JFrame {

    /** Creates new form SelectTaskUI */
    public SelectTaskUI() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public SelectTaskUI(JFrame child){
        initComponents();
        setLocationRelativeTo(child);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createButton = new javax.swing.JButton();
        assignButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Myrmidia - Select task");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        createButton.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        createButton.setMnemonic('C');
        createButton.setText("Create army roster");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        assignButton.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        assignButton.setMnemonic('a');
        assignButton.setText("Assign outcome to fought battles");
        assignButton.setMaximumSize(new java.awt.Dimension(125, 23));
        assignButton.setMinimumSize(new java.awt.Dimension(125, 23));
        assignButton.setPreferredSize(new java.awt.Dimension(125, 23));
        assignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        exitButton.setMnemonic('x');
        exitButton.setText("Exit");
        exitButton.setMaximumSize(new java.awt.Dimension(125, 23));
        exitButton.setMinimumSize(new java.awt.Dimension(125, 23));
        exitButton.setPreferredSize(new java.awt.Dimension(125, 23));
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
            .addComponent(assignButton, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
            .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assignButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void assignButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignButtonActionPerformed
        new RetainUI(this).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_assignButtonActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        new QueryUI(this).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_createButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        InputParameters.getInstance().parseInput(args);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectTaskUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton assignButton;
    private javax.swing.JButton createButton;
    private javax.swing.JButton exitButton;
    // End of variables declaration//GEN-END:variables

}