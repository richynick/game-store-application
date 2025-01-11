package com.richard.store.category;

import com.richard.store.common.BaseEntity;
import com.richard.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "Category.namedQueryFindByName",
                query = "SELECT c FROM Category c " +
                        "WHERE c.name LIKE lower(:catName) " +
                        "ORDER BY c.name ASC")
})
public class Category extends BaseEntity {

    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Game> games;
}
