package com.richard.store.game;

import com.richard.store.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public PageResponse<Game> pageResult(final int page, final int size) {

        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(
                        Sort.Direction.DESC,
                        "title",
                        "createTime"
                )
                );

        Page<Game> pageResult = gameRepository.findAllByCategoryName("anyCat", pageable);

        return PageResponse.<Game>builder()
                .content(pageResult.getContent())
                .totalPages(pageResult.getTotalPages())
                .totalElements(pageResult.getNumberOfElements())
                .isFirst(pageResult.isFirst())
                .isLast(pageResult.isLast())
                .build();
    }

    public void queryByExampleCaseSensitive() {

        Game game = new Game();
        game.setTitle("The witcher III"); // iny my DB --> the Witcher iii
        game.setSupportedPlatforms(SupportedPlatforms.PS);

        Example<Game> example = Example.of(game);

        Optional<Game> myGame = gameRepository.findOne(example);
    }

    public void queryByExampleCaseInsensitive() {

        Game game = new Game();
        game.setTitle("The witcher III"); // iny my DB --> the Witcher iii
        game.setSupportedPlatforms(SupportedPlatforms.PS);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();

        Example<Game> example = Example.of(game, matcher);

        Optional<Game> myGame = gameRepository.findOne(example);
    }

    public void queryByExampleCustomMatching() {

        Game game = new Game();
        game.setTitle("witcher"); // iny my DB --> the Witcher iii
        game.setSupportedPlatforms(SupportedPlatforms.PS);

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("supportedPlatforms", ExampleMatcher.GenericPropertyMatchers.exact());

        Example<Game> example = Example.of(game, matcher);

        // the output query
        /*
            select * from game
            where  lower(title) like '%witcher%'
            and supportedPlatforms = 'PS'
         */

        List<Game> myGame = gameRepository.findAll(example);
    }

    public void queryByExampleIgnoringProperties() {
        Game game = new Game();
        game.setTitle("witcher"); // iny my DB --> the Witcher iii

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withIgnorePaths("supportedPlatforms", "coverPicture");

        Example<Game> example = Example.of(game, matcher);
        List<Game> myGame = gameRepository.findAll(example);

    }

}
