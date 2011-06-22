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

import javax.persistence.*;
import java.util.Set;

@Entity
public class Hierarchy extends Model {
    public Integer category;
    public String label;

    // Create a relationship to children. For the Cache, we use FetchType EAGER to load all the children
    // and store them with this Hierarchy instance in the cache.
    @OneToMany(mappedBy = "parent", targetEntity = Hierarchy.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @OrderBy("label")
    public Set<Hierarchy> children;

    public Integer level;

    @ManyToOne
    public Hierarchy parent;

    /**
     * Loads the specified Hierarchy from DB or from Cache, if the Hierarchy
     * was loaded less than 10 mn ago
     *
     * @param id is the Hiearchy PK
     * @return the Hierarchy or null if it was not found
     */
    public static Hierarchy findByIdAndCache(Long id) {
        if (id == null) return null;
        Hierarchy hierarchy;
        hierarchy = Hierarchy.findById(id);
        if (hierarchy == null) {
            return null;
        }
        return hierarchy;
    }

}
