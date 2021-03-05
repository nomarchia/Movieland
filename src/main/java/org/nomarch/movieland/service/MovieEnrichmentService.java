package org.nomarch.movieland.service;

import org.nomarch.movieland.dto.ReturnedEntity;

import java.util.List;
import java.util.Map;

public interface MovieEnrichmentService {
    Map<String, List<ReturnedEntity>> enrichDependantEntities(Long movieId);
}
