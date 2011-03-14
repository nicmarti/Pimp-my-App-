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

    public static void showCustomer(Long id) {
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
}
