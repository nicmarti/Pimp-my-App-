package controllers;

import models.BigTableR;
import models.BigTable_R6;
import play.mvc.Controller;
import play.templates.JavaExtensions;

import java.util.List;

public class BigPage extends Controller {

    public static void index(Integer page, Integer pageSize) {
        if (pageSize == null) {
            flash.success("Affichage des 10 premiers résultats");
            pageSize = 10;
        }
        if (page == null) {
            page = 0;
        }
        Integer current = page;
        Long count = BigTableR.count();
        List<BigTableR> bigList = BigTableR.all().fetch(page, pageSize);

        Long totalPages;

        if (pageSize >= count) {
            totalPages = 1L;
        } else {
            totalPages = (count / pageSize);
        }
        render(bigList, count, current, totalPages, page, pageSize);
    }

    public static void loadTable() {
        render();
    }

    public static void yahooTableSorted() {
        List<BigTableR> bigList = BigTableR.all().fetch(3, 50);
        Long count = 500L;
        render(bigList, count);
    }


    public static void yahooChart() {
        List<BigTableR> bigList = BigTableR.find("from BigTableR B where B.localisation='Alsace' order by Period").fetch(50);
        Long count = 500L;
        render(bigList, count);
    }

    public static void autocomplete() {
        render();
    }

    public static void search(String q) {
        List<String> results;
        if (q == null) {
            results = BigTable_R6.find("select distinct localisation from BigTable_R6 order by localisation").fetch(100);
        } else {
            results = BigTable_R6.find("select distinct localisation from BigTable_R6 where localisation like :ploc order by localisation")
                    .bind("ploc", JavaExtensions.camelCase(q.trim())+"%")
                    .fetch();
        }
        renderJSON(results);
    }
}
