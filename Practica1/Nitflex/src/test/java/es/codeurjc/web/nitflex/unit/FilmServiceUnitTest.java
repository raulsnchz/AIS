package es.codeurjc.web.nitflex.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.codeurjc.web.nitflex.dto.film.CreateFilmRequest;
import es.codeurjc.web.nitflex.dto.film.FilmDTO;
import es.codeurjc.web.nitflex.model.Film;
import es.codeurjc.web.nitflex.repository.FilmRepository;
import es.codeurjc.web.nitflex.service.FilmService;
import es.codeurjc.web.nitflex.service.exceptions.FilmNotFoundException;
import es.codeurjc.web.nitflex.dto.film.FilmMapper;

public class FilmServiceUnitTest {

    private FilmRepository filmRepository;

    private FilmMapper filmMapper;

    private FilmService filmService;

    @BeforeEach
    public void init(){
        filmRepository = mock(FilmRepository.class);
        filmMapper = mock(FilmMapper.class);
        filmService = new FilmService(filmRepository, null,null,filmMapper);
    }

    @Test
    public void testSaveFilmWithValidTitle() {
        CreateFilmRequest request = new CreateFilmRequest("Terminator", "En el año 2029 las máquinas dominan el mundo", 1984, "PG-18");
        Film film = new Film();
        film.setTitle(request.title());
        film.setSynopsis(request.synopsis());
        film.setReleaseYear(request.releaseYear());
        film.setAgeRating(request.ageRating());

        when(filmMapper.toDomain(request)).thenReturn(film);

        when(filmRepository.save(any(Film.class))).thenReturn(film);

        FilmDTO filmDTO = new FilmDTO(1L, "Terminator", "En el año 2029 las máquinas dominan el mundo", 1984, "PG-18", null, null);
        when(filmMapper.toDTO(film)).thenReturn(filmDTO);

        FilmDTO result = filmService.save(request);

        assertNotNull(result);
        assertEquals("Terminator", result.title());
        verify(filmRepository).save(any(Film.class));
    }

    @Test
    public void testDeleteNonExistentFilm() {
        long nonExistentFilmId = 999L;
        when(filmRepository.existsById(nonExistentFilmId)).thenReturn(false);

        assertThrows(FilmNotFoundException.class, () -> {
            filmService.delete(nonExistentFilmId);
        });

        verify(filmRepository, never()).deleteById(anyLong());
    }
}