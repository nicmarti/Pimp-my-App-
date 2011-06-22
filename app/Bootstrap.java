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

import models.BigTable;
import models.BigTableR;
import models.Employee;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.util.Date;

/**
 * Loads a sample into the database
 *
 * @author Nicolas Martignole
 * @since 06/03/11 14:55
 */
@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        if (Play.mode == Play.Mode.DEV) {
            if (Employee.count() == 0) {
                Fixtures.load("test-datas.yml");
            }

            // BigTable.deleteAll();
            // BigTableR.deleteAll();

            int maxEntries = 2500;
            int maxEntriesR = 500;


            if (BigTable.count() == 0) {
                Logger.info("Creating " + maxEntries + " entries for testing");

                BigTable bigTable;
                DateMidnight aDate = new DateMidnight().minusYears(2);

                for (int i = 0; i < maxEntries; i++) {
                    bigTable = new BigTable();
                    bigTable.variable01 = Math.random() * 100;
                    bigTable.variable02 = Math.random() * 100;
                    bigTable.lastUpdated = aDate.toDate();
                    if (i % 100 == 0) {
                        aDate = aDate.plusDays(1);
                        Logger.info("Created " + i + " entries");
                    }
                    bigTable.save();
                }
            }
            if (BigTableR.count() == 0) {
                Logger.info("Creating " + maxEntriesR + " entries for BigTableR");

                BigTableR bigTableR;

                int j = 0;
                String locCurs = "Localisation #0";
                String segCurs = "Segment #0";
                String perCurs = "Period #0";

                for (int i = 0; i < maxEntriesR; i++) {
                    bigTableR = new BigTableR();
                    bigTableR.difference = Math.random();
                    bigTableR.evolution = Math.random() * 10;
                    bigTableR.localisation = locCurs;
                    bigTableR.segment = segCurs;
                    bigTableR.period = perCurs;
                    bigTableR.indicator = new StringBuffer("Indicator #").append(i).toString();
                    bigTableR.product = new Long(10 * i);
                    if (i % 100 == 0) {
                        Logger.info("Created " + i + " entries");
                        locCurs = new StringBuffer("Localisation ").append(j).toString();
                        segCurs = new StringBuffer("Segment #").append(j).toString();
                        perCurs = new StringBuffer("Period #").append(j).toString();
                        j++;
                    }
                    bigTableR.save();
                }

            }
        }
    }

}