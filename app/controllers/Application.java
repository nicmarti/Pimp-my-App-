package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        long numberOfEmployees = Employee.count();
        render(numberOfEmployees);
    }

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


    public static void show(Long id) {
        Employee employee = Employee.findById(id);
        render(employee);
    }

    public static void button(){
        render();
    }

    public static void listFilter(){
        List<Employee> listOfEmployees=Employee.findAll();
        render(listOfEmployees);
    }


    public static void niceForm(){
        render();
    }

    public static void create(Employee employee){
        niceForm();
    }

    public static void demoHTML5(){
        render();
    }
}
