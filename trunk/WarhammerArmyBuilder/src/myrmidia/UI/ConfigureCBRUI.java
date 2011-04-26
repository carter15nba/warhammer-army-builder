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
 * ConfigureCBRUI.java
 *
 * Created on 26.apr.2011, 12:56:27
 */

package myrmidia.UI;

import java.awt.Color;
import java.awt.Cursor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.ExecutionException;
import myrmidia.CBR.CBREngine;
import myrmidia.CBR.Resources.SimilarityConfiguration;
import myrmidia.Explanation.ExplanationEngine;

/**
 *
 * @author Glenn Rune Strandbråten
 */
public class ConfigureCBRUI extends javax.swing.JFrame {

    private SimilarityConfiguration simConf;
    private CBRQuery query;

    /** Creates new form ConfigureCBRUI */
    public ConfigureCBRUI() {
        ToolTipManager.sharedInstance().setDismissDelay(20000);
        initComponents();
        simConf = SimilarityConfiguration.getInstance();
        query = null;
    }

    public ConfigureCBRUI(CBRQuery query,JFrame parent) {
        ToolTipManager.sharedInstance().setDismissDelay(20000);
        initComponents();
        setLocationRelativeTo(parent);
        
        simConf = SimilarityConfiguration.getInstance();
        this.query = query;
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        armyPointIntervalField = new myrmidia.UI.Resources.IntTextField();
        kNNField = new myrmidia.UI.Resources.IntTextField();
        nextButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        armyPointIntervalLabel = new javax.swing.JLabel();
        kNNLabel = new javax.swing.JLabel();
        similarityPanel = new javax.swing.JPanel();
        playerSlider = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        outcomeSlider = new javax.swing.JSlider();
        opponentSlider = new javax.swing.JSlider();
        armyCount = new javax.swing.JLabel();
        opponentRaceCount = new javax.swing.JLabel();
        playerRaceCount = new javax.swing.JLabel();
        armyPointSlider = new javax.swing.JSlider();
        armySlider = new javax.swing.JSlider();
        unitSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        armyPointsCount = new javax.swing.JLabel();
        unitCount = new javax.swing.JLabel();
        outcomeCount = new javax.swing.JLabel();
        requiredLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Myrmidia - Configure similarity functions");
        setResizable(false);

        armyPointIntervalField.setText("500");
        armyPointIntervalField.setToolTipText("<html>Affects how large a gap there can be between the desired army points and the army points in the case.<br>E.g.: With an interval of 500 and a query of 2500 will any case with points between 2000 and 3000 have some point similarity<br>while any case with army points outside that range have no point similarity with the query.");

        kNNField.setText("3");

        nextButton.setMnemonic('n');
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        cancelButton.setMnemonic('c');
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        armyPointIntervalLabel.setText("Army point interval*:");

        kNNLabel.setText("Number of cases to retrieve*:");

        similarityPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Configure similarity weights"));

        playerSlider.setToolTipText("Affects how much your selected player race counts when searching through the stored cases.");
        playerSlider.setValue(100);
        playerSlider.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                playerSliderMouseWheelMoved(evt);
            }
        });
        playerSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                playerSliderStateChanged(evt);
            }
        });

        jLabel3.setText("Outcome:");

        jLabel4.setText("Army points:");

        jLabel5.setText("Army:");

        jLabel6.setText("Unit:");

        outcomeSlider.setToolTipText("Affects how much the outcome conditions counts when searhing through the stored cases");
        outcomeSlider.setValue(100);
        outcomeSlider.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                outcomeSliderMouseWheelMoved(evt);
            }
        });
        outcomeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                outcomeSliderStateChanged(evt);
            }
        });

        opponentSlider.setToolTipText("Affects how much your selected opponent race counts when searching through the stored cases.");
        opponentSlider.setValue(100);
        opponentSlider.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                opponentSliderMouseWheelMoved(evt);
            }
        });
        opponentSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                opponentSliderStateChanged(evt);
            }
        });

        armyCount.setText("100%");

        opponentRaceCount.setText("100%");

        playerRaceCount.setText("100%");

        armyPointSlider.setToolTipText("Affects how much the army points counts when searching through the stored cases.");
        armyPointSlider.setValue(100);
        armyPointSlider.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                armyPointSliderMouseWheelMoved(evt);
            }
        });
        armyPointSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                armyPointSliderStateChanged(evt);
            }
        });

        armySlider.setToolTipText("Affects how much the overall army configuration counts when searching for stored cases.");
        armySlider.setValue(100);
        armySlider.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                armySliderMouseWheelMoved(evt);
            }
        });
        armySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                armySliderStateChanged(evt);
            }
        });

        unitSlider.setToolTipText("Affects how much each individual unit counts when searching for stored cases.");
        unitSlider.setValue(100);
        unitSlider.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                unitSliderMouseWheelMoved(evt);
            }
        });
        unitSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                unitSliderStateChanged(evt);
            }
        });

        jLabel1.setText("Player race:");

        jLabel2.setText("Opponent race:");

        armyPointsCount.setText("100%");

        unitCount.setText("100%");

        outcomeCount.setText("100%");

        javax.swing.GroupLayout similarityPanelLayout = new javax.swing.GroupLayout(similarityPanel);
        similarityPanel.setLayout(similarityPanelLayout);
        similarityPanelLayout.setHorizontalGroup(
            similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(similarityPanelLayout.createSequentialGroup()
                .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unitSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opponentSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outcomeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playerSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(armyPointSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(armySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outcomeCount)
                    .addComponent(opponentRaceCount)
                    .addComponent(playerRaceCount)
                    .addComponent(unitCount)
                    .addComponent(armyCount)
                    .addComponent(armyPointsCount))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        similarityPanelLayout.setVerticalGroup(
            similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(similarityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(3, 3, 3)
                        .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(opponentSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(outcomeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(playerRaceCount)
                        .addGap(3, 3, 3)
                        .addComponent(opponentRaceCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outcomeCount)))
                .addGap(9, 9, 9)
                .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(armyPointSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(armySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(unitSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(similarityPanelLayout.createSequentialGroup()
                        .addComponent(armyPointsCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(armyCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unitCount)))
                .addContainerGap())
        );

        unitSlider.getAccessibleContext().setAccessibleDescription("");

        requiredLabel.setText("Fields marked with * are required, and must have a value greater than 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(similarityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(requiredLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kNNLabel)
                            .addComponent(armyPointIntervalLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(armyPointIntervalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelButton)
                                .addGap(6, 6, 6)
                                .addComponent(nextButton))
                            .addComponent(kNNField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(similarityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(armyPointIntervalLabel)
                    .addComponent(armyPointIntervalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton)
                    .addComponent(nextButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kNNLabel)
                    .addComponent(kNNField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requiredLabel)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void unitSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_unitSliderStateChanged
        int value = unitSlider.getValue();
        unitCount.setText(value+"%");
    }//GEN-LAST:event_unitSliderStateChanged

    private void unitSliderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_unitSliderMouseWheelMoved
        int rotation = evt.getWheelRotation();
        if(rotation>0)
            unitSlider.setValue(unitSlider.getValue()-1);
        else
            unitSlider.setValue(unitSlider.getValue()+1);
    }//GEN-LAST:event_unitSliderMouseWheelMoved

    private void armySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_armySliderStateChanged
        int value = armySlider.getValue();
        armyCount.setText(value+"%");
    }//GEN-LAST:event_armySliderStateChanged

    private void armySliderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_armySliderMouseWheelMoved
        int rotation = evt.getWheelRotation();
        if(rotation>0)
            armySlider.setValue(armySlider.getValue()-1);
        else
            armySlider.setValue(armySlider.getValue()+1);
    }//GEN-LAST:event_armySliderMouseWheelMoved

    private void armyPointSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_armyPointSliderStateChanged
        int value = armyPointSlider.getValue();
        armyPointsCount.setText(value+"%");
    }//GEN-LAST:event_armyPointSliderStateChanged

    private void armyPointSliderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_armyPointSliderMouseWheelMoved
        int rotation = evt.getWheelRotation();
        if(rotation>0)
            armyPointSlider.setValue(armyPointSlider.getValue()-1);
        else
            armyPointSlider.setValue(armyPointSlider.getValue()+1);
    }//GEN-LAST:event_armyPointSliderMouseWheelMoved

    private void outcomeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_outcomeSliderStateChanged
        int value = outcomeSlider.getValue();
        outcomeCount.setText(value+"%");
    }//GEN-LAST:event_outcomeSliderStateChanged

    private void outcomeSliderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_outcomeSliderMouseWheelMoved
        int rotation = evt.getWheelRotation();
        if(rotation>0)
            outcomeSlider.setValue(outcomeSlider.getValue()-1);
        else
            outcomeSlider.setValue(outcomeSlider.getValue()+1);
    }//GEN-LAST:event_outcomeSliderMouseWheelMoved

    private void opponentSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_opponentSliderStateChanged
        int value = opponentSlider.getValue();
        opponentRaceCount.setText(value+"%");
    }//GEN-LAST:event_opponentSliderStateChanged

    private void opponentSliderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_opponentSliderMouseWheelMoved
        int rotation = evt.getWheelRotation();
        if(rotation>0)
            opponentSlider.setValue(opponentSlider.getValue()-1);
        else
            opponentSlider.setValue(opponentSlider.getValue()+1);
    }//GEN-LAST:event_opponentSliderMouseWheelMoved

    private void playerSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_playerSliderStateChanged
        int value = playerSlider.getValue();
        playerRaceCount.setText(value+"%");
    }//GEN-LAST:event_playerSliderStateChanged

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if(verifyRequiredFields()){
            if(isRequiredFieldsGreaterThanZero()){
                try {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    parseConfiguration();
                    CBREngine cbrEngine = CBREngine.getInstance();
                    cbrEngine.configure();
                    cbrEngine.preCycle();
                    cbrEngine.cycle(query);
                    cbrEngine.postCycle();
                    ExplanationEngine eng = ExplanationEngine.getInstance();
                    System.out.println(eng.generateTransparencyExplanations());
                    System.out.println(eng.generateJustificationExplanations());
                }
                catch (ExecutionException ex) {}
                finally{
                     setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
            else
                JOptionPane.showMessageDialog(this , "A required field have an invalid value.", "Error 03 - Wrong value", JOptionPane.ERROR_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(this , "Not all required fields are set.", "Error 02 - Required fields", JOptionPane.ERROR_MESSAGE);
        
    }//GEN-LAST:event_nextButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if(query==null)
            System.exit(0);
        else{
            new SelectTaskUI(this).setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void playerSliderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_playerSliderMouseWheelMoved
        int rotation = evt.getWheelRotation();
        if(rotation>0)
            playerSlider.setValue(playerSlider.getValue()-1);
        else
            playerSlider.setValue(playerSlider.getValue()+1);
    }//GEN-LAST:event_playerSliderMouseWheelMoved

    public boolean verifyRequiredFields(){
        boolean points = armyPointIntervalField.getText().isEmpty();
        boolean knn = kNNField.getText().isEmpty();
        if(points)
            armyPointIntervalLabel.setForeground(Color.RED);
        else
            armyPointIntervalLabel.setForeground(Color.BLACK);
        if(knn)
            kNNLabel.setForeground(Color.RED);
        else
            kNNLabel.setForeground(Color.BLACK);
        if(points||knn)
            return false;
        else
            return true;
    }

    private boolean isRequiredFieldsGreaterThanZero() {
        int points = armyPointIntervalField.getValue();
        int knn = kNNField.getValue();
        if(points<=0)
            armyPointIntervalLabel.setForeground(Color.RED);
        else
            armyPointIntervalLabel.setForeground(Color.BLACK);
        if(knn<=0)
            kNNLabel.setForeground(Color.RED);
        else
            kNNLabel.setForeground(Color.BLACK);
        if(points<=0||knn<=0)
            return false;
        else
            return true;
    }

    private void parseConfiguration() {
        double player = (double)playerSlider.getValue()/100;
        double opponent = (double)opponentSlider.getValue()/100;
        double outcome = (double)outcomeSlider.getValue()/100;
        double points =(double)armyPointSlider.getValue()/100;
        double army = (double)armySlider.getValue()/100;
        double unit = (double)unitSlider.getValue()/100;
        int interval = Math.abs(armyPointIntervalField.getValue());
        int k = Math.abs(kNNField.getValue());
        simConf.setConfiguration(opponent, outcome, player, army, points, unit,
                interval, k);
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigureCBRUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel armyCount;
    private myrmidia.UI.Resources.IntTextField armyPointIntervalField;
    private javax.swing.JLabel armyPointIntervalLabel;
    private javax.swing.JSlider armyPointSlider;
    private javax.swing.JLabel armyPointsCount;
    private javax.swing.JSlider armySlider;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private myrmidia.UI.Resources.IntTextField kNNField;
    private javax.swing.JLabel kNNLabel;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel opponentRaceCount;
    private javax.swing.JSlider opponentSlider;
    private javax.swing.JLabel outcomeCount;
    private javax.swing.JSlider outcomeSlider;
    private javax.swing.JLabel playerRaceCount;
    private javax.swing.JSlider playerSlider;
    private javax.swing.JLabel requiredLabel;
    private javax.swing.JPanel similarityPanel;
    private javax.swing.JLabel unitCount;
    private javax.swing.JSlider unitSlider;
    // End of variables declaration//GEN-END:variables
}
