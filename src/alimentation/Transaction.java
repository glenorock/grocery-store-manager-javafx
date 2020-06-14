/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alimentation;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author jhy
 */
public interface Transaction {
    public double trans();
    public Date getDate();
    public static Comparator<Transaction> sortByDate = new Comparator<Transaction>() {
        @Override
        public int compare(Transaction o1, Transaction o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };
    public static Comparator<Transaction> sortByMostRecent = new Comparator<Transaction>() {
        @Override
        public int compare(Transaction o1, Transaction o2) {
            return o2.getDate().compareTo(o1.getDate());
        }
    };
    
    public Integer getId();
}
