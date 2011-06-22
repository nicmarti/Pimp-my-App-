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

import models.BigTable;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

/**
 * A long-polling controller to demonstrate Comet capabilities.
 *
 * @author Nicolas Martignole
 * @since 16/03/11 17:35
 */
public class AsyncBlotter extends Controller {


    /**
     * Selectionne 10 entrées où id < 15, puis met à jour
     */
    public static void index(){
        List<BigTable> listOfData = BigTable.find("order by id").fetch(10);
        render(listOfData);
    }

    public static void getMessages(){
       List<BigTable> messages = BigTable.find("from BigTable r where r.lastUpdated > :lastVisit order by id")
               .bind("lastVisit",request.date)
               .fetch();

        // Play long-polling, the current HTTP Request is suspended
        if (messages.isEmpty()) {
            suspend("1s");
        }
        renderJSON(messages);
    }

    public static void updateLine(Long id, Double variable01, Double variable02){
        BigTable entry=BigTable.findById(id);
        if(entry==null){
            flash.error("Not found");
            index();
        }
        entry.variable01=variable01;
        entry.variable02=variable02;
        entry.lastUpdated=request.date;
        entry.save();

        List<BigTable> listOfData = BigTable.find("order by id").fetch(10);
        renderTemplate("AsyncBlotter/index.html",listOfData);
    }

    // Pour la demo update agg_r6 set part_var=100.0,pro_n1=210.21,date=now() where id < 10;

    public static void shuffle(){
        List<BigTable> listOfData = BigTable.find("order by id").fetch(10);
        for(BigTable bt:listOfData){
            bt.variable01=Math.random()*10;
            bt.variable02=Math.random()*10;
            bt.lastUpdated=new Date();
            bt.save();
        }
        index();
    }
}
