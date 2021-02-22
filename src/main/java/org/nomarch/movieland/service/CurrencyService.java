package org.nomarch.movieland.service;

import org.nomarch.movieland.common.CurrencyCode;

public interface CurrencyService {

    double convert(CurrencyCode from, CurrencyCode to, Double amount);
}
