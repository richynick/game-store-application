package com.richard.store.wishlist;

import com.richard.store.common.BaseEntity;
import com.richard.store.game.Game;
import com.richard.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WishList extends BaseEntity {

    private String name;

    @OneToOne
    private User user;
    @ManyToMany(mappedBy = "wishlists", fetch = FetchType.EAGER)
    private List<Game> games;

}
