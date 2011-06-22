/*
 * Copyright (C) 2011 Nicolas Martignole
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package controllers;

import models.Customer;
import models.Invoice;
import play.libs.Codec;
import play.mvc.Controller;

import java.util.List;
import java.util.Random;

public class Demo extends Controller {
    public static void index() {
        List<Customer> listOfCustomers = Customer.findAll();
        render(listOfCustomers);
    }

    public static void showCustomer(Long id, String name) {
        Customer customer=Customer.findById(id);
        if(customer==null){
            flash.error("Customer non trouvé");
            index(); // termine l'execution de showCustomer
                     // donc la ligne en dessous n'est pas appelée
        }
        render(customer);
    }

    public static void showInvoicesForCustomer(Long id) {
        List<Invoice> listOfInvoices=Invoice.findByCustomer(id);
        render(listOfInvoices);
    }

    public static void persistNewCustomer(String name) {
        Customer c = new Customer();
        c.name = name;
        c.validateAndSave();
        if(validation.hasErrors()){
            flash.error("Erreur de validation");
            index();
        }
        flash.success("Customer sauvé");
        index();
    }

    public static void deleteCustomer(Long id) {
        Customer c = Customer.findById(id);
        if (c == null) {
            flash.success("Customer non trouvé");
            index();
        }
        c.delete();
        flash.success("Customer effacé");
        index();
    }


    public static void createInvoices() {

        List<Customer> customers=Customer.findAll();

        for(Customer c:customers){
            for(int i=0;i<10;i++){
                Invoice inv=new Invoice();
                inv.amount=Math.random()*1000;
                inv.customer=c;
                inv.reference= Codec.hexMD5(c.name+i);
                inv.save();
            }
        }


        flash.success("Factures de démonstration créées");
        index();
    }


    public static void indexDemo(){
       render();
    }
}
