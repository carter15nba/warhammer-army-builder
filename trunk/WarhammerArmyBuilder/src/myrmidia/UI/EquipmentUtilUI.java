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
package myrmidia.UI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Set;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import myrmidia.Enums.Races;
import myrmidia.UI.Resources.CheckListItem;
import myrmidia.UI.Resources.UnitModel;
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.Unit;

/**
 * Modal user interface to display the Equipment/Utility/Promotion units and
 * magic items
 * of the selected unit
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class EquipmentUtilUI extends javax.swing.JDialog {

    private UnitModel unitModel;
    private boolean noList;
    /** Creates new form EquipmentUtilUI */
    public EquipmentUtilUI(UnitModel unitModel) {
        initComponents();
        setListSizes();
        setTitle(unitModel.getName());
        initializeLists(unitModel);
        getRootPane().setDefaultButton(okButton);
        setLocationRelativeTo(null);
    }
    /**
     * Creates new form EquipmentUtilUI
     * @param parent The QueryUI parent
     * @param unitModel The UnitModel to display
     */
    public EquipmentUtilUI(QueryUI parent, UnitModel unitModel) {
        super(parent);
        initComponents();
        this.unitModel = unitModel;
        setListSizes();
        setTitle(unitModel.getName());
        initializeLists(unitModel);
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(okButton);
    }

    /**
     * Creates new form EquipmentUtilUI
     * @param parent The JFrame parent
     * @param unitModel The UnitModel to display
     * @param useDefaultListCellRenderer boolean which indicates if the 
     * DefaultListCellRenderer should be used, or if the CheckBoxListRenderer 
     * should be used.
     */
    public EquipmentUtilUI(JFrame parent, UnitModel unitModel, boolean useDefaultListCellRenderer) {
        super(parent);
        initComponents();
        this.unitModel = unitModel;
        if(useDefaultListCellRenderer){
            equipmentList.setCellRenderer(new DefaultListCellRenderer());
            utilityList.setCellRenderer(new DefaultListCellRenderer());
            promotionList.setCellRenderer(new DefaultListCellRenderer());
            noList=true;
            magicScroll.setVisible(false);
            magicLabel.setVisible(false);
        }
        setListSizes();
        setTitle(unitModel.getName());
        initializeLists(unitModel);
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(okButton);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        equipmentScroll = new javax.swing.JScrollPane();
        equipmentList = new javax.swing.JList();
        utilityScroll = new javax.swing.JScrollPane();
        utilityList = new javax.swing.JList();
        magicScroll = new javax.swing.JScrollPane();
        magicList = new javax.swing.JList();
        promotionScroll = new javax.swing.JScrollPane();
        promotionList = new javax.swing.JList();
        magicLabel = new javax.swing.JLabel();
        equipmentLabel = new javax.swing.JLabel();
        utilityLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        promotionLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        equipmentScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        equipmentList.setCellRenderer(new myrmidia.UI.Resources.CheckBoxListRenderer());
        equipmentList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                equipmentListMouseClicked(evt);
            }
        });
        equipmentScroll.setViewportView(equipmentList);

        utilityScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        utilityList.setCellRenderer(new myrmidia.UI.Resources.CheckBoxListRenderer());
        utilityList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                utilityListMouseClicked(evt);
            }
        });
        utilityScroll.setViewportView(utilityList);

        magicScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        magicList.setCellRenderer(new myrmidia.UI.Resources.CheckBoxListRenderer());
        magicList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                magicListMouseClicked(evt);
            }
        });
        magicScroll.setViewportView(magicList);

        promotionScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        promotionList.setCellRenderer(new myrmidia.UI.Resources.CheckBoxListRenderer());
        promotionList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                promotionListMouseClicked(evt);
            }
        });
        promotionScroll.setViewportView(promotionList);

        magicLabel.setText("Magic pile");

        equipmentLabel.setText("Unit equipment");

        utilityLabel.setText("Utility units");

        okButton.setMnemonic('O');
        okButton.setLabel("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        promotionLabel.setText("Promotion unit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(406, 406, 406)
                        .addComponent(okButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(promotionLabel)
                            .addComponent(promotionScroll, 0, 215, Short.MAX_VALUE)
                            .addComponent(equipmentScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                            .addComponent(equipmentLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(magicLabel)
                            .addComponent(utilityLabel)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(utilityScroll, javax.swing.GroupLayout.Alignment.LEADING, 0, 220, Short.MAX_VALUE)
                                .addComponent(magicScroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(equipmentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(equipmentScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(utilityLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(utilityScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(promotionLabel)
                    .addComponent(magicLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(magicScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(promotionScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(okButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action performed when a mouse click were performed inside the equipmentList
     * @param evt The MouseEvent trigger
     */
    private void equipmentListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equipmentListMouseClicked
        performListClick(equipmentList,evt.getPoint());
    }//GEN-LAST:event_equipmentListMouseClicked

    /**
     * Action performed when a mouse click were performed inside the utilityList
     * @param evt The MouseEvent trigger
     */
    private void utilityListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_utilityListMouseClicked
        performListClick(utilityList,evt.getPoint());
    }//GEN-LAST:event_utilityListMouseClicked

    /**
     * Action performed when a mouse click were performed inside the promotionList
     * @param evt The MouseEvent trigger
     */
    private void promotionListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_promotionListMouseClicked
        performListClick(promotionList,evt.getPoint());
    }//GEN-LAST:event_promotionListMouseClicked

    /**
     * Action performed when a mouse click were performed inside the magicList
     * @param evt The MouseEvent trigger
     */
    private void magicListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_magicListMouseClicked
        performListClick(magicList,evt.getPoint());
    }//GEN-LAST:event_magicListMouseClicked

    /**
     * Action performed when the OkButton is selected
     * @param evt The ActionEvent trigger
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if(getParent() instanceof ReviseUI){
            ReviseUI ui = (ReviseUI) getParent();
            ui.parseUnitModelForSelectedUnit(unitModel);
        }
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * Method which populates the list with the data of the UnitModel
     * @param unitModel The UnitModel to populate the lists with
     */
    private void initializeLists(UnitModel unitModel) {
        if(unitModel==null)
            return;
        if(unitModel.isEmpty())
            return;
        if(unitModel.isBattleStandardBearer())
            magicLabel.setText("Battle standards");
        equipmentList.setListData(unitModel.getEquipment());
        utilityList.setListData(unitModel.getUtility());
        promotionList.setListData(unitModel.getPromotion());
        magicList.setListData(unitModel.getMagic());
        magicList.revalidate();
        promotionList.revalidate();
        equipmentList.revalidate();
        utilityList.revalidate();
    }

    /**
     * Method to set the sizes of the lists
     */
    private void setListSizes() {
        Dimension dim = new Dimension(200, 140);
        equipmentScroll.setPreferredSize(dim);
        equipmentScroll.setMaximumSize(dim);
        equipmentScroll.setMinimumSize(dim);
        utilityScroll.setPreferredSize(dim);
        utilityScroll.setMaximumSize(dim);
        utilityScroll.setMinimumSize(dim);
        magicScroll.setPreferredSize(dim);
        magicScroll.setMaximumSize(dim);
        magicScroll.setMinimumSize(dim);
        promotionScroll.setPreferredSize(dim);
        promotionScroll.setMaximumSize(dim);
        promotionScroll.setMinimumSize(dim);
        pack();
    }

    /**
     * Method which performs the desired action when a item in any of the
     * checkBoxLists are clicked.
     * @param list The JList that were clicked
     * @param point The Point of the MouseClick
     */
    private void performListClick(JList list, Point point) {
        if(noList)
            return;
        int index = list.locationToIndex(point);
        if(index==-1)
            return;
        CheckListItem item =(CheckListItem) list.getModel().getElementAt(index);
        item.setSelected(! item.isSelected());
        list.repaint(list.getCellBounds(index, index));
        
        if(item.toString().contains("Battle standard bearer")){
            Container cont = getParent();
                Races race;
                if(cont instanceof QueryUI){
                    QueryUI qui = (QueryUI) cont;
                    race = qui.getPlayerRace();
                }
                else if(cont instanceof ReviseUI){
                    ReviseUI rui = (ReviseUI) cont;
                    race = rui.getPlayerRace();
                }
                else
                    return;
            if(item.isSelected()){
                Set<Equipment> eq = CreateObjectFromDB.getBattleStandards(race);
                unitModel.setMagic(UnitModel.parseEquipment(eq));
                unitModel.setBattleStandardBearer(true);
                magicLabel.setText("Battle standards");
            }
            else{
                Unit unit = CreateObjectFromDB.createUnitFromDB(
                        race+":"+getTitle());
                unitModel.setMagic(UnitModel.parseMagic(unit));
                unitModel.setBattleStandardBearer(false);
                magicLabel.setText("Magic pile");
            }
            initializeLists(unitModel);
        }
        unitModel.sortModels();
        initializeLists(unitModel);
        
    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EquipmentUtilUI(new UnitModel("<placeholder>",-1)).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel equipmentLabel;
    private javax.swing.JList equipmentList;
    private javax.swing.JScrollPane equipmentScroll;
    private javax.swing.JLabel magicLabel;
    private javax.swing.JList magicList;
    private javax.swing.JScrollPane magicScroll;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel promotionLabel;
    private javax.swing.JList promotionList;
    private javax.swing.JScrollPane promotionScroll;
    private javax.swing.JLabel utilityLabel;
    private javax.swing.JList utilityList;
    private javax.swing.JScrollPane utilityScroll;
    // End of variables declaration//GEN-END:variables
}