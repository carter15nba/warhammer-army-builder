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

package UI.Resources;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class WarhammerRaceTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer{


    public static final String[] types = new String[]{"-","Ca","Ch","In","Mo","MB","MC","MI","Sw","Un","WB","WM"};
    /**
     *
     * @param table
     * @param value
     * @param selected
     * @param focused
     * @param row
     * @param column
     * @return
     */
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean selected,
            boolean focused,
            int row,
            int column){
        setEnabled(table == null || table.isEnabled());
        if((row % 2) == 1)
            setBackground(Color.LIGHT_GRAY);
        else
            setBackground(Color.WHITE);
        //Setup the ComboBox in the last Column
//        javax.swing.table.TableColumn includeColumn = table.getColumnModel().getColumn(10);
//        includeColumn.setCellEditor(new javax.swing.DefaultCellEditor(new javax.swing.JComboBox(types)));
//        includeColumn.setCellRenderer(new javax.swing.table.TableCellRenderer(){
//            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                javax.swing.JComboBox renderedComponent = new javax.swing.JComboBox(types);
//                renderedComponent.setSelectedItem(value);
//                return renderedComponent;
//            }
//        });
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }

}
