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

package myrmidia.UI.Resources;

import java.awt.Component;
import javax.swing.JTable;

/**
 * Class used to render JComboBoxes inside TableCells
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class ComboBoxTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer{

    private String[] comboBoxContent;
    private int comboBoxColumn;
    public ComboBoxTableCellRenderer(String[] content,int column){
        comboBoxContent = content;
        comboBoxColumn = column;
    }
 
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean selected,
            boolean focused,
            int row,
            int column){
        setEnabled(table == null || table.isEnabled());

//        Setup the ComboBox in the last Column
        javax.swing.table.TableColumn includeColumn = table.getColumnModel().getColumn(comboBoxColumn);
        includeColumn.setCellEditor(new javax.swing.DefaultCellEditor(new javax.swing.JComboBox(comboBoxContent)));
        includeColumn.setCellRenderer(new javax.swing.table.TableCellRenderer(){
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                javax.swing.JComboBox renderedComponent = new javax.swing.JComboBox(comboBoxContent);
                if(value==null)
                    renderedComponent.setSelectedItem(comboBoxContent[0]);
                else
                    renderedComponent.setSelectedItem(value);
                return renderedComponent;
            }
        });
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
}