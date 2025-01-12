package com.richard.store.game;

import com.richard.store.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

     /*
    QueryByExampleExecutor LIMITATIONS
    1- Nesting and grouping statements are not supported
        => select * from game where (title = ?0 and supportedPlatforms = ?1) OR coverPicture is not null

    2- String matching only includes exact, case-sensitive, starts, ends, contains and regex
    3- All types other than String are exact-match only
     */

    public void specificationExample1() {

        Specification<Game> spec = buildSpecificationWithAndOperator("witcher", SupportedPlatforms.PC);
        List<Game> games = gameRepository.findAll(spec);

    }
    public void specificationExample2() {

        Specification<Game> spec = buildSpecificationWithOrOperator("witcher", SupportedPlatforms.PC);
        List<Game> games = gameRepository.findAll(spec);

    }

    private Specification<Game> buildSpecificationWithOrOperator(String title, SupportedPlatforms platform) {
        Specification<Game> spec = Specification.where(null);

        if (StringUtils.hasLength(title)) {
            spec = spec.and(GameSpecification.byGameTitle(title));

        }
        if (platform != null) {
            spec = spec.or(GameSpecification.bySupportedPlatform(platform));

        }


        return spec;
    }

    private Specification<Game> buildSpecificationWithAndOperator(String title, SupportedPlatforms platform) {
        Specification<Game> spec = Specification.where(null);

        if (StringUtils.hasLength(title)) {
            spec = spec.and(GameSpecification.byGameTitle(title));

        }
        if (platform != null) {
            spec = spec.and(GameSpecification.bySupportedPlatform(platform));

        }


        return spec;
    }

    // 1- class (GameRepresentation1) (id, title, platforms)
    // 2- call the game repository and fetch all the games (paged)
    // 3- map the result (loop over the result from the DB, do the mapping, collect, return the result)

    public List<GameRepresentation1> getGamesWithRep1() {
        return gameRepository.findAllGames();
    }


}
