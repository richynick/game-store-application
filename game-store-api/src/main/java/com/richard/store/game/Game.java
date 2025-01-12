package com.richard.store.game;

import com.richard.store.category.Category;
import com.richard.store.comment.Comment;
import com.richard.store.common.BaseEntity;
import com.richard.store.wishlist.WishList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game extends BaseEntity {

    private String title;
    @Enumerated(EnumType.STRING)
    private SupportedPlatforms supportedPlatforms;
    private String coverPicture;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "game")
    @OrderBy(value = "content")
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "game_whislist",
            joinColumns = {
                    @JoinColumn(
                            name="game_id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "wishlist_id"),
            }
    )
    private List<WishList> wishlists;

    public void addWishlist(WishList wishList){
        this.wishlists.add(wishList);
        wishList.getGames().add(this);
    }
    public void removeWishlist(WishList wishList){
        this.wishlists.remove(wishList);
        wishList.getGames().remove(this);
    }

}
