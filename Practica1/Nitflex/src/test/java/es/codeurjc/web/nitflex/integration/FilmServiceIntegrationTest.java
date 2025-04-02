package es.codeurjc.web.nitflex.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.web.nitflex.ImageTestUtils;
import es.codeurjc.web.nitflex.utils.ImageUtils;
import es.codeurjc.web.nitflex.dto.film.CreateFilmRequest;
import es.codeurjc.web.nitflex.dto.film.FilmDTO;
import es.codeurjc.web.nitflex.dto.film.FilmMapper;
import es.codeurjc.web.nitflex.dto.film.FilmSimpleDTO;
import es.codeurjc.web.nitflex.service.FilmService;

@SpringBootTest
public class FilmServiceIntegrationTest {

    @Autowired
    private FilmService filmService;
    FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    @Test
    public void testAddFilmWithValidTitle() {
        CreateFilmRequest request = new CreateFilmRequest("Matrix", "Un clasico", 1999, "R");

        FilmDTO result = filmService.save(request);

        assertNotNull(result);
        assertEquals("Matrix", result.title());
        assertNotNull(result.id());
    }

    @Test
    @Transactional
    public void testUpdateFilmTitleAndSynopsis() throws SQLException, IOException {
        CreateFilmRequest createRequest = new CreateFilmRequest("Interstellar", "Un viaje por el espacio", 2014, "PG-13");

        MultipartFile imageFile = ImageTestUtils.createSampleImage();
        ImageUtils image = new ImageUtils();
        Blob imageBlob = image.multiPartFileImageToBlob(imageFile);

        FilmDTO createdFilm = filmService.save(createRequest, imageBlob);

        Blob originalImage = filmMapper.toDomain(filmService.findOne(createdFilm.id()).get()).getPosterFile();

        CreateFilmRequest updateRequest = new CreateFilmRequest("Interstellar: Una nueva perspectiva", "Una revolución en la ciencia y la tecnología", 2014, "PG-13");
        FilmSimpleDTO updatedFilm = new FilmSimpleDTO(
            createdFilm.id(),
            updateRequest.title(), 
            updateRequest.synopsis(),
            updateRequest.releaseYear(), 
            updateRequest.ageRating());
        
        filmService.save(updateRequest);

        Blob updatedImage = filmMapper.toDomain(filmService.findOne(updatedFilm.id()).get()).getPosterFile();

        assertTrue(ImageTestUtils.areSameBlob(originalImage, updatedImage),"La imagen no deberia cambiar");
        
    }
}