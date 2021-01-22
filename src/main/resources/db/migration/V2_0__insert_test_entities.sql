INSERT INTO genres (name) VALUES
    ('драма'),
    ('криминал'),
    ('фэнтези'),
    ('детектив'),
    ('мелодрама');

INSERT INTO countries (name) VALUES
    ('США'),
    ('Великобритания'),
    ('Италия');

INSERT INTO movies (name_native, name_russian, year, description, rating, price, poster_img) VALUES
    ('The Shawshank Redemption', 'Побег из Шоушенка', 1994, 'a', 8.9, 123.45,'image1.jpg'),
    ('Forrest Gump', 'Форрест Гамп', 1994, 'b', 8.6, 200.60,'image2.jpg'),
    ('La vita è bella', 'Жизнь прекрасна', 1997, 'c', 8.2, 145.99,'image3.jpg'),
    ('Titanic', 'Титаник', 1997, 'd', 7.9, 150.00,'image4.jpg'),
    ('Dances with Wolves', 'Танцующий с волками', 1990, 'e', 8.00, 120.55,'image5.jpg');

INSERT INTO movie_to_genre (movie_id, genre_id) VALUES
    (1,1), (2,5), (3,5), (4,5), (5,1);

INSERT INTO movie_to_country (movie_id, country_id) VALUES
    (1,1), (2,2), (3,2), (4,3), (5,1), (5,3);
