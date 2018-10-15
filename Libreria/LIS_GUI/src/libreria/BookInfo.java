/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

import java.io.Serializable;
import java.util.ArrayList;

class BookInfo implements Serializable{
//    private int ID;
    private boolean isIssued;
    private ArrayList<BookCopy> issuedMembers=new ArrayList<BookCopy>();

    public BookInfo(boolean isIssued) {
//        this.ID = ID;
        this.isIssued = isIssued;
        issuedMembers=new ArrayList<>();
    }

//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }

    public boolean isIsIssued() {
        return isIssued;
    }

    public void setIsIssued(boolean isIssued) {
        this.isIssued = isIssued;
    }

    public ArrayList<BookCopy> getIssuedMembers() {
        return issuedMembers;
    }

    public void setIssuedMembers(ArrayList<BookCopy> issuedMembers) {
        this.issuedMembers = issuedMembers;
    }    
    
}
