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
        List<BigTable> listOfData = BigTable.find("id < ?", 15L).fetch(10);
         for(BigTable m:listOfData){
            m.variable01 =Math.random()*10;
            m.variable02 =Math.random()*10;
            m.date=new Date();
            m.save();
        }
        render(listOfData);
    }

    public static void getMessages(){
       List<BigTable> messages = BigTable.find("from BigTable r where r.id<15 and r.date > :lastVisit ")
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
        entry.date=new Date();
        entry.save();

        List<BigTable> listOfData = BigTable.find("id < ?", 15L).fetch(10);
        renderTemplate("AsyncBlotter/index.html",listOfData);
    }

    // Pour la demo update agg_r6 set part_var=100.0,pro_n1=210.21,date=now() where id < 10;

}
