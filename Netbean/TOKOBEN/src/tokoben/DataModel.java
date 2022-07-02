/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokoben;

/**
 *
 * @author Gio
 */
class DataModel {
    int item_id, item_price, item_stock;
    String item_name, item_desc;
    
    public DataModel (int item_id, String item_name, int item_price, int item_stock, String item_desc ) {
    this.item_id = item_id;
    this.item_name = item_name;
    this.item_price = item_price;
    this.item_stock = item_stock;
    this.item_desc = item_desc;
    }
    
    public int getitem_id(){
        return item_id;
    }
    
    public String getitem_name(){
        return item_name;
    }
    
    public int getitem_price(){
        return item_price;
    }
    
    public int getitem_stock(){
        return item_stock;
    }
    
    public String getitem_desc(){
        return item_desc;
    }
    
}
