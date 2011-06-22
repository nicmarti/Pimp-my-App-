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

import models.BigTableR;
import models.BigTable;
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
        List<BigTableR> bigList = BigTableR.find("from BigTableR B where B.localisation='Localisation 0' order by product").fetch(50);
        Long count = 500L;
        render(bigList, count);
    }

    public static void autocomplete() {
        render();
    }

    public static void search(Long q) {
        List<String> results;
        if (q == null) {
            results = BigTable.find("select distinct product from BigTableR order by product").fetch(100);
        } else {
            results = BigTable.find("select distinct product from BigTableR where product > :ploc order by product")
                    .bind("ploc", q)
                    .fetch();
        }
        renderJSON(results);
    }
}
