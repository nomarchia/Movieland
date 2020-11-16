package org.nomarch.movieland.dao.jdbc;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.MainApplicationContext;
import org.nomarch.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig (MainApplicationContext.class)
class JdbcMovieDaoITest {
    @Autowired
    private JdbcMovieDao jdbcMovieDao;

    @DisplayName("Get all movies from DB")
    @Test
    void testGetAll() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(1)
                .name("Побег из Шоушенка/The Shawshank Redemption")
                .country("США")
                .year(1994)
                .description("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.")
                .rating(8.9)
                .price(123.45)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        Movie expectedMovieLast = Movie.builder()
                .id(25)
                .name("Танцующий с волками/Dances with Wolves")
                .country("США, Великобритания")
                .year(1990)
                .description("Действие фильма происходит в США во времена Гражданской войны. Лейтенант американской армии Джон Данбар после ранения в бою просит перевести его на новое место службы ближе к западной границе США. Место его службы отдалённый маленький форт. Непосредственный его командир покончил жизнь самоубийством, а попутчик Данбара погиб в стычке с индейцами после того, как довез героя до места назначения. Людей, знающих, что Данбар остался один в форте и должен выжить в условиях суровой природы, и в соседстве с кажущимися негостеприимными коренными обитателями Северной Америки, просто не осталось. Казалось, он покинут всеми. Постепенно лейтенант осваивается, он ведет записи в дневнике…")
                .rating(8.00)
                .price(120.55)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        //when
        List<Movie> actualMovies = jdbcMovieDao.getAll();
        actualMovies.sort(new SortMovieById());
        //then
        assertEquals(25, actualMovies.size());
        Assert.assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieLast).matches(actualMovies.get(24)));
    }

    @DisplayName("Get three random movies")
    @Test
    void getThreeRandomMovies() {
        List<Movie> actualMovies = jdbcMovieDao.getThreeRandomMovies();

        assertEquals(3, actualMovies.size());
    }

    @Test
    void testGetMoviesByGenre() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(3)
                .name("Форрест Гамп/Forrest Gump")
                .country("США")
                .year(1994)
                .description("От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни.Фантастическим образом превращается он в известного футболиста, героя войны, преуспевающего бизнесмена. Он становится миллиардером, но остается таким же бесхитростным, глупым и добрым. Форреста ждет постоянный успех во всем, а он любит девочку, с которой дружил в детстве, но взаимность приходит слишком поздно.")
                .rating(8.6)
                .price(200.60)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        Movie expectedMovieSecond = Movie.builder()
                .id(7)
                .name("Жизнь прекрасна/La vita è bella")
                .country("Италия")
                .year(1997)
                .description("Во время II Мировой войны в Италии в концлагерь были отправлены евреи, отец и его маленький сын. Жена, итальянка, добровольно последовала вслед за ними. В лагере отец сказал сыну, что все происходящее вокруг является очень большой игрой за приз в настоящий танк, который достанется тому мальчику, который сможет не попасться на глаза надзирателям. Он сделал все, чтобы сын поверил в игру и остался жив, прячась в бараке.")
                .rating(8.2)
                .price(145.99)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        Movie expectedMovieThird = Movie.builder()
                .id(12)
                .name("Титаник/Titanic")
                .country("США")
                .year(1997)
                .description("Молодые влюбленные Джек и Роза находят друг друга в первом и последнем плавании «непотопляемого» Титаника. Они не могли знать, что шикарный лайнер столкнется с айсбергом в холодных водах Северной Атлантики, и их страстная любовь превратится в схватку со смертью…")
                .rating(7.9)
                .price(150.00)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();

        //when
        List<Movie> actualMovies = jdbcMovieDao.getMoviesByGenre(5);

        //then
        actualMovies.sort(new SortMovieById());

        assertEquals(actualMovies.size(), 3);

        Assert.assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieSecond).matches(actualMovies.get(1)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieThird).matches(actualMovies.get(2)));
    }

    class SortMovieById implements Comparator<Movie>
    {
        public int compare(Movie a, Movie b)
        {
            return a.getId() - b.getId();
        }
    }
}