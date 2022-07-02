/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokoben;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gio
 */
public class TableElement extends AbstractTableModel{
    private List<DataModel> usernameData = new ArrayList<DataModel>();
    private String[] columnNames = {"Item ID", "Item Name", "Item Price", "Item Stock", "Item Desc"};
    
    public TableElement(List<DataModel> usernameData){
        this.usernameData = usernameData;
    }
    
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
    
    @Override
    public int getRowCount() {
        return usernameData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object userAttribute = null;
        DataModel userObject = usernameData.get(rowIndex);
        switch (columnIndex){
            case 0: userAttribute = userObject.getitem_id(); break;
            case 1: userAttribute = userObject.getitem_name(); break;
            case 2: userAttribute = userObject.getitem_price(); break;
            case 3: userAttribute = userObject.getitem_stock(); break;
            case 4: userAttribute = userObject.getitem_desc(); break;
            default: break;
        }
        return userAttribute;
    }        
}
