/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokoben;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gio
 */
public class trTableElement extends AbstractTableModel{
    private List<tr_DataModel> transactionsData = new ArrayList<tr_DataModel>();
    private String[] columnName = {"Trans.ID", "Name", "Address", "Telp", "Item Amount", "Item Price", "Status"};
    
    public trTableElement(List<tr_DataModel> transactionsData){
        this.transactionsData = transactionsData;
    }
    
    
    @Override
    public String getColumnName(int column){
        return columnName[column];
    }
    @Override
    public int getRowCount() {
    return transactionsData.size();
    }

    @Override
    public int getColumnCount() {
    return columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object userAttribute = null;
        tr_DataModel userObject = transactionsData.get(rowIndex);
        switch (columnIndex){
            case 0: userAttribute = userObject.getmaster_transaction_id(); break;
            case 1: userAttribute = userObject.getname(); break;
            case 2: userAttribute = userObject.getaddress(); break;
            case 3: userAttribute = userObject.gettelp(); break;
            case 4: userAttribute = userObject.getitem_amount(); break;
            case 5: userAttribute = userObject.getitem_price(); break;
            case 6: userAttribute = userObject.getstatus(); break;
        }
        return userAttribute;
    }
    
}
