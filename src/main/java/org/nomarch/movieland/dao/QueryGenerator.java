package org.nomarch.movieland.dao;

import org.nomarch.movieland.dto.movie.MovieDTO;


public class QueryGenerator {

    public static String formMovieUpdateQuery(MovieDTO updatedMovie) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE movies SET ");


        if (updatedMovie.getNameNative() != null) {
            queryBuilder.append("name_native = '");
            queryBuilder.append(updatedMovie.getNameNative());
            queryBuilder.append("', ");
        }
        if (updatedMovie.getNameRussian() != null) {
            queryBuilder.append("name_russian = '");
            queryBuilder.append(updatedMovie.getNameRussian());
            queryBuilder.append("', ");
        }
        if (updatedMovie.getYearOfRelease() != null) {
            queryBuilder.append("year = ");
            queryBuilder.append(updatedMovie.getYearOfRelease());
            queryBuilder.append(", ");
        }
        if (updatedMovie.getDescription() != null) {
            queryBuilder.append("description = '");
            queryBuilder.append(updatedMovie.getDescription());
            queryBuilder.append("', ");
        }
        if (updatedMovie.getPrice() != null) {
            queryBuilder.append("price = ");
            queryBuilder.append(updatedMovie.getPrice());
            queryBuilder.append(", ");
        }
        if (updatedMovie.getPicturePath() != null) {
            queryBuilder.append("poster_img = '");
            queryBuilder.append(updatedMovie.getPicturePath());
            queryBuilder.append("', ");
        }

        queryBuilder.deleteCharAt(queryBuilder.lastIndexOf(","));
        queryBuilder.append("WHERE id = ?");

        return queryBuilder.toString();
    }
}
