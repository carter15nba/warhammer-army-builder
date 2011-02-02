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

package UI;

import javax.swing.JOptionPane;


/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class CreateRaceXMLUI extends javax.swing.JFrame{
    private javax.swing.JButton addUnitButton;
    private javax.swing.JButton removeSelectedButton;
    private javax.swing.JButton addUtilityUnitButton;
    private javax.swing.JLabel raceNameLabel;
    private javax.swing.JTextField raceNameTextField;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JTable unitTable;
    private javax.swing.JScrollPane unitTableScrollPane;
    private int[] attributes = new int[]{Warhammer.Unit_old.CHARACTHERISTIC_MOVEMENT_ALLOVANCE,
    Warhammer.Unit_old.CHARACTHERISTIC_WEAPON_SKILL,
    Warhammer.Unit_old.CHARACTHERISTIC_BALLISTIC_SKILL,
    Warhammer.Unit_old.CHARACTHERISTIC_STRENGTH,
    Warhammer.Unit_old.CHARACTHERISTIC_TOUGHNESS,
    Warhammer.Unit_old.CHARACTHERISTIC_WOUNDS,
    Warhammer.Unit_old.CHARACTHERISTIC_INITIATIVE,
    Warhammer.Unit_old.CHARACTHERISTIC_ATTACKS,
    Warhammer.Unit_old.CHARACTHERISTIC_LEADERSHIP};
    private final String TITLE = "Warhammer Race XML editor";

    private Warhammer.Race race;
    public CreateRaceXMLUI(){
        initComponents();
        setupTableColumnWidth();
        setupTableCellRendererAndEditor();
        java.awt.Toolkit toolkit = getToolkit();
        java.awt.Dimension dimension = toolkit.getScreenSize();
        setLocation((dimension.width-getWidth())/2, (dimension.height-getHeight())/2);
        setTitle(TITLE);
        race = new Warhammer.Race();
    }
    // <editor-fold defaultstate="collapsed" desc="initComponents()">
    private void initComponents(){
        // <editor-fold defaultstate="collapsed" desc="instantiateUIElements">
        addUnitButton = new javax.swing.JButton("Add unit");
        removeSelectedButton = new javax.swing.JButton("Remove selected unit(s)");
        addUtilityUnitButton = new javax.swing.JButton("Add utility unit");
        raceNameLabel = new javax.swing.JLabel("Race name:");
        raceNameTextField = new javax.swing.JTextField();
        menu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu("File");
        newMenuItem = new javax.swing.JMenuItem("New");
        saveMenuItem = new javax.swing.JMenuItem("Save");
        openMenuItem = new javax.swing.JMenuItem("Open");
        exitMenuItem = new javax.swing.JMenuItem("Exit");
        unitTable = new javax.swing.JTable();
        unitTableScrollPane = new javax.swing.JScrollPane();
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="create menu bar">
        fileMenu.setMnemonic(java.awt.event.KeyEvent.VK_F);

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_N);
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuItem);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_O);
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_S);
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_X);
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menu.add(fileMenu);
        setJMenuBar(menu);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="setup window parameters">
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="create buttons">
        addUnitButton.setMnemonic(java.awt.event.KeyEvent.VK_U);
        addUnitButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUnitButtonActionPerformed(evt);
            }
        });

        addUtilityUnitButton.setMnemonic(java.awt.event.KeyEvent.VK_T);
        addUtilityUnitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUtilityUnitButtonActionPerformed(evt);
            }
        });

        removeSelectedButton.setMnemonic(java.awt.event.KeyEvent.VK_R);
        removeSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSelectedButtonActionPerformed(evt);
            }
        });
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="create table">
        unitTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Name", "M", "WS", "BS", "S",
                "T", "W", "I", "A", "Ld", "Type"}){
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
                };
                @Override
                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            }
        );
        unitTable.setRowHeight(20);
        unitTable.getTableHeader().setReorderingAllowed(false);
        unitTable.setDefaultRenderer(Object.class, new UI.Resources.WarhammerRaceTableCellRenderer());
        unitTableScrollPane.setViewportView(unitTable);
        unitTableScrollPane.getVerticalScrollBar().setBlockIncrement(20);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="setupUILayout">
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(unitTableScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(raceNameLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(raceNameTextField))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(addUnitButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(addUtilityUnitButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                                .addComponent(removeSelectedButton)))
                        .addContainerGap())
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(raceNameLabel)
                            .addComponent(raceNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addUtilityUnitButton)
                            .addComponent(addUnitButton)
                            .addComponent(removeSelectedButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(unitTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );

        pack();
        // </editor-fold>
    }// </editor-fold>
    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt){
        race = new Warhammer.Race();
        populateTable();
    }
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt){
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser(
                File.Resources.XML.DEFAULT_XML_LOCATION);
        jfc.setFileFilter(new UI.Resources.WarhammerXMLFileFilter());
        int retVal = jfc.showOpenDialog(this);
        if(retVal==javax.swing.JFileChooser.APPROVE_OPTION){
            File.XMLRaceParser xmlRP = new File.XMLRaceParser();
            race = xmlRP.parseDocument(jfc.getSelectedFile());
            raceNameTextField.setText(race.getRaceName());
            populateTable();
        }
    }
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt){
        race = createRaceFromTable();
        if(race!=null){
            File.XMLRaceWriter xmlRaceWriter = new File.XMLRaceWriter();
            xmlRaceWriter.createDocument(race, "");
        }
    }
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt){
        //TODO: Save check
        System.exit(0);
    }
    private void addUnitButtonActionPerformed(java.awt.event.ActionEvent evt){
        javax.swing.table.DefaultTableModel tableModel =
                (javax.swing.table.DefaultTableModel) unitTable.getModel();
        tableModel.addRow(new Object[]{"","","","","","","","","","","-"});
        java.awt.Rectangle visRect = new java.awt.Rectangle(
                unitTable.getWidth(), unitTable.getHeight()+20, 1, 1);
        unitTable.scrollRectToVisible(visRect);
        int lastRow = tableModel.getRowCount()-1;
        unitTable.clearSelection();
        unitTable.addRowSelectionInterval(lastRow, lastRow);
        unitTable.editCellAt(lastRow, 0);
        unitTable.requestFocus();

    }
    private void addUtilityUnitButtonActionPerformed(java.awt.event.ActionEvent evt){
        java.util.ArrayList<String> units = new java.util.ArrayList<String>();
        units.add("-");
        int rows = unitTable.getRowCount();
        for (int i = 0; i < rows; i++) {
            String value = unitTable.getValueAt(i, 0).toString();
            if(!value.startsWith("-"))
                units.add(value);
        }

        String[] eligibleUnits = new String[units.size()];
        eligibleUnits = units.toArray(eligibleUnits);
        new SelectUtilUI(eligibleUnits, this);
    }
    private void removeSelectedButtonActionPerformed(java.awt.event.ActionEvent evt){
        javax.swing.table.DefaultTableModel tableModel =
                (javax.swing.table.DefaultTableModel) unitTable.getModel();
        int[] rows = unitTable.getSelectedRows();
        for(int i = rows.length-1 ; i>-1 ; i--)
            tableModel.removeRow(rows[i]);
    }
    private void formWindowClosing(java.awt.event.WindowEvent evt){
        //TODO: Save check
        System.exit(0);
    }
    private void setupTableColumnWidth()
    {
        unitTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        unitTable.getColumnModel().getColumn(0).setPreferredWidth(203);
        unitTable.getColumnModel().getColumn(0).setMinWidth(150);
        unitTable.getColumnModel().getColumn(1).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(1).setMinWidth(35);
        unitTable.getColumnModel().getColumn(2).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(2).setMinWidth(35);
        unitTable.getColumnModel().getColumn(3).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(3).setMinWidth(35);
        unitTable.getColumnModel().getColumn(4).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(4).setMinWidth(35);
        unitTable.getColumnModel().getColumn(5).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(5).setMinWidth(35);
        unitTable.getColumnModel().getColumn(6).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(6).setMinWidth(35);
        unitTable.getColumnModel().getColumn(7).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(7).setMinWidth(35);
        unitTable.getColumnModel().getColumn(8).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(8).setMinWidth(35);
        unitTable.getColumnModel().getColumn(9).setPreferredWidth(35);
        unitTable.getColumnModel().getColumn(9).setMinWidth(35);
        unitTable.getColumnModel().getColumn(10).setPreferredWidth(55);
        unitTable.getColumnModel().getColumn(10).setMinWidth(55);
    }
    private void setupTableCellRendererAndEditor()
    {
        javax.swing.table.TableColumn includeColumn = unitTable.getColumnModel().getColumn(10);
        includeColumn.setCellEditor(new javax.swing.DefaultCellEditor(new javax.swing.JComboBox(UI.Resources.WarhammerRaceTableCellRenderer.TYPES)));
        includeColumn.setCellRenderer(new javax.swing.table.TableCellRenderer(){
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                javax.swing.JComboBox renderedComponent = new javax.swing.JComboBox(UI.Resources.WarhammerRaceTableCellRenderer.TYPES);
                renderedComponent.setSelectedItem(value);
                return renderedComponent;
            }
        });
    }

    private void populateTable(){
        if(race!=null){
            emptyTable();
            javax.swing.table.DefaultTableModel tableModel =
                (javax.swing.table.DefaultTableModel) unitTable.getModel();
            java.util.ArrayList<Warhammer.ArmyUnit> units = race.getUnits();
            for(Warhammer.ArmyUnit unit : units){
                tableModel.addRow(unit.getTableObject());
                if(unit.getUtilityUnits()!=null)
                    for(Warhammer.UtilityUnit_old crew : unit.getUtilityUnits()){
                        tableModel.addRow(crew.getTableObject());
                    }
            }
        }
    }
    private void emptyTable(){
        javax.swing.table.DefaultTableModel tableModel =
                (javax.swing.table.DefaultTableModel) unitTable.getModel();
        int rows = tableModel.getRowCount()-1;
        for(int i = rows ; i > -1 ; i--){
            tableModel.removeRow(i);
        }
    }

    private Warhammer.Race createRaceFromTable(){
        race = new Warhammer.Race();
        String raceName = raceNameTextField.getText();
        if(raceName.length()==0){
            JOptionPane.showMessageDialog(this, "You must enter the name of the race in order to save the race to a file.", "Error: No supplied race name", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        javax.swing.table.DefaultTableModel tableModel =
                (javax.swing.table.DefaultTableModel) unitTable.getModel();
        int rows = tableModel.getRowCount();
        Warhammer.ArmyUnit unit = null;
        for(int i = 0 ; i < rows ; i++){
            String unitName = (String) tableModel.getValueAt(i, 0);
            if(unitName.startsWith("-")){
                Warhammer.UtilityUnit_old utilUnit = new Warhammer.UtilityUnit_old();
                utilUnit.setName(unitName);
                createUtilityUnit(utilUnit, tableModel, i);
                unit.addCrew(utilUnit);
            }
            else{
                unit = new Warhammer.ArmyUnit();
                createUnit(unit, tableModel, i);
                race.addUnit(unit);
            }
        }
        race.assignRace(raceName);
        return race;
    }
    private void createUnit(Warhammer.ArmyUnit unit,
            javax.swing.table.DefaultTableModel tableModel,
            int row){
        unit.setName((String) tableModel.getValueAt(row, 0));
        unit.setCategory((String) tableModel.getValueAt(row, 10));
        for(int i = 0; i < attributes.length ; i++){
            String tableValue = String.valueOf(tableModel.getValueAt(row, i+1));
            int value = Integer.parseInt(tableValue);
            unit.setCharacteristics(attributes[i], value);
        }
    }
    private void createUtilityUnit(Warhammer.UtilityUnit_old unit,
            javax.swing.table.DefaultTableModel tableModel,
            int row){
        unit.setName((String) tableModel.getValueAt(row, 0));
        unit.setCategory((String) tableModel.getValueAt(row, 10));
        for(int i = 0; i < attributes.length ; i++){
            String tableValue = String.valueOf(tableModel.getValueAt(row, i+1));
            int value = Integer.parseInt(tableValue);
            unit.setCharacteristics(attributes[i], value);
        }
    }
    public void addUtilityUnit(String nameOfParent, String nameOfChild){
        javax.swing.table.DefaultTableModel tableModel =
                (javax.swing.table.DefaultTableModel) unitTable.getModel();
        int rows = tableModel.getRowCount();
        for (int i = 0; i < rows; i++) {
            String value = tableModel.getValueAt(i, 0).toString();
            if(value.equals(nameOfParent)){
                if(i==rows-1){
                    tableModel.addRow(new Object[]{nameOfChild,"","","","","","","","","","-"});
                    unitTable.clearSelection();
                    unitTable.editCellAt(i+1, 0);
                    break;
                }
                else{
                    tableModel.insertRow(i+1, new Object[]{nameOfChild,"","","","","","","","","","-"});
                    unitTable.clearSelection();
                    unitTable.editCellAt(i, 0);
                    break;
                }
            }
        }
    }

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {}
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CreateRaceXMLUI().setVisible(true);
            }
        });
    }

//	public static void main(String[] args){
//           //TODO: Move main method to appropriate location.
////		Bretonnia b = new Bretonnia(Race.RACE_BRETONNIA);
////		b.createArmy();
////		System.out.println(b.toString());
//                File.XMLRaceParser t = new File.XMLRaceParser();
//                Race r = t.parseDocument(new java.io.File("Resources/XML/Bretonnia.xml"));
//                System.out.println(r.toString());
//
////                File.XMLRaceWriter w = new File.XMLRaceWriter();
////                w.createDocument(r, "Glenn Rune Strandbråten");
////                try{
////                    w.createDocument();
////                }
////                catch(Exception e){}
//	}
}
