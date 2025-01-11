package com.richard.store.category;

import com.richard.store.common.BaseEntity;
import com.richard.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {

    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Game> games;
}
