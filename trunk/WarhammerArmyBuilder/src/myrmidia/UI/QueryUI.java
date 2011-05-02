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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jcolibri.cbrcore.CBRQuery;
import myrmidia.Enums.Outcomes;
import myrmidia.Enums.Races;
import myrmidia.UI.Resources.CheckListItem;
import myrmidia.UI.Resources.ComboBoxTableCellRenderer;
import myrmidia.UI.Resources.UnitModel;
import myrmidia.UI.Resources.UnitModelControler;
import myrmidia.UI.Resources.WindowCloser;
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Util.PrintFactory;
import myrmidia.Warhammer.Army;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Case;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.Unit;
import myrmidia.Warhammer.UtilityUnit;

/**
 * User interface to create a CBR-query
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class QueryUI extends javax.swing.JFrame {

    private int minimizedHeight = 170;
    private static int defaultHeight = 500;
    private static int defaultWidth = 597;
    private UnitModelControler unitModelControler;
    private String selectedPlayerRace;

    /** Creates new form QueryUI */
    public QueryUI() {
        init();
    }

    /**
     * Creates new form QuetyUI with the supplied parent
     * @param parent The SelectTaskUI parent to set
     */
    public QueryUI(JFrame parent){
        init();
        setLocationRelativeTo(parent);
    }

    /**
     * Performs form initialization and custom initialization of object variables
     */
    private void init(){
        initComponents();
        popupMenu = new javax.swing.JPopupMenu("Edit");
        popupAddRow = new javax.swing.JMenuItem("Add row");
        popupAddRow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRowButtonActionPerformed(e);
            }
        });
        popupRemoveRow = new javax.swing.JMenuItem("Remove row");
        popupRemoveRow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeRowButtonActionPerformed(e);
            }
        });
        popupView = new javax.swing.JMenuItem("View equipment/utility");
        popupView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewEqUtButtonActionPerformed(e);
            }
        });
        popupMenu.add(popupAddRow);
        popupMenu.add(popupRemoveRow);
        popupMenu.add(popupView);

        updateUIBounds();
        initRaceDropDown(opponentRaceInput);
        initRaceDropDown(playerRaceInput);
        addUnitsPanel.setVisible(false);
        unitsTable.getTableHeader().setReorderingAllowed(false);
        unitModelControler = new UnitModelControler();
        selectedPlayerRace = "";
        addWindowListener(new WindowCloser());
        pack();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playerRaceInput = new javax.swing.JComboBox();
        opponentRaceInput = new javax.swing.JComboBox();
        playerRaceLabel = new javax.swing.JLabel();
        opponentRaceLabel = new javax.swing.JLabel();
        armyPointsLabel = new javax.swing.JLabel();
        requiredFieldsLabel = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        addUnitToggleButton = new javax.swing.JToggleButton();
        addUnitsPanel = new javax.swing.JPanel();
        addUnitsScroll = new javax.swing.JScrollPane();
        unitsTable = new javax.swing.JTable();
        addRowButton = new javax.swing.JButton();
        removeRowButton = new javax.swing.JButton();
        viewEqUtButton = new javax.swing.JButton();
        armyPointsField = new myrmidia.UI.Resources.IntTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Myrmidia - Create Query");
        setName("queryUI"); // NOI18N

        playerRaceInput.setNextFocusableComponent(opponentRaceInput);
        playerRaceInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerRaceInputActionPerformed(evt);
            }
        });

        opponentRaceInput.setNextFocusableComponent(armyPointsField);

        playerRaceLabel.setLabelFor(playerRaceInput);
        playerRaceLabel.setText("Select player race*");

        opponentRaceLabel.setLabelFor(opponentRaceInput);
        opponentRaceLabel.setText("Select opponent race*");

        armyPointsLabel.setLabelFor(armyPointsField);
        armyPointsLabel.setText("Set army points*");

        requiredFieldsLabel.setText("Fields marked with * are required");

        nextButton.setMnemonic('n');
        nextButton.setText("Next");
        nextButton.setNextFocusableComponent(playerRaceInput);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        cancelButton.setMnemonic('c');
        cancelButton.setText("Cancel");
        cancelButton.setNextFocusableComponent(nextButton);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        addUnitToggleButton.setMnemonic('a');
        addUnitToggleButton.setText("Add units to query");
        addUnitToggleButton.setEnabled(false);
        addUnitToggleButton.setNextFocusableComponent(cancelButton);
        addUnitToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUnitToggleButtonActionPerformed(evt);
            }
        });

        unitsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Unit name", "Number of units"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        unitsTable.setRowHeight(20);
        unitsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                unitsTableMouseReleased(evt);
            }
        });
        addUnitsScroll.setViewportView(unitsTable);

        addRowButton.setMnemonic('d');
        addRowButton.setText("Add unit");
        addRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowButtonActionPerformed(evt);
            }
        });

        removeRowButton.setMnemonic('r');
        removeRowButton.setText("Remove unit");
        removeRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowButtonActionPerformed(evt);
            }
        });

        viewEqUtButton.setMnemonic('v');
        viewEqUtButton.setText("View equipment/utility");
        viewEqUtButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewEqUtButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addUnitsPanelLayout = new javax.swing.GroupLayout(addUnitsPanel);
        addUnitsPanel.setLayout(addUnitsPanelLayout);
        addUnitsPanelLayout.setHorizontalGroup(
            addUnitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addUnitsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addUnitsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addUnitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(viewEqUtButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addRowButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeRowButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        addUnitsPanelLayout.setVerticalGroup(
            addUnitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUnitsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addUnitsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addUnitsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addGroup(addUnitsPanelLayout.createSequentialGroup()
                        .addComponent(addRowButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeRowButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewEqUtButton)
                        .addGap(195, 195, 195))))
        );

        armyPointsField.setText("intTextField1");
        armyPointsField.setMaximumSize(new java.awt.Dimension(30, 20));
        armyPointsField.setMinimumSize(new java.awt.Dimension(30, 20));
        armyPointsField.setNextFocusableComponent(addUnitToggleButton);
        armyPointsField.setPreferredSize(new java.awt.Dimension(30, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addUnitsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerRaceLabel)
                            .addComponent(opponentRaceLabel)
                            .addComponent(armyPointsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(opponentRaceInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playerRaceInput, 0, 123, Short.MAX_VALUE)
                            .addComponent(armyPointsField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
                        .addComponent(addUnitToggleButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(requiredFieldsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerRaceLabel)
                    .addComponent(playerRaceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addUnitToggleButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opponentRaceLabel)
                    .addComponent(opponentRaceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(armyPointsLabel)
                    .addComponent(armyPointsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addUnitsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nextButton)
                        .addComponent(cancelButton))
                    .addComponent(requiredFieldsLabel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action performed when the AddUnitToggleButton is selected
     * @param evt The ActionEvent trigger
     */
    private void addUnitToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUnitToggleButtonActionPerformed
        if(addUnitToggleButton.isSelected()){
            try{
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                populateUnitComboBox();
                addUnitsPanel.setVisible(true);
            }
            finally{
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
        else 
            addUnitsPanel.setVisible(false);
        updateUIBounds();
    }//GEN-LAST:event_addUnitToggleButtonActionPerformed

    /**
     * Action performed when the nectButton is selected
     * @param evt The ActionEvent trigger
     */
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if(verifyRequiredFields()){
            CBRQuery query = createQuery();
            new ConfigureCBRUI(query, this).setVisible(true);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this , "Not all required fields are set.", "Error 01 - Required fields", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    /**
     * Action perfomed when the cancelButton is selected
     * @param evt The ActionEvent trigger
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        new SelectTaskUI(this).setVisible(true);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Action performed when the player race is selected
     * @param evt The ActionEvent trigger
     */
    private void playerRaceInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerRaceInputActionPerformed
        if(playerRaceInput.getSelectedIndex()==0){
            if(addUnitToggleButton.isSelected())
                addUnitToggleButton.doClick();
            addUnitToggleButton.setEnabled(false);
        }
        else{
            addUnitToggleButton.setEnabled(true);
            if(!selectedPlayerRace.isEmpty()||
                    !selectedPlayerRace.equals(playerRaceInput.getSelectedItem()
                    .toString())){
                if(addUnitToggleButton.isSelected())
                    addUnitToggleButtonActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_playerRaceInputActionPerformed

    /**
     * Aciton peformed when the addRowButt is selected
     * @param evt The ActionEvent trigger
     */
    private void addRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) unitsTable.getModel();
        dtm.addRow(new Object[]{"N/A",null});
        unitModelControler.placeDummyObjects(unitsTable.getRowCount());
    }//GEN-LAST:event_addRowButtonActionPerformed

    /**
     * Action performed when the removeRowButton is selected
     * @param evt The ActionEvent trigger
     */
    private void removeRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRowButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) unitsTable.getModel();
        int selectedRow = unitsTable.getSelectedRow();
        if(selectedRow==-1)
            return;
        dtm.removeRow(selectedRow);
        unitModelControler.removeUnitModel(selectedRow);
    }//GEN-LAST:event_removeRowButtonActionPerformed

    /**
     * Action performed when the viewEqUtButton is selected
     * @param evt The ActionEvent trigger
     */
    private void viewEqUtButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewEqUtButtonActionPerformed
        int selectedRow = unitsTable.getSelectedRow();
        if(selectedRow==-1)
            return;
        try{
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            new EquipmentUtilUI(this, getUnitModel(selectedRow)).setVisible(true);
        }
        catch(NullPointerException npe){}
        finally{
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_viewEqUtButtonActionPerformed

    /**
     * Action performed when the a mouse button is released inside the unitsTable
     * @param evt The MouseEvent trigger
     */
    private void unitsTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unitsTableMouseReleased
        if(evt.isPopupTrigger()){
            int row = unitsTable.rowAtPoint(evt.getPoint());
            unitsTable.setRowSelectionInterval(row, row);           
            popupMenu.show(unitsTable, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_unitsTableMouseReleased

    /**
     * Method which populates the race combo box with the available races
     * @param box The JComboBox to populate
     */
    private void initRaceDropDown(JComboBox box){
        box.addItem("N/A");
        for(Races race : Races.values())
            box.addItem(race);
    }

    /**
     * Method which verifies if all the required fields are filled out
     * @return true if all reauired fields are filled, false if any required
     * field not is filled
     */
    private boolean verifyRequiredFields(){
        boolean verified = true;
        if(playerRaceInput.getSelectedIndex()==0){
            verified = false;
            playerRaceLabel.setForeground(Color.RED);
        }
        else
            playerRaceLabel.setForeground(Color.BLACK);
        if(opponentRaceInput.getSelectedIndex()==0){
            verified = false;
            opponentRaceLabel.setForeground(Color.RED);
        }
        else
            opponentRaceLabel.setForeground(Color.BLACK);
        if(armyPointsField.getText().isEmpty()){
            verified = false;
            armyPointsLabel.setForeground(Color.RED);
        }
        else
            armyPointsLabel.setForeground(Color.BLACK);
        return verified;
    }

    /**
     * Method which updates the size of the UI when the addUnitToggleButton
     * changes state
     */
    private void updateUIBounds() {
        if(addUnitToggleButton.isSelected()) {
            setMinimumSize(new Dimension(defaultWidth, defaultHeight));
            if(getWidth()<defaultWidth)
                setPreferredSize(new Dimension(defaultWidth,getHeight()));
            if(getHeight()<defaultHeight)
                setPreferredSize(new Dimension(getWidth(),defaultHeight));
        }
        else{
            setMinimumSize(new Dimension(defaultWidth, minimizedHeight));
            if(getWidth()==defaultWidth&&getHeight()==defaultHeight){
                setPreferredSize(getMinimumSize());
                pack();
            }
            if(getWidth()<defaultWidth)
                setPreferredSize(new Dimension(defaultWidth, getHeight()));
            if(getHeight()<minimizedHeight)
                setPreferredSize(new Dimension(getWidth(),minimizedHeight));

        }
    }

    /**
     * This method populates the combobox in the 1st column in the unitsTable
     * with the available units
     */
    public void populateUnitComboBox(){
        List<Unit> unit = CreateObjectFromDB.getRaceUnits(Races.valueOf(
        playerRaceInput.getSelectedItem().toString()));
        String[] names = new String[unit.size()+1];
        names[0] = "N/A";
        int pos = 1;
        for (Unit u : unit) {
            String name = u.getName();
            String[] array = name.split(":");
            names[pos++] = array[1];
        }
        unitsTable.getColumnModel().getColumn(0).setCellRenderer(new ComboBoxTableCellRenderer(names, 0));
        unitsTable.setValueAt("N/A", 0, 0);
    }

    /**
     * Method wich returns the UnitModel from the selected row, if the unitModel
     * at the row dont match the row data, a new unit model is returned.
     * @param selectedRow int The selected row in the unitsTable
     * @return The UnitModel for the selected row in the table
     */
    private UnitModel getUnitModel(int selectedRow) {
        String unitName = unitsTable.getValueAt(selectedRow, 0).toString();
        if(unitName.contentEquals("N/A"))
            return null;
        UnitModel model = unitModelControler.getUnitModel(selectedRow);
        if(model==null){
            Unit unit = (Unit) CreateObjectFromDB.createUnitFromDB(
                    playerRaceInput.getSelectedItem().toString()+":"+unitName);
            model = UnitModel.parseUnitModelFromUnit(unit);
            model.setRow(selectedRow);
            unitModelControler.addUnitModel(selectedRow, model);
        }
        else if((model.getName().contentEquals(unitName)) &&
                (model.getRow() == selectedRow)){
            return model;
        }
        else{
            Unit unit = (Unit) CreateObjectFromDB.createUnitFromDB(
                    playerRaceInput.getSelectedItem().toString()+":"+unitName);
            model = UnitModel.parseUnitModelFromUnit(unit);
            model.setRow(selectedRow);
            unitModelControler.replaceUnitModel(selectedRow, model);
        }
        return model;
    }

    /**
     * Method to get the selected Player race
     * @return The selected player Race
     */
    public Races getPlayerRace(){
        return Races.valueOf(playerRaceInput.getSelectedItem().toString());
    }

    /**
     * Method which creates the CBRQuery from the data supplied in the UI.
     * @return The created CBRQuery
     */
    private CBRQuery createQuery(){
        Case queryCase = new Case();
        queryCase.setOpponent(Races.valueOf(opponentRaceInput.getSelectedItem()
                .toString()));
        queryCase.setOutcome(Outcomes.Victory);

        Army queryArmy = new Army();
        queryArmy.setArmyPoints(armyPointsField.getValue());
        queryArmy.setPlayerRace(Races.valueOf(playerRaceInput.getSelectedItem()
                .toString()));
        queryArmy.setArmyUnits(createArmyUnits(queryArmy.getPlayerRace()));
        queryCase.setArmy(queryArmy);
        PrintFactory.printCase(queryCase, true, null);
        CBRQuery cbrQuery = new CBRQuery();
        cbrQuery.setDescription(queryCase);
        return cbrQuery;
    }

    /**
     * Method which creates the army units contained in the unitsTable and
     * returns them as a set of army units.
     * @param race Races The player race
     * @return The set of ArmyUnits parsed from the unitsTable
     */
    private Set<ArmyUnit> createArmyUnits(Races race){
        Set<ArmyUnit> units = new HashSet<ArmyUnit>();
        int rows = unitsTable.getRowCount();
        for(int i=0; i<rows; i++){
            if(unitsTable.getValueAt(i, 0)==null)
                continue;
            String name = unitsTable.getValueAt(i, 0).toString();
            if(name.equalsIgnoreCase("N/A"))
                continue;
            Object obj = unitsTable.getValueAt(i, 1);
            int number=-1;
            if(obj!=null)
                number = Integer.parseInt(obj.toString());
            Unit unit = CreateObjectFromDB.createUnitFromDB(race.toString()+
                    ":"+name);
            ArmyUnit armyUnit = new ArmyUnit();
            armyUnit.setUnit(unit);
            if(number==-1)
                number = unit.getMinNumber();
            armyUnit.setNumberOfUnits(number);
            UnitModel model = unitModelControler.getUnitModel(i);
            assignModelSelectionToUnit(armyUnit, model);
            units.add(armyUnit);
        }
        return units;
    }

    /**
     * Method which assigns the UnitModel selection to the army unit
     * @param unit ArmyUnit The armyUnit to assign the UnitModel selections to
     * @param model UnitModel The unit model to assign to the ArmyUnit
     */
    private void assignModelSelectionToUnit(ArmyUnit unit, UnitModel model){
        CheckListItem[] items = model.getEquipment();
        for (CheckListItem cli : items) {
            if(cli.isSelected()){
                int index = cli.toString().indexOf(")");
                String name = cli.toString().substring(index+1);
                int cost = Integer.parseInt(cli.toString().substring(1, index));
                Equipment eq = CreateObjectFromDB.createEquipment(name, cost);
                unit.getEquipment().add(eq);
            }
        }
        items = model.getMagic();
        for (CheckListItem cli : items) {
            if(cli.isSelected()){
                int index = cli.toString().indexOf(")");
                String name = cli.toString().substring(index+1);
                int cost = Integer.parseInt(cli.toString().substring(1,index));
                Equipment eq = CreateObjectFromDB.createEquipment(name, cost);
                unit.getEquipment().add(eq);
            }
        }
        items = model.getPromotion();
        for (CheckListItem cli : items) {
            if(cli.isSelected()){
                int index = cli.toString().indexOf(")");
                String name = getPlayerRace().toString()+":"+
                        cli.toString().substring(index+1);
                UtilityUnit ut = CreateObjectFromDB.createUtilityUnit(name);
                unit.getUtility().add(ut);
            }
        }
        items = model.getUtility();
        for (CheckListItem cli : items) {
            if(cli.isSelected()){
                int index = cli.toString().indexOf(")");
                String name = getPlayerRace().toString()+":"+
                        cli.toString().substring(index+1);
                UtilityUnit ut = CreateObjectFromDB.createUtilityUnit(name);
                unit.getUtility().add(ut);
            }
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QueryUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRowButton;
    private javax.swing.JToggleButton addUnitToggleButton;
    private javax.swing.JPanel addUnitsPanel;
    private javax.swing.JScrollPane addUnitsScroll;
    private myrmidia.UI.Resources.IntTextField armyPointsField;
    private javax.swing.JLabel armyPointsLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JComboBox opponentRaceInput;
    private javax.swing.JLabel opponentRaceLabel;
    private javax.swing.JComboBox playerRaceInput;
    private javax.swing.JLabel playerRaceLabel;
    private javax.swing.JButton removeRowButton;
    private javax.swing.JLabel requiredFieldsLabel;
    private javax.swing.JTable unitsTable;
    private javax.swing.JButton viewEqUtButton;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JMenuItem popupRemoveRow;
    private javax.swing.JMenuItem popupAddRow;
    private javax.swing.JMenuItem popupView;
    private javax.swing.JPopupMenu popupMenu;
}
