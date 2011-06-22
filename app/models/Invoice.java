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

    public static List<Invoice> findByCustomerLike(String q) {
        if(q==null){
            return new ArrayList<Invoice>();
        }
        List<Invoice> invoices=Invoice.find("from Invoice i where i.customer.name like :p1").bind("p1","%"+q.trim()+"%").fetch();
        return invoices;
    }
}
