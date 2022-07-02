/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokoben;

/**
 *
 * @author Gio
 */
class tr_DataModel {
    int master_transaction_id, item_amount, item_price;
    String name, address, telp, status;
    
    public tr_DataModel(int master_transaction_id, String name, String address, String telp, int item_amount, int item_price, String status){
    this.master_transaction_id = master_transaction_id;
    this.name = name;
    this.address = address;
    this.telp = telp;
    this.item_amount = item_amount;
    this.item_price = item_price;
    this.status = status;
    }
    
    public int getmaster_transaction_id(){
        return master_transaction_id;
    }
    
    public String getname(){
        return name;
    }
    
    public String getaddress(){
        return address;
    }
    
    public String gettelp(){
        return telp;
    }
    
    public int getitem_amount(){
        return item_amount;
    }
    public int getitem_price(){
        return item_price;
    }
    public String getstatus(){
        return status;
    }
    
}
