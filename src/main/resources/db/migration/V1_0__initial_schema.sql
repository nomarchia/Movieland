CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(200),
    email VARCHAR(100),
    password VARCHAR(40)
);

CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
    );

CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    name_native VARCHAR(200) NOT NULL,
    name_russian VARCHAR(200) NOT NULL,
    country VARCHAR(120) NOT NULL,
    year BIGINT NOT NULL,
    description TEXT NOT NULL,
    rating DOUBLE PRECISION NOT NULL,
    price DOUBLE PRECISION  NOT NULL,
    poster_img VARCHAR
);

CREATE TABLE movie_to_genre (
    column_id SERIAL PRIMARY KEY,
    movie_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    CONSTRAINT FK_movie_id FOREIGN KEY (movie_id) REFERENCES movies(id),
    CONSTRAINT FK_genre_id FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    movie_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    review TEXT,
    CONSTRAINT FK_movie_id FOREIGN KEY (movie_id) REFERENCES movies(id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);



