package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Invoice extends Model {
    public String reference;
    public Double amount;
    @ManyToOne(optional = false)
    public Customer customer;

    @Override
    public String toString() {
        return id+"-"+reference;
    }

    // Cette méthode n'est pas nécessaire, je la créé pour la démo seulement
    // il est possible de faire la requete dans le controller directement



    public static List<Invoice> findByCustomer(Long id) {
        Customer customer=Customer.findById(id);
        if(customer==null){
            return new ArrayList<Invoice>();
        }
        List<Invoice> listOfInvoices=Invoice.find("byCustomer",customer).fetch();

        return listOfInvoices;
    }

}
