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
 * ExplanationUI.java
 *
 * Created on 27.apr.2011, 09:48:55
 */

package myrmidia.UI;

import javax.swing.JFrame;
import myrmidia.Explanation.ExplanationEngine;

/**
 *
 * @author Glenn Rune Strandbråten
 */
public class ExplanationUI extends javax.swing.JDialog {

    public static final int MODE_TRANSPARENCY = 100;
    public static final int MODE_JUSTIFICATION = 200;
    /** Creates new form ExplanationUI */
    public ExplanationUI() {
        initComponents();
        getRootPane().setDefaultButton(closeButton);
    }

    public ExplanationUI(JFrame parent, int index,int mode) {
        super(parent, true);
        initComponents();
        getRootPane().setDefaultButton(closeButton);
        setLocationRelativeTo(parent);
        ExplanationEngine explEng = ExplanationEngine.getInstance();
        String expl;
        if(mode==MODE_JUSTIFICATION)
            expl = explEng.generateJustificationExplanation(index);
        else if(mode==MODE_TRANSPARENCY)
            expl = explEng.generateTransparencyExplanation(index);
        else
            expl = "No explanation were found!";
        explanationText.setText(expl);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        explanationScroll = new javax.swing.JScrollPane();
        explanationText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Myrmidia - Explanation");

        closeButton.setMnemonic('C');
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        explanationText.setColumns(20);
        explanationText.setEditable(false);
        explanationText.setFont(new java.awt.Font("Tahoma", 0, 14));
        explanationText.setRows(5);
        explanationScroll.setViewportView(explanationText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(explanationScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addComponent(closeButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(closeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(explanationScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExplanationUI(null,0,0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane explanationScroll;
    private javax.swing.JTextArea explanationText;
    // End of variables declaration//GEN-END:variables

}
