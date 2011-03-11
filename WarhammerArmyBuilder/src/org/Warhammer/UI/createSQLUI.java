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
 * createUnitSQLUI.java
 *
 * Created on 14.feb.2011, 09:41:02
 */

package org.Warhammer.UI;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import org.Warhammer.Warhammer.Unit.armyType;
import org.Warhammer.Warhammer.Unit.unitType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import org.Warhammer.Database.DatabaseManager;
import org.Warhammer.UI.Resources.CaseStorage;
import org.Warhammer.UI.Resources.CheckListItem;
import org.Warhammer.Warhammer.Case.Outcomes;
import org.Warhammer.Warhammer.Case.Races;
import org.Warhammer.Warhammer.Equipment.itemType;

/**
 *
 * @author Glenn Rune Strandbråten
 */
public class createSQLUI extends javax.swing.JFrame {

    private static final String insertUnit = "INSERT INTO UNIT(NAME,RACE,COST,MINUNITS,MAXUNITS,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE,MAGICPOINTS) VALUES(";
    private static final String insertUtility = "INSERT INTO UTILITYUNIT(ID,NAME,COST,MINUNITS,REQUIRED,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE) VALUES(";
    private static final String insertUnit_Util = "INSERT INTO UNIT_UTILITY(NAME,UTILID)VALUES(";
    private int pane;
    private int id=0;
    private ArrayList<String> sql;
    private DatabaseManager dbm = DatabaseManager.getInstance();
    private ArrayList<CaseStorage> caseStore;
    /** Creates new form createUnitSQLUI */
    public createSQLUI() {
            initComponents();
            armyPoints.setText("");
            armyPoints.setPreferredSize(new Dimension(80,20));
            sql = new ArrayList<String>();
            String[] model = new String[17];
            model[0] = "All";
            raceBox.addItem("Select race");
            int i=1;
            for (org.Warhammer.Warhammer.Case.Races race : org.Warhammer.Warhammer.Case.Races.values()) {
                raceBox.addItem(race);
                model[i] = "Race:"+race;
                i++;
            }
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            utilTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tableEquipment.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            tableEquipment.getColumnModel().getColumn(6).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(model, 6));
            model = new String[12];
            model[0] = "N/A";
            int pos = 1;
            for (org.Warhammer.Warhammer.Unit.unitType ut : unitType.values()) {
                if (ut == unitType._na) {
                    continue;
                }
                model[pos] = ut.toString();
                pos++;
            }
            table.getColumnModel().getColumn(13).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(model, 13));
            utilTable.getColumnModel().getColumn(14).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(model, 14));
            model = new String[7];
            model[0] = "N/A";
            pos = 1;
            for (org.Warhammer.Warhammer.Unit.armyType ut : armyType.values()) {
                model[pos] = ut.toString();
                pos++;
            }
            table.getColumnModel().getColumn(14).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(model, 14));
            model = new String[10];
            model[0] = "N/A";
            pos=1;
            for(org.Warhammer.Warhammer.Equipment.itemType it : itemType.values()){
                model[pos] = it.toString();
                pos++;
            }
            tableEquipment.getColumnModel().getColumn(5).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(model, 5));
            dbm.connectWithoutHibernate();
            addUnit.doClick();
            addUnit.setMnemonic('a');
            caseStore = new ArrayList<CaseStorage>();
            equipmentList.setCellRenderer(new org.Warhammer.UI.Resources.CheckBoxListRenderer());
            equipmentList.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent evt){
                    JList list = (JList) evt.getSource();
                    int index = list.locationToIndex(evt.getPoint());
                    CheckListItem item =
                            (CheckListItem) list.getModel().getElementAt(index);
                    item.setSelected(! item.isSelected());
                    list.repaint(list.getCellBounds(index, index));
                    specialOccurence(list,item);
                }
            });
            utilityList.setCellRenderer(new org.Warhammer.UI.Resources.CheckBoxListRenderer());
            utilityList.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent evt){
                    JList list = (JList) evt.getSource();
                    int index = list.locationToIndex(evt.getPoint());
                    CheckListItem item =
                            (CheckListItem) list.getModel().getElementAt(index);
                    item.setSelected(! item.isSelected());
                    list.repaint(list.getCellBounds(index, index));
                }
            });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        raceBox = new javax.swing.JComboBox();
        addUnit = new javax.swing.JButton();
        generateSQL = new javax.swing.JButton();
        executeSQL = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        utilTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableAssocUnit_Util = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableEquipment = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableAssocUnit_Equipment = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableSpecialRules = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableAssoc_unit_rule = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableAssocEqRule = new javax.swing.JTable();
        ccase = new javax.swing.JPanel();
        armyPoints = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        equipmentList = new javax.swing.JList();
        jScrollPane10 = new javax.swing.JScrollPane();
        utilityList = new javax.swing.JList();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableArmy = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableCreateCase = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        armyDetails = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("!!!!!!!!!!Warning: This UI is for developers only!!!!!!!!! Create domain knowledge and cases");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        raceBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raceBoxActionPerformed(evt);
            }
        });

        addUnit.setText("Add");
        addUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUnitActionPerformed(evt);
            }
        });

        generateSQL.setText("Generate sql file");
        generateSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateSQLActionPerformed(evt);
            }
        });

        executeSQL.setText("Execute sql");
        executeSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeSQLActionPerformed(evt);
            }
        });

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Cost", "Min number of units", "Max number of units", "M", "WS", "BS", "S", "T", "W", "I", "A", "Ld", "UnitType", "ArmyType", "Magic Points"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.setRowHeight(20);
        jScrollPane1.setViewportView(table);

        tabbedPane.addTab("Create unit", jScrollPane1);

        utilTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Cost", "Number of Units", "Required", "M", "WS", "BS", "S", "T", "W", "I", "A", "LD", "Unit type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        utilTable.setRowHeight(20);
        jScrollPane2.setViewportView(utilTable);

        tabbedPane.addTab("Create util unit", jScrollPane2);

        tableAssocUnit_Util.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unit", "Utility"
            }
        ));
        tableAssocUnit_Util.setRowHeight(20);
        jScrollPane3.setViewportView(tableAssocUnit_Util);

        tabbedPane.addTab("Assoc unit and util", jScrollPane3);

        tableEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Cost", "Range", "Modifier", "ItemType", "Usable by", "DefaultEQ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableEquipment.setRowHeight(20);
        jScrollPane4.setViewportView(tableEquipment);

        tabbedPane.addTab("Create Equipment", jScrollPane4);

        tableAssocUnit_Equipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unit", "Equipment"
            }
        ));
        tableAssocUnit_Equipment.setRowHeight(20);
        jScrollPane5.setViewportView(tableAssocUnit_Equipment);

        tabbedPane.addTab("Assoc unit and eq", jScrollPane5);

        tableSpecialRules.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Rule"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSpecialRules.setRowHeight(20);
        jScrollPane6.setViewportView(tableSpecialRules);

        tabbedPane.addTab("Special Rules", jScrollPane6);

        tableAssoc_unit_rule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unit", "Rule"
            }
        ));
        tableAssoc_unit_rule.setRowHeight(20);
        jScrollPane7.setViewportView(tableAssoc_unit_rule);

        tabbedPane.addTab("Assoc unit and rule", jScrollPane7);

        tableAssocEqRule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment", "Rule"
            }
        ));
        tableAssocEqRule.setRowHeight(20);
        jScrollPane8.setViewportView(tableAssocEqRule);

        tabbedPane.addTab("Assoc eq and rule", jScrollPane8);

        armyPoints.setText("<placeholder>");
        armyPoints.setMaximumSize(new java.awt.Dimension(77, 20));
        armyPoints.setMinimumSize(new java.awt.Dimension(77, 20));

        jLabel3.setText("Army points:");

        jScrollPane9.setViewportView(equipmentList);

        jScrollPane10.setViewportView(utilityList);

        tableArmy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unit", "Number of units"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableArmy.setRowHeight(20);
        jScrollPane11.setViewportView(tableArmy);

        jLabel1.setText("<html>E<br>Q<br>U<br>I<br>P<br>M<br>E<br>N<br>T");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setText("<html>U<br>T<br>I<br>L<br>I<br>T<br>Y");

        jButton1.setMnemonic('g');
        jButton1.setText("Get util/eq");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ccaseLayout = new javax.swing.GroupLayout(ccase);
        ccase.setLayout(ccaseLayout);
        ccaseLayout.setHorizontalGroup(
            ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ccaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ccaseLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(armyPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane10)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addContainerGap())
        );
        ccaseLayout.setVerticalGroup(
            ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ccaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(armyPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                    .addGroup(ccaseLayout.createSequentialGroup()
                        .addGroup(ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ccaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))))
                .addContainerGap())
        );

        tabbedPane.addTab("Create army", ccase);

        jButton2.setMnemonic('g');
        jButton2.setText("Get army details");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tableCreateCase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Case", "Opponent race", "Outcome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCreateCase.setRowHeight(20);
        jScrollPane12.setViewportView(tableCreateCase);

        armyDetails.setColumns(20);
        armyDetails.setRows(5);
        jScrollPane13.setViewportView(armyDetails);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(423, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(360, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        tabbedPane.addTab("Create case", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(raceBox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(addUnit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generateSQL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(executeSQL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadButton)
                .addGap(337, 337, 337))
            .addComponent(tabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateSQL)
                    .addComponent(executeSQL)
                    .addComponent(loadButton)
                    .addComponent(raceBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUnitActionPerformed
        DefaultTableModel tm = null;
        JTable tab = null;
        Object[] row = null;
        if(pane==0){
            tab = table;
            row = new Object[]{null,null,null,null,null,null,null,null,null,null,null,null,null,"N/A","N/A",0};
        }
        else if(pane==1){
            tab = utilTable;
            id+= tab.getRowCount()+1;
            row = new Object[]{id,null,null,null,false,null,null,null,null,null,null,null,null,null,"N/A"};
        }
        else if(pane==2){
            tab = tableAssocUnit_Util;
            row = new Object[]{"N/A","N/A"};
        }
        else if(pane==3){
            tab = tableEquipment;
            id+= tab.getRowCount()+1;
            row = new Object[]{id,null,null,null,null,"N/A","All",false};
        }
        else if(pane==4){
            tab = tableAssocUnit_Equipment;
            row = new Object[]{"N/A","N/A"};
        }
        else if(pane==5){
            tab= tableSpecialRules;
            id+= tab.getRowCount()+1;
            row = new Object[]{id,null};
        }
        else if(pane==6){
            tab = tableAssoc_unit_rule;
            row = new Object[]{"N/A","N/A"};
        }
        else if(pane==7){
            tab = tableAssocEqRule;
            row = new Object[]{"N/A","N/A"};
        }
        else if(pane==8){
            tab = tableArmy;
            row = new Object[]{null,null};
        }
        else if(pane==9){
            tab = tableCreateCase;
            id+= tab.getRowCount()+1;
            row = new Object[]{id,"N/A","N/A","Draw"};
        }
        if(tab!=null){
            tm = (DefaultTableModel) tab.getModel();
            tm.addRow(row);
            tab.setCellSelectionEnabled(true);
            tab.getSelectionModel().setSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
            tab.getColumnModel().getSelectionModel().setSelectionInterval(0, 0);
            tab.requestFocus();
        }
    }//GEN-LAST:event_addUnitActionPerformed

    private void generateSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateSQLActionPerformed
        String race = raceBox.getSelectedItem().toString();
        if(pane==0){
            parseSQLSFromUnitTable();
            org.Warhammer.File.SQLFileWriter.writeRaceUnitSQLFile(race, sql);
        }
        else if(pane==1){
            parseSQLFromUtilityTable();
            org.Warhammer.File.SQLFileWriter.writeUtilityUnitSQLFile(race,sql);
        }
        else if(pane==2){
            parseSQLFromUnit_UtilityTable();
            org.Warhammer.File.SQLFileWriter.write_Unit_UtilitySQLFile(race, sql);
        }
        else if(pane==3){
            parseSQLFromEquipmentTable();
            org.Warhammer.File.SQLFileWriter.writeEquipmentSQLFile(sql);
        }
        else if(pane==4){
            parseSQLFromUnit_EquipmentTable();
            org.Warhammer.File.SQLFileWriter.writeUnit_EquipmentSQLFile(race, sql);
        }
        else if(pane==5){
            parseSQLFromSpecialRules();
            org.Warhammer.File.SQLFileWriter.writeRule(sql);
        }
        else if(pane==6){
            parseSQLFromAssocUnit_Rule();
            org.Warhammer.File.SQLFileWriter.write_unit_ruleSQLFile(race, sql);
        }
        else if(pane==7){
            parseEQ_RuleTable();
            org.Warhammer.File.SQLFileWriter.write_eq_ruleSQLFile(sql);
        }
        else if(pane==8){
            parseArmyTable();
            org.Warhammer.File.SQLFileWriter.write_armySQLFile(race,sql);
        }
        else if(pane==9){
            parseCaseTable();
            org.Warhammer.File.SQLFileWriter.write_caseSQLFile(sql);
        }
        
    }//GEN-LAST:event_generateSQLActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
    JFileChooser jfc = new JFileChooser(new java.io.File("src/org/Warhammer/Database/Resources/"));
    jfc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(java.io.File f) {
                String name = f.getName();
                String filter="";
                if(pane==0){filter = "race_units";}
                if(pane==1){filter = "utilityunits";}
                if(pane==2){filter = "unit_utility";}
                if(pane==3){filter = "equipment";}
                if(pane==4){filter = "unit_equipment";}
                if(pane==5){filter = "specialRules";}
                if(pane==6){filter="unit_rules_";}
                if(pane==7){filter="eq_rules";}
                if(pane==8){filter="army_";}
                if(pane==9){filter="case_";}
                if(name.startsWith(filter))
                    return true;
                else
                    return false;
            }

            @Override
            public String getDescription() {
                return "Eligible .sql files";
            }
        });
        int ret = jfc.showOpenDialog(this);
        if(ret==JFileChooser.APPROVE_OPTION){
            emptyTable();
            ArrayList<String> result = org.Warhammer.File.SQLFileParser.parseSQL(jfc.getSelectedFile());
            if(pane==0)
                populteUnitTable(result);
            else if(pane == 1)
                populateUtilityTable(result);
            else if(pane==2)
                populateUnit_UtilityTable(result);
            else if(pane==3)
                populateEquipmentTable(result);
            else if(pane==4)
                populateUnit_EquipmentTable(result);
            else if(pane==5)
                populateRuleTable(result);        
            else if(pane==6)
                populateUnit_RuleTable(result);
            else if(pane==7)
                populateEQ_RuleTable(result);
            else if(pane==8)
                populateArmyTable(result);
            else if(pane==9)
                populateCaseTable(result);
        }

    }//GEN-LAST:event_loadButtonActionPerformed

    private void raceBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raceBoxActionPerformed
        if(pane==2)
            tabbedPaneStateChanged(null);
    }//GEN-LAST:event_raceBoxActionPerformed

    private void executeSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeSQLActionPerformed
        try {
            if(pane==0)
                parseSQLSFromUnitTable();
            else if(pane==1)
                parseSQLFromUtilityTable();
            else if(pane==2)
                parseSQLFromUnit_UtilityTable();
            else if(pane==3)
                parseSQLFromEquipmentTable();
            else if(pane==4)
                parseSQLFromUnit_EquipmentTable();
            else if(pane==5)
                parseSQLFromSpecialRules();
            else if(pane==6)
                parseSQLFromAssocUnit_Rule();
            else if(pane==7)
                parseEQ_RuleTable();
            else if(pane==8)
                parseArmyTable();
            else if(pane==9)
                parseCaseTable();
            for (String string : sql) {
                dbm.executeSQL(string, org.Warhammer.Database.DatabaseManager.UPDATE_QUERY);
            }
            dbm.commit();
        }

        catch (SQLException ex) {
            Logger.getLogger(createSQLUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex) {
            Logger.getLogger(createSQLUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_executeSQLActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        org.Warhammer.Database.DatabaseManager.getInstance().disconnectNoHibernate(false);
    }//GEN-LAST:event_formWindowClosing

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        pane = tabbedPane.getSelectedIndex();
        try{
            if(pane==1){
                ResultSet res = dbm.executeSQL("Select * from UTILITYUNIT", DatabaseManager.SELECT_QUERY);
                while (res.next()) {
                    id = res.getInt("ID");
                }
            }
            else if(pane==2){
                ResultSet res = dbm.executeSQL("Select name, cost from UTILITYUNIT where name like '"+raceBox.getSelectedItem().toString()+"%'", DatabaseManager.SELECT_QUERY);
                ArrayList<String> util = new ArrayList<String>();
                util.add("N/A");
                while(res.next()){
                    util.add(res.getString("name")+"{"+res.getInt("cost")+"}");
                }
                String[] comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssocUnit_Util.getColumnModel().getColumn(1).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 1));
                res = dbm.executeSQL("Select name from UNIT where race ='"+raceBox.getSelectedItem().toString()+"'", DatabaseManager.SELECT_QUERY);
                util.clear();
                util.add("N/A");
                while(res.next()){
                    util.add(res.getString("name"));
                }
                comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssocUnit_Util.getColumnModel().getColumn(0).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 0));
            }
            else if(pane==3){
                ResultSet res = dbm.executeSQL("SELECT ID FROM EQUIPMENT",DatabaseManager.SELECT_QUERY);
                while(res.next())
                    id = res.getInt("ID");
            }
            else if(pane==4){
                ResultSet res = dbm.executeSQL("SELECT NAME, COST FROM EQUIPMENT WHERE USABLEBY='Race:"+raceBox.getSelectedItem().toString()+"' or USABLEBY='All' ORDER BY NAME ASC",DatabaseManager.SELECT_QUERY);
                ArrayList<String> util = new ArrayList<String>();
                util.add("N/A");
                while(res.next())
                    util.add(res.getString("Name")+"{"+res.getInt("cost")+"}");
                String[] comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssocUnit_Equipment.getColumnModel().getColumn(1).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 1));
                util.clear();
                util.add("N/A");
                res = dbm.executeSQL("Select name from UNIT where race ='"+raceBox.getSelectedItem().toString()+"'", DatabaseManager.SELECT_QUERY);
                while(res.next())
                    util.add(res.getString("Name"));
                comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssocUnit_Equipment.getColumnModel().getColumn(0).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 0));
            }
            else if(pane==5){
                ResultSet res = dbm.executeSQL("Select * from SPECIALRULES", DatabaseManager.SELECT_QUERY);
                id=0;
                while(res.next()){
                    id = res.getInt("ID");
                }
            }
            else if(pane==6){
                ResultSet res = dbm.executeSQL("SELECT NAME FROM UNIT WHERE RACE='"+raceBox.getSelectedItem().toString()+"'", DatabaseManager.SELECT_QUERY);
                ArrayList<String> util = new ArrayList<String>();
                util.add("N/A");
                while(res.next()){
                    util.add(res.getString("Name"));
                }
                String[] comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssoc_unit_rule.getColumnModel().getColumn(0).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 0));
                util.clear();
                util.add("N/A");
                res = dbm.executeSQL("SELECT SPECIALRULE FROM SPECIALRULES ORDER BY SPECIALRULE ASC", DatabaseManager.SELECT_QUERY);
                while(res.next())
                    util.add(res.getString("Specialrule"));
                comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssoc_unit_rule.getColumnModel().getColumn(1).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 1));
            }
            else if(pane==7){
                ResultSet res = dbm.executeSQL("SELECT NAME,COST FROM EQUIPMENT ORDER BY NAME ASC", DatabaseManager.SELECT_QUERY);
                ArrayList<String> util = new ArrayList<String>();
                util.add("N/A");
                while(res.next()){
                    util.add(res.getString("Name")+"{"+res.getInt("cost")+"}");
                }
                String[] comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssocEqRule.getColumnModel().getColumn(0).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 0));
                util.clear();
                util.add("N/A");
                res = dbm.executeSQL("SELECT SPECIALRULE FROM SPECIALRULES ORDER BY SPECIALRULE ASC", DatabaseManager.SELECT_QUERY);
                while(res.next())
                    util.add(res.getString("Specialrule"));
                comp = new String[util.size()];
                comp = util.toArray(comp);
                tableAssocEqRule.getColumnModel().getColumn(1).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 1));
            }
            else if(pane==8){
                ResultSet res = dbm.executeSQL("Select name from unit where race='"+raceBox.getSelectedItem().toString()+"'", DatabaseManager.SELECT_QUERY);
                ArrayList<String> unit = new ArrayList<String>();
                unit.add("N/A");
                while(res.next()){
                    unit.add(res.getString("Name"));
                }
                String[] comp = new String[unit.size()];
                comp = unit.toArray(comp);
                tableArmy.getColumnModel().getColumn(0).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(comp, 0));
                res = dbm.executeSQL("SELECT ID FROM ARMIES", DatabaseManager.SELECT_QUERY);
                while(res.next())
                    id=res.getInt("ID");
            }
            else if(pane==9){
                ResultSet res = dbm.executeSQL("SELECT * FROM ARMIES", DatabaseManager.SELECT_QUERY);
                ArrayList<String> army = new ArrayList<String>();
                army.add("N/A");
                while(res.next()){
                    id = res.getInt("ID");
                    army.add(id+":"+res.getString("PLAYER_RACE"));
                }
                String[] cont = new String[army.size()];
                cont = army.toArray(cont);
                tableCreateCase.getColumnModel().getColumn(1).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(cont, 1));
                Races[] ra = Races.values();
                String[] race = new String[ra.length+1];
                race[0] = "N/A";
                for(int i = 0 ; i < ra.length;i++){
                    race[i+1] = ra[i].toString();
                }
                tableCreateCase.getColumnModel().getColumn(2).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(race, 2));
                Outcomes[] out = Outcomes.values();
                String[] outcome = new String[out.length];
                for(int i = 0 ; i < out.length;i++){
                    outcome[i] = out[i].toString();
                }
                tableCreateCase.getColumnModel().getColumn(3).setCellRenderer(new org.Warhammer.UI.Resources.ComboBoxTableCellRenderer(outcome, 3));
                res = dbm.executeSQL("SELECT ID FROM CASES", DatabaseManager.SELECT_QUERY);
                id=-1;
                while(res.next())
                    id=res.getInt("ID");
                
            }
        }
        catch(SQLException ex){}
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = tableArmy.getSelectedRow();
        if(row<0)
            return;
        ArrayList<String> equip = new ArrayList<String>();
        ArrayList<String> util = new ArrayList<String>();
        while(caseStore.size()<=row){
            caseStore.add(null);
        }
        if(caseStore.get(row)==null)
//        if(row>=caseStore.size())
        {
            try{
                String name = tableArmy.getValueAt(row, 0).toString();
                String[] r = name.split(":");
                String unitRace = r[0];
                ResultSet eq = dbm.executeSQL("SELECT EQUIPMENT.NAME, EQUIPMENT.COST FROM EQUIPMENT, UNIT_EQUIPMENT WHERE UNIT_EQUIPMENT.NAME = '"+name+"' and UNIT_EQUIPMENT.EQUIPMENT_ID = EQUIPMENT.ID and EQUIPMENT.DEFAULTEQ=0 order by equipment.name asc",DatabaseManager.SELECT_QUERY);
                ResultSet ut = dbm.executeSQL("SELECT UTILITYUNIT.NAME,UTILITYUNIT.REQUIRED FROM UTILITYUNIT,UNIT_UTILITY WHERE UTILITYUNIT.ID = UNIT_UTILITY.UTILID AND UNIT_UTILITY.NAME='"+name+"'", DatabaseManager.SELECT_QUERY);
                while(eq.next())
                    equip.add(eq.getString("NAME")+"{"+eq.getInt("COST")+"}");
                while(ut.next())
                    util.add(ut.getString("NAME")+";"+ut.getString("REQUIRED"));
                String selQ = "Select equipment.Name,equipment.Cost "+
                    "from equipment,unit "+
                    "where unit.name ='"+name+"' and "+
                    "equipment.cost <= unit.magicpoints and "+
                    "equipment.defaulteq=0 and "+
                    "(equipment.itemtype='Arcane_Items' "+
                    "or equipment.itemtype='Enchanted_Items' "+
                    "or equipment.itemtype='Magic_Weapon' "+
                    "or equipment.itemtype='Magic_Armour' " +
                    "or equipment.itemtype='Standard' " +
                    "or equipment.itemtype='Talisman')and "+
                    "(equipment.usableby='All' "+
                    "or equipment.usableby='Race:"+unitRace+"') order by equipment.name asc";
                System.out.println(selQ);
                ResultSet res = dbm.executeSQL(selQ, DatabaseManager.SELECT_QUERY);
                while(res.next()){
                     equip.add(res.getString("NAME")+"{"+res.getInt("COST")+"}");
                }
                CheckListItem[] e = new CheckListItem[equip.size()];
                for(int i = 0 ; i < equip.size() ; i++)
                    e[i] = new CheckListItem(equip.get(i));
                CaseStorage c = new CaseStorage();
                c.setEquipment(e);
                e = new CheckListItem[util.size()];
                for(int i = 0 ; i < util.size() ; i++){
                    String s[] = util.get(i).split(";");
                    int j = Integer.parseInt(s[1]);
                    boolean b = false;
                    if(j==1)
                        b = true;
                    e[i] = new CheckListItem(s[0],b);
                }
                c.setUtility(e);
                caseStore.set(row, c);
            }
            catch(SQLException sqle){}
        }
        CaseStorage item = caseStore.get(row);
        equipmentList.setListData(item.getEquipment());
        utilityList.setListData(item.getUtility());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = tableCreateCase.getSelectedRow();
        String army = tableCreateCase.getValueAt(row, 1).toString();
        if(army.contentEquals("N/A"))
            return;
        String[] s = army.split(":");
        ResultSet res = dbm.executeSQL("Select * from armies where ID="+s[0], DatabaseManager.SELECT_QUERY);
        armyDetails.setText("");
        try {
            while (res.next()) {
                armyDetails.append("ID: "+s[0]+"\n");
                armyDetails.append("Race: "+res.getString("PLAYER_RACE")+"\n");
                armyDetails.append("Points: "+res.getInt("ARMY_POINTS")+"\n");
            }
            res = dbm.executeSQL("Select * from army_unit where Army_ID="+s[0], DatabaseManager.SELECT_QUERY);
            while(res.next()){
                armyDetails.append("+---------\n");
                armyDetails.append("| Unit name: "+res.getString("UNIT_NAME")+"\n");
                armyDetails.append("| Number of units: "+res.getInt("NUM_UNITS")+"\n");
            }
            armyDetails.append("+---------\n");
        } catch (SQLException ex) {}
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new createSQLUI().setVisible(true);
            }
        });
    }
    private void parseSQLSFromUnitTable(){
        int rows = table.getRowCount();
        sql.clear();
        for(int i = 0 ; i < rows ; i++){
            if(table.getValueAt(i, 0)==null)
                continue;
            if(table.getValueAt(i, 1)==null)
                continue;
            if(table.getValueAt(i, 2)==null)
                continue;
            if(table.getValueAt(i, 3)==null)
                continue;
            if(table.getValueAt(i, 4)==null)
                continue;
            if(table.getValueAt(i, 5)==null)
                continue;
            if(table.getValueAt(i,6)==null)
                continue;
            if(table.getValueAt(i, 7)==null)
                continue;
            if(table.getValueAt(i, 8)==null)
                continue;
            if(table.getValueAt(i, 9)==null)
                continue;
            if(table.getValueAt(i, 10)==null)
                continue;
            if(table.getValueAt(i, 11)==null)
                continue;
            if(table.getValueAt(i, 12)==null)
                continue;
            String name = table.getValueAt(i, 0).toString();
            int cost = (Integer)table.getValueAt(i, 1);
            int min = (Integer) table.getValueAt(i,2);
            int max = (Integer) table.getValueAt(i, 3);
            String m = table.getValueAt(i,4).toString();
            String ws = table.getValueAt(i,5).toString();
            String bs = table.getValueAt(i,6).toString();
            String s =  table.getValueAt(i,7).toString();
            String t = table.getValueAt(i,8).toString();
            String w = table.getValueAt(i,9).toString();
            String in = table.getValueAt(i,10).toString();
            String a = table.getValueAt(i,11).toString();
            String ld = table.getValueAt(i,12).toString();
            String ut = table.getValueAt(i,13).toString();
            int magicPoints = Integer.parseInt(table.getValueAt(i, 15).toString());
            if(ut.contentEquals("N/A"))
                ut="_na";
            String at = table.getValueAt(i,14).toString();
            if(at.contentEquals("N/A"))
                at="_na";
            String race = raceBox.getSelectedItem().toString();
            String stat = insertUnit+"'"+race+":"+name+"','"+race+"',"+cost+","+min+","+max+",'"+m+"','"+ws+"','"+bs+"','"+s+"','"+t+"','"+w+"','"+in+"','"+a+"','"+ld+"','"+ut+"','"+at+"',"+magicPoints+")";
            sql.add(stat);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUnit;
    private javax.swing.JTextArea armyDetails;
    private javax.swing.JTextField armyPoints;
    private javax.swing.JPanel ccase;
    private javax.swing.JList equipmentList;
    private javax.swing.JButton executeSQL;
    private javax.swing.JButton generateSQL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JButton loadButton;
    private javax.swing.JComboBox raceBox;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable table;
    private javax.swing.JTable tableArmy;
    private javax.swing.JTable tableAssocEqRule;
    private javax.swing.JTable tableAssocUnit_Equipment;
    private javax.swing.JTable tableAssocUnit_Util;
    private javax.swing.JTable tableAssoc_unit_rule;
    private javax.swing.JTable tableCreateCase;
    private javax.swing.JTable tableEquipment;
    private javax.swing.JTable tableSpecialRules;
    private javax.swing.JTable utilTable;
    private javax.swing.JList utilityList;
    // End of variables declaration//GEN-END:variables

    private void populteUnitTable(ArrayList<String> result) {
        DefaultTableModel tm = (DefaultTableModel) table.getModel();
        String race="";
        for (String string : result) {
            String[] s = parseQuery(string);
            race = s[1];
            if(s[13].contentEquals("_na"))
                s[13] = "N/A";
            if(s[14].contentEquals("_na"))
                s[14] = "N/A";
            int points = 0 ;
            if(s.length==17)
                points = Integer.parseInt(s[16]);
            tm.addRow(new Object[]{s[0],Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]),s[5],s[6],s[7],s[8],s[9],s[10],s[11],s[12],s[13],s[14],s[15],points});
        }
        for (int i = 0; i < raceBox.getItemCount() ; i++) {
            Object object = raceBox.getItemAt(i);
            String s = object.toString();
            if(s.equalsIgnoreCase(race)){
                raceBox.setSelectedItem(object);
                break;
            }
        }
    }
    private void populateUtilityTable(ArrayList<String> result){
        DefaultTableModel tm = (DefaultTableModel) utilTable.getModel();
        String name[]= null;
        for (String string : result) {
            String[] s = parseQuery(string);
            name = s[1].split(":");
            if(s[14].contentEquals("_na"))
                s[14] = "N/A";
            boolean req;
            if(Integer.parseInt(s[4])==0)
                req=false;
            else
                req=true;
            tm.addRow(new Object[]{Integer.parseInt(s[0]),name[1],Integer.parseInt(s[2]),Integer.parseInt(s[3]),req,s[5],s[6],s[7],s[8],s[9],s[10],s[11],s[12],s[13],s[14]});
        }

        for (int i = 0; i < raceBox.getItemCount() ; i++) {
            Object object = raceBox.getItemAt(i);
            String s = object.toString();
            if(s.equalsIgnoreCase(name[0])){
                raceBox.setSelectedItem(object);
                break;
            }
        }
    }
    private void populateUnit_UtilityTable(ArrayList<String> result) {
        DefaultTableModel tm = (DefaultTableModel) tableAssocUnit_Util.getModel();
        String[] entry = parseQuery(result.get(0));
        String[] race = entry[0].split(":");
        for (int i = 0; i < raceBox.getItemCount() ; i++) {
            Object object = raceBox.getItemAt(i);
            String s = object.toString();
            System.out.println(race[0]);
            if(s.equalsIgnoreCase(race[0])){
                raceBox.setSelectedItem(object);
                break;
            }
        }
        tabbedPaneStateChanged(null);
        for (String string : result) {
            String[] s = parseQuery(string);
            System.out.println(s[0]+"  "+s[1]);
            ResultSet res = dbm.executeSQL("SELECT NAME,COST FROM UTILITYUNIT WHERE ID="+s[1], DatabaseManager.SELECT_QUERY);
            try {
                while(res.next())
                    tm.addRow(new Object[]{s[0], res.getString("NAME")+"{"+res.getInt("COST")+"}"});
            }
            catch (SQLException ex) {}
        }

    }
    private String[] parseQuery(String q){
        int idx = q.indexOf("VALUES");
        String insert = q.substring(idx+7,q.length()-1);
        String clean = insert.replaceAll("'", "");
        idx = clean.indexOf(":");
        if(pane==0)
            return clean.substring(idx+1).split(",");
        return clean.split(",");
    }

    private void emptyTable() {
        DefaultTableModel tm = (DefaultTableModel) table.getModel();
        for (int i = tm.getRowCount()-1; i > -1; i--) {
            tm.removeRow(i);

        }
    }

    private void parseSQLFromUtilityTable() {
        int rows = utilTable.getRowCount();
        sql.clear();
        for(int i = 0 ; i < rows ; i++){
            if(utilTable.getValueAt(i, 1)==null)
                continue;
            if(utilTable.getValueAt(i, 2)==null)
                continue;
            if(utilTable.getValueAt(i, 3)==null)
                continue;
            if(utilTable.getValueAt(i, 5)==null)
                continue;
            if(utilTable.getValueAt(i,6)==null)
                continue;
            if(utilTable.getValueAt(i, 7)==null)
                continue;
            if(utilTable.getValueAt(i, 8)==null)
                continue;
            if(utilTable.getValueAt(i, 9)==null)
                continue;
            if(utilTable.getValueAt(i, 10)==null)
                continue;
            if(utilTable.getValueAt(i, 11)==null)
                continue;
            if(utilTable.getValueAt(i, 12)==null)
                continue;
            int idx = (Integer)utilTable.getValueAt(i, 0);
            String name = utilTable.getValueAt(i, 1).toString();
            int cost = (Integer)utilTable.getValueAt(i,2);
            int min = (Integer) utilTable.getValueAt(i,3);
            boolean req = (Boolean) utilTable.getValueAt(i, 4);
            int iReq;
            if(req)
                iReq = 1;
            else
                iReq = 0;
            String m = utilTable.getValueAt(i,5).toString();
            String ws = utilTable.getValueAt(i,6).toString();
            String bs = utilTable.getValueAt(i,7).toString();
            String s =  utilTable.getValueAt(i,8).toString();
            String t = utilTable.getValueAt(i,9).toString();
            String w = utilTable.getValueAt(i,10).toString();
            String in = utilTable.getValueAt(i,11).toString();
            String a = utilTable.getValueAt(i,12).toString();
            String ld = utilTable.getValueAt(i,13).toString();
            String ut = utilTable.getValueAt(i,14).toString();
            String race = raceBox.getSelectedItem().toString();
            if(ut.contentEquals("N/A"))
                ut="_na";
            String stat = insertUtility+idx+",'"+race+":"+name+"',"+cost+","+min+","+iReq+",'"+m+"','"+ws+"','"+bs+"','"+s+"','"+t+"','"+w+"','"+in+"','"+a+"','"+ld+"','"+ut+"')";
            System.out.println(stat);
            sql.add(stat);
    }}

    private void parseSQLFromUnit_UtilityTable() {
        int rows = tableAssocUnit_Util.getRowCount();
        sql.clear();
        for (int i = 0; i < rows; i++) {
            String unit = tableAssocUnit_Util.getValueAt(i, 0).toString();
            String util = tableAssocUnit_Util.getValueAt(i, 1).toString();
            if(unit.contentEquals("N/A")||util.contentEquals("N/A"))
                    continue;
            int idx = util.indexOf("{");
            int idx2 = util.indexOf("}");
            String name = util.substring(0,idx);
            String cost = util.substring(idx+1,idx2);
            ResultSet res = dbm.executeSQL("Select ID FROM UTILITYUNIT WHERE NAME='"+name+"' and cost="+cost, DatabaseManager.SELECT_QUERY);
            int tID=-1;
            try {
                while (res.next()) {
                   tID = res.getInt("ID");
                }
            }
            catch (SQLException ex) {}
            if(tID!=-1){
                String stat = insertUnit_Util+"'"+unit+"',"+tID+")";
                System.out.println(stat);
                sql.add(stat);
            }

        }
    }

    private void parseSQLFromSpecialRules() {
        int rows = tableSpecialRules.getRowCount();
        sql.clear();
        for (int i = 0; i < rows; i++) {
            if(tableSpecialRules.getValueAt(i, 1)==null)
                continue;
            int ID = (Integer)tableSpecialRules.getValueAt(i, 0);
            String rule = tableSpecialRules.getValueAt(i, 1).toString();
            
            String query = "INSERT INTO SPECIALRULES(ID,SPECIALRULE)VALUES("+ID+",'"+rule+"')";
            System.out.println(query);
            sql.add(query);
        }
    }

    private void populateRuleTable(ArrayList<String> result) {
        DefaultTableModel tm = (DefaultTableModel) tableSpecialRules.getModel();
        for (String string : result) {
            String[] s = parseQuery(string);
            tm.addRow(new Object[]{Integer.parseInt(s[0]),s[1]});
        }
    }

    private void parseSQLFromAssocUnit_Rule() {
        int rows = tableAssoc_unit_rule.getRowCount();
        sql.clear();
        for(int i = 0 ; i  < rows ; i++ ){
            String unit = (String) tableAssoc_unit_rule.getValueAt(i, 0);
            String rule = (String) tableAssoc_unit_rule.getValueAt(i, 1);
            if(unit.contentEquals("N/A")||rule.contentEquals("N/A"))
                continue;
            ResultSet res = dbm.executeSQL("SELECT ID FROM SPECIALRULES WHERE SPECIALRULE='"+rule+"'", DatabaseManager.SELECT_QUERY);
            int ID = -1;
            try {
                while (res.next()) {
                    ID = res.getInt("ID");
                }
            } catch (SQLException ex) {}
            if(ID==-1)
                continue;
            String query = "INSERT INTO UNIT_RULE(NAME,RULE_ID) VALUES('"+unit+"',"+ID+")";
            sql.add(query);
        }
    }

    private void populateUnit_RuleTable(ArrayList<String> result) {
        DefaultTableModel tm = (DefaultTableModel) tableAssoc_unit_rule.getModel();
        String[] q = parseQuery(result.get(0));
        q = q[0].split(":");
        String race = q[0];
        for (int i = 0; i < raceBox.getItemCount() ; i++) {
            Object object = raceBox.getItemAt(i);
            String s = object.toString();
            if(s.equalsIgnoreCase(race)){
                raceBox.setSelectedItem(object);
                break;
            }
        }
        tabbedPaneStateChanged(null);
        for (String string : result) {
            String[] s = parseQuery(string);
            int ID = Integer.parseInt(s[1]);
            ResultSet res = dbm.executeSQL("SELECT SPECIALRULE FROM SPECIALRULES WHERE ID="+ID, DatabaseManager.SELECT_QUERY);
            String rule="N/A";
            try {
                while (res.next()) {
                    rule = res.getString("SPECIALRULE");
                }
            } catch (SQLException ex) {}
            tm.addRow(new Object[]{s[0],rule});
        }
    }

    private void parseSQLFromEquipmentTable() {
        int rows = tableEquipment.getRowCount();
        sql.clear();
        for(int i = 0 ; i < rows ; i++){
            if(tableEquipment.getValueAt(i, 1)==null)
                continue;
            else if(tableEquipment.getValueAt(i, 2)==null)
                continue;
            else if(tableEquipment.getValueAt(i, 3)==null)
                continue;
            else if(tableEquipment.getValueAt(i, 4)==null)
                continue;
            else if(tableEquipment.getValueAt(i, 5).toString().equals("N/A"))
                continue;
            else if(tableEquipment.getValueAt(i, 6)==null)
                continue;
            int ID = (Integer) tableEquipment.getValueAt(i, 0);
            String name = tableEquipment.getValueAt(i, 1).toString();
            int cost = (Integer) tableEquipment.getValueAt(i, 2);
            int range = (Integer) tableEquipment.getValueAt(i, 3);
            String mod = tableEquipment.getValueAt(i, 4).toString();
            String type = tableEquipment.getValueAt(i, 5).toString();
            String usable = tableEquipment.getValueAt(i, 6).toString();
            boolean def = (Boolean)tableEquipment.getValueAt(i, 7);
            int defEq = 0;
            if(def)
                defEq = 1;
            String query = "INSERT INTO EQUIPMENT(ID,NAME,COST,RANGE,MODIFIER,ITEMTYPE,USABLEBY,DEFAULTEQ)VALUES("+ID+",'"+name+"',"+cost+","+range+",'"+mod+"','"+type+"','"+usable+"',"+defEq+")";
            sql.add(query);
        }
    }

    private void populateEquipmentTable(ArrayList<String> result) {
        DefaultTableModel tm = (DefaultTableModel) tableEquipment.getModel();
        for (String string : result) {
            String[] s = parseQuery(string);
            int def = Integer.parseInt(s[7]);
            boolean defEq = false;
            if(def==1)
                defEq=true;
            tm.addRow(new Object[]{Integer.parseInt(s[0]),s[1],Integer.parseInt(s[2]),Integer.parseInt(s[3]),s[4],s[5],s[6],defEq});
        }
    }

    private void parseSQLFromUnit_EquipmentTable() {
        int rows = tableAssocUnit_Equipment.getRowCount();
        sql.clear();
        for (int i = 0; i < rows; i++) {
            String unit = tableAssocUnit_Equipment.getValueAt(i, 0).toString();
            String eq = tableAssocUnit_Equipment.getValueAt(i, 1).toString();
            if(unit.contentEquals("N/A")||eq.contentEquals("N/A"))
                continue;
            int idx = eq.indexOf("{");
            int idx2 = eq.indexOf("}");
            String equip = eq.substring(0, idx);
            String cost = eq.substring(idx+1, idx2);
            ResultSet res = dbm.executeSQL("select ID from EQUIPMENT where name='"+equip+"' and COST="+cost, DatabaseManager.SELECT_QUERY);
            int ID=-1;
            try {
                while(res.next())
                    ID = res.getInt("ID");
            }
            catch (SQLException ex) {}
            if(ID==-1)
                continue;
            String query = "INSERT INTO UNIT_EQUIPMENT(NAME,EQUIPMENT_ID) VALUES('"+unit+"',"+ID+")";
            System.out.println(query);
            sql.add(query);
        }
    }

    private void populateUnit_EquipmentTable(ArrayList<String> result) {
        DefaultTableModel tm = (DefaultTableModel) tableAssocUnit_Equipment.getModel();
        String[] q = parseQuery(result.get(0));
        q = q[0].split(":");
        String race = q[0];
        for (int i = 0; i < raceBox.getItemCount() ; i++) {
            Object object = raceBox.getItemAt(i);
            String s = object.toString();
            if(s.equalsIgnoreCase(race)){
                raceBox.setSelectedItem(object);
                break;
            }
        }
        tabbedPaneStateChanged(null);

        for (String string : result) {
            String[] s = parseQuery(string);
            int ID = Integer.parseInt(s[1]);
            ResultSet res = dbm.executeSQL("SELECT NAME,COST FROM EQUIPMENT WHERE ID="+ID, DatabaseManager.SELECT_QUERY);
            String rule="N/A";
            try {
                while (res.next()) {
                    rule = res.getString("NAME")+"{"+res.getInt("cost")+"}";
                }
            } catch (SQLException ex) {}
            tm.addRow(new Object[]{s[0],rule});
            
        }

    }

    private void parseEQ_RuleTable() {
        int rows = tableAssocEqRule.getRowCount();
        sql.clear();
        for(int i = 0 ; i < rows ; i++){
            String eq = tableAssocEqRule.getValueAt(i, 0).toString();
            String rule = tableAssocEqRule.getValueAt(i, 1).toString();
            if(eq.contentEquals("N/A")||rule.contentEquals("N/A"))
                continue;
            int idx = eq.indexOf("{");
            int idx2 = eq.indexOf("}");
            String equip = eq.substring(0,idx);
            String cost = eq.substring(idx+1, idx2);
            int eqID = -1;
            int ruleID = -1;
            try{
                ResultSet res = dbm.executeSQL("SELECT ID FROM EQUIPMENT WHERE NAME='"+equip+"' and cost="+cost, DatabaseManager.SELECT_QUERY);
                while(res.next())
                    eqID = res.getInt("ID");
                res = dbm.executeSQL("SELECT ID FROM SPECIALRULES WHERE SPECIALRULE='"+rule+"'", DatabaseManager.SELECT_QUERY);
                while(res.next())
                    ruleID = res.getInt("ID");
            }
            catch(SQLException sqle){}
            if(eqID==-1||ruleID==-1)
                continue;
            String query = "INSERT INTO EQUIPMENT_RULES(EQUIPMENT_ID,RULE_ID)VALUES("+eqID+","+ruleID+")";
            sql.add(query);
        }
    }

    private void populateEQ_RuleTable(ArrayList<String> result) {
       DefaultTableModel tm = (DefaultTableModel) tableAssocEqRule.getModel();
        for (String string : result) {
            String[] s = parseQuery(string);
            try{
                String eqName="";
                String ruleName="";
//                System.out.println("ID:"+s[0]);
//                System.out.println("results:"+string);
                ResultSet res = dbm.executeSQL("SELECT NAME,COST FROM EQUIPMENT WHERE ID="+s[0], DatabaseManager.SELECT_QUERY);
                while(res.next())
                    eqName = res.getString("NAME")+"{"+res.getInt("COST")+"}";
                res = dbm.executeSQL("SELECT SPECIALRULE FROM SPECIALRULES WHERE ID="+s[1], DatabaseManager.SELECT_QUERY);
                while(res.next())
                    ruleName = res.getString("SPECIALRULE");
                System.out.println("eqName:"+eqName);
                tm.addRow(new Object[]{eqName,ruleName});
            }
            catch(SQLException sqle){sqle.printStackTrace();}
        }

    }

    private void parseArmyTable() {
        int rows = tableArmy.getRowCount();
        sql.clear();
        if(armyPoints.getText().length()==0)
            return;
        int cost = Integer.parseInt(armyPoints.getText());
        id++;
        String armyQuery = "INSERT INTO ARMIES(ID,PLAYER_RACE,ARMY_POINTS)VALUES("+id+",'"+raceBox.getSelectedItem().toString()+"',"+cost+")";
        sql.add(armyQuery);
        int unitID = -1;
        ResultSet res = dbm.executeSQL("SELECT ID FROM ARMY_UNIT", DatabaseManager.SELECT_QUERY);
        try {
            while (res.next()) {
                unitID = res.getInt("ID");
                System.out.println("Existing IDs: "+unitID);
            }
        }catch (SQLException ex) {}
        for(int i = 0 ; i < rows; i++){
            if(caseStore.size()<i)
                break;
            if(tableArmy.getValueAt(i, 1)==null)
                continue;
            String unit = tableArmy.getValueAt(i, 0).toString();
            int number = Integer.parseInt(tableArmy.getValueAt(i, 1).toString());
            if(unit.contentEquals("N/A"))
                continue;
            unitID++;
            String unitQ = "INSERT INTO ARMY_UNIT(ID,ARMY_ID,UNIT_NAME,NUM_UNITS)VALUES("+unitID+","+id+",'"+unit+"',"+number+")";
            sql.add(unitQ);
            CaseStorage store = caseStore.get(i);
            if(store.getUtility()==null)
                continue;
            CheckListItem[] utility = store.getUtility();
            for (CheckListItem checkListItem : utility) {
                if(checkListItem==null)
                    continue;
                if(checkListItem.isSelected()){
                    res = dbm.executeSQL("SELECT ID FROM UTILITYUNIT WHERE NAME='"+checkListItem.toString()+"'", DatabaseManager.SELECT_QUERY);
                    int utilID=-1;
                    try {
                        while (res.next()) {
                            utilID = res.getInt("ID");
                        }
                    } catch (SQLException ex) {}
                    String query = "INSERT INTO ARMY_UNIT_UTILITY(ARMY_UNIT_ID,UTILITY_ID)VALUES("+unitID+","+utilID+")";
                    System.out.println(query);
                    sql.add(query);
                }
            }
            if(store.getEquipment()==null)
                continue;
            CheckListItem[] eq = store.getEquipment();
            for (CheckListItem checkListItem : eq) {
                if(checkListItem==null)
                    continue;
                if(checkListItem.isSelected()){
                    String name = checkListItem.toString();
                    int idx = name.indexOf("{");
                    int idx2 = name.indexOf("}");
                    String eqName = name.substring(0, idx);
                    String eqCost = name.substring(idx+1, idx2);
                    res = dbm.executeSQL("SELECT ID FROM EQUIPMENT WHERE NAME='"+eqName+"' and cost="+eqCost, DatabaseManager.SELECT_QUERY);
                    int eqID=-1;
                    try {
                        while (res.next()) {
                            eqID = res.getInt("ID");
                        }
                    } catch (SQLException ex) {ex.printStackTrace();}
                    String query = "INSERT INTO ARMY_UNIT_EQUIPMENT(ARMY_UNIT_ID,EQUIPMENT_ID)VALUES("+unitID+","+eqID+")";
                    System.out.println(query);
                    sql.add(query);
                }
            }
        }
    }

    private void parseCaseTable() {
        int rows = tableCreateCase.getRowCount();
        sql.clear();
        for(int i = 0 ; i < rows ; i++){
            if(tableCreateCase.getValueAt(i, 2).toString()==null)
                continue;
            String race = tableCreateCase.getValueAt(i, 1).toString();
            String opponent = tableCreateCase.getValueAt(i, 2).toString();
            String outcome = tableCreateCase.getValueAt(i, 3).toString();
            String[] split = race.split(":");
            race = split[1];
            String armyID = split[0];
            int ID = Integer.parseInt(tableCreateCase.getValueAt(i, 0).toString());
            if(race.contentEquals("N/A")||opponent.contentEquals("N/A"))
                continue;
            String query = "INSERT INTO CASES(ID,ARMY_ID,OPPONENT_RACE,OUTCOME)VALUES("+ID+","+armyID+",'"+opponent+"','"+outcome+"')";
            System.out.println(query);
            sql.add(query);
        }
    }

    private void populateArmyTable(ArrayList<String> result) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void populateCaseTable(ArrayList<String> result) {
        DefaultTableModel tm = (DefaultTableModel) tableCreateCase.getModel();
        for (String string : result) {
            String[] s = parseQuery(string);
            try{
                int ID = Integer.parseInt(s[0]);
                String name=null;
                ResultSet res = dbm.executeSQL("SELECT PLAYERRACE FROM ARMIES WHERE ID="+ID, DatabaseManager.SELECT_QUERY);
                while(res.next())
                    name = res.getString("PLAYERRACE");
                if(name==null)
                    continue;
                tm.addRow(new Object[]{ID,name,s[1],s[2]});
            }
            catch(SQLException sqle){}
        }
    }
    private void specialOccurence(JList list, CheckListItem item){
        if(item.toString().startsWith("Battle standard bearer")){
            if(item.isSelected()){
                int row = tableArmy.getSelectedRow();
                ArrayList<String> equip = new ArrayList<String>();
                String name = tableArmy.getValueAt(row, 0).toString();
                String[] r = name.split(":");
                String race = r[0];
                ResultSet def = dbm.executeSQL("SELECT EQUIPMENT.NAME, EQUIPMENT.COST FROM EQUIPMENT, UNIT_EQUIPMENT WHERE UNIT_EQUIPMENT.NAME = '"+name+"' and UNIT_EQUIPMENT.EQUIPMENT_ID = EQUIPMENT.ID and EQUIPMENT.DEFAULTEQ=0 order by equipment.name asc",DatabaseManager.SELECT_QUERY);
                ResultSet eq = dbm.executeSQL("Select equipment.Name,equipment.Cost "+
                    "from equipment,unit "+
                    "where unit.name ='"+name+"' and "+
                    "equipment.cost <= unit.magicpoints and "+
                    "equipment.defaulteq=0 and "+
                    "(equipment.itemtype='Arcane_Items' "+
                    "or equipment.itemtype='Enchanted_Items' "+
                    "or equipment.itemtype='Magic_Weapon' "+
                    "or equipment.itemtype='Magic_Armour' " +
                    "or equipment.itemtype='Talisman')and "+
                    "(equipment.usableby='All' "+
                    "or equipment.usableby='Race:"+race+"') order by equipment.name asc",DatabaseManager.SELECT_QUERY);
                ResultSet st = dbm.executeSQL("SELECT EQUIPMENT.NAME, EQUIPMENT.COST FROM EQUIPMENT WHERE EQUIPMENT.ITEMTYPE='Standard' AND (EQUIPMENT.USABLEBY='All' or EQUIPMENT.USABLEBY='Race:"+race+"')", DatabaseManager.SELECT_QUERY);
                try{
                    while(def.next())
                        equip.add(def.getString("NAME")+"{"+def.getInt("COST")+"}");
                    while(eq.next())
                        equip.add(eq.getString("NAME")+"{"+eq.getInt("COST")+"}");
                    while(st.next())
                        equip.add(st.getString("NAME")+"{"+st.getInt("COST")+"}");
                }
                catch(SQLException sqle){}
                int idx = equip.indexOf(item.toString());
                System.out.println("item: "+item.toString()+", idx: "+idx);
                CheckListItem[] cEq = new CheckListItem[equip.size()];
                for(int i = 0 ; i < equip.size() ; i++){
                    cEq[i] = new CheckListItem(equip.get(i));
                    System.out.println(equip.get(i).toString());
                }
                cEq[idx].setSelected(true);
                CaseStorage cs = new CaseStorage();
                cs.setEquipment(cEq);
                cs.setUtility(caseStore.get(row).getUtility());
                caseStore.set(row, cs);
                equipmentList.setListData(cEq);
            }
        }
        else
            return;
    }
}
