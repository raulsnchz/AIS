package es.codeurjc.web.nitflex.dto.user;

import es.codeurjc.web.nitflex.dto.film.FilmDTO;
import es.codeurjc.web.nitflex.dto.review.ReviewDTO;
import es.codeurjc.web.nitflex.dto.review.ReviewSimpleDTO;
import es.codeurjc.web.nitflex.model.Film;
import es.codeurjc.web.nitflex.model.Review;
import es.codeurjc.web.nitflex.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T10:00:08+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        List<FilmDTO> favoriteFilms = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        favoriteFilms = filmListToFilmDTOList( user.getFavoriteFilms() );

        List<ReviewDTO> reviews = null;

        UserDTO userDTO = new UserDTO( id, name, email, favoriteFilms, reviews );

        return userDTO;
    }

    @Override
    public List<UserDTO> toDTOs(Collection<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public User toDomain(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        if ( userDTO.id() != null ) {
            user.setId( userDTO.id() );
        }
        user.setName( userDTO.name() );
        user.setEmail( userDTO.email() );
        user.setFavoriteFilms( filmDTOListToFilmList( userDTO.favoriteFilms() ) );

        return user;
    }

    protected UserSimpleDTO userToUserSimpleDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();

        UserSimpleDTO userSimpleDTO = new UserSimpleDTO( id, name, email );

        return userSimpleDTO;
    }

    protected ReviewSimpleDTO reviewToReviewSimpleDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        Long id = null;
        String text = null;
        int score = 0;
        UserSimpleDTO user = null;

        id = review.getId();
        text = review.getText();
        score = review.getScore();
        user = userToUserSimpleDTO( review.getUser() );

        Date created_at = null;

        ReviewSimpleDTO reviewSimpleDTO = new ReviewSimpleDTO( id, text, score, created_at, user );

        return reviewSimpleDTO;
    }

    protected List<ReviewSimpleDTO> reviewListToReviewSimpleDTOList(List<Review> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewSimpleDTO> list1 = new ArrayList<ReviewSimpleDTO>( list.size() );
        for ( Review review : list ) {
            list1.add( reviewToReviewSimpleDTO( review ) );
        }

        return list1;
    }

    protected List<UserSimpleDTO> userListToUserSimpleDTOList(List<User> list) {
        if ( list == null ) {
            return null;
        }

        List<UserSimpleDTO> list1 = new ArrayList<UserSimpleDTO>( list.size() );
        for ( User user : list ) {
            list1.add( userToUserSimpleDTO( user ) );
        }

        return list1;
    }

    protected FilmDTO filmToFilmDTO(Film film) {
        if ( film == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String synopsis = null;
        int releaseYear = 0;
        String ageRating = null;
        List<ReviewSimpleDTO> reviews = null;
        List<UserSimpleDTO> usersThatLiked = null;

        id = film.getId();
        title = film.getTitle();
        synopsis = film.getSynopsis();
        releaseYear = film.getReleaseYear();
        ageRating = film.getAgeRating();
        reviews = reviewListToReviewSimpleDTOList( film.getReviews() );
        usersThatLiked = userListToUserSimpleDTOList( film.getUsersThatLiked() );

        FilmDTO filmDTO = new FilmDTO( id, title, synopsis, releaseYear, ageRating, reviews, usersThatLiked );

        return filmDTO;
    }

    protected List<FilmDTO> filmListToFilmDTOList(List<Film> list) {
        if ( list == null ) {
            return null;
        }

        List<FilmDTO> list1 = new ArrayList<FilmDTO>( list.size() );
        for ( Film film : list ) {
            list1.add( filmToFilmDTO( film ) );
        }

        return list1;
    }

    protected User userSimpleDTOToUser(UserSimpleDTO userSimpleDTO) {
        if ( userSimpleDTO == null ) {
            return null;
        }

        User user = new User();

        if ( userSimpleDTO.id() != null ) {
            user.setId( userSimpleDTO.id() );
        }
        user.setName( userSimpleDTO.name() );
        user.setEmail( userSimpleDTO.email() );

        return user;
    }

    protected Review reviewSimpleDTOToReview(ReviewSimpleDTO reviewSimpleDTO) {
        if ( reviewSimpleDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setId( reviewSimpleDTO.id() );
        review.setText( reviewSimpleDTO.text() );
        review.setScore( reviewSimpleDTO.score() );
        review.setUser( userSimpleDTOToUser( reviewSimpleDTO.user() ) );

        return review;
    }

    protected List<Review> reviewSimpleDTOListToReviewList(List<ReviewSimpleDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Review> list1 = new ArrayList<Review>( list.size() );
        for ( ReviewSimpleDTO reviewSimpleDTO : list ) {
            list1.add( reviewSimpleDTOToReview( reviewSimpleDTO ) );
        }

        return list1;
    }

    protected List<User> userSimpleDTOListToUserList(List<UserSimpleDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<User> list1 = new ArrayList<User>( list.size() );
        for ( UserSimpleDTO userSimpleDTO : list ) {
            list1.add( userSimpleDTOToUser( userSimpleDTO ) );
        }

        return list1;
    }

    protected Film filmDTOToFilm(FilmDTO filmDTO) {
        if ( filmDTO == null ) {
            return null;
        }

        Film film = new Film();

        film.setId( filmDTO.id() );
        film.setTitle( filmDTO.title() );
        film.setSynopsis( filmDTO.synopsis() );
        film.setReleaseYear( filmDTO.releaseYear() );
        film.setAgeRating( filmDTO.ageRating() );
        if ( film.getReviews() != null ) {
            List<Review> list = reviewSimpleDTOListToReviewList( filmDTO.reviews() );
            if ( list != null ) {
                film.getReviews().addAll( list );
            }
        }
        if ( film.getUsersThatLiked() != null ) {
            List<User> list1 = userSimpleDTOListToUserList( filmDTO.usersThatLiked() );
            if ( list1 != null ) {
                film.getUsersThatLiked().addAll( list1 );
            }
        }

        return film;
    }

    protected List<Film> filmDTOListToFilmList(List<FilmDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Film> list1 = new ArrayList<Film>( list.size() );
        for ( FilmDTO filmDTO : list ) {
            list1.add( filmDTOToFilm( filmDTO ) );
        }

        return list1;
    }
}
