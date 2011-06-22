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

import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.*;

import java.util.*;

import models.*;

/**
 * Main application controller.
 */
public class Application extends Controller {

    /**
     * Loads the index, returns the number of employees
     */
    public static void index() {
        render();
    }

    /**
     * Loads the list of employees in a table, add support for paginate.
     * @param page is the page number, can be null.
     */
    public static void employees(Integer page) {
        int itemPerPage = 3;
        List<Employee> employees = null;
        page = page != null ? page : 1;
        int current = page;
        employees = Employee.all().fetch(page, itemPerPage);
        List<Integer> pages = new ArrayList<Integer>();

        if (itemPerPage >= Employee.count()) {
            pages.add(1);
        } else {
            for (int i = 0; i < (Employee.count() % itemPerPage); i++) {
                pages.add(i + 1);
            }
        }
        int max = pages.size();
        render(employees, pages, current, max);

    }

    /**
     * Displays details for a specific employee
     * @param id is the primary key to load the employee.
     */
    public static void show(Long id) {
        Employee employee = Employee.findById(id);
        render(employee);
    }

    /**
     * Loads the css3 button page.
     * We can of course move the page to the static folder, we don't have
     * to create an action if the page content is static.
     */
    public static void button() {
        render();
    }

    /**
     * Loads the list of all employees.
     */
    public static void listFilter() {
        List<Employee> listOfEmployees = Employee.findAll();
        render(listOfEmployees);
    }


    /**
     * Same here : I can move the niceForm.html page to the public static folder dir.
     */
    public static void niceForm() {
        render();
    }

    /**
     * Creates a new employee, uses Play! Framework validation feature.
     * @param employee is a valid employee.
     */
    public static void create(@Valid Employee employee) {
        employee.validateAndCreate();
        if(Validation.hasErrors()){
            params.flash();
            validation.keep();
            niceForm();
        }
        flash.success("New employee created");
        niceForm();
    }

    public static void demoHTML5() {
        render();
    }

    /**
     * This is a private joke for French people.
     * The minitel is the old French Internet's like system we had between 1982 and 2011.
     * @param q
     */
    public static void minitel(String q) {
        List<Invoice> invoices = Invoice.findByCustomerLike(q);
        render(invoices);
    }
}
