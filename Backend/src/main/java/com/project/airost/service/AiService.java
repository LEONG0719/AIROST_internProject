package com.project.airost.service;

import java.util.Map;

public interface AiService {

    /**
     * Compare two images (URLs or base64) and return a similarity score 0..100
     *
     * @param imageA first image URL/base64
     * @param imageB second image URL/base64
     * @return similarity score from 0 to 100
     */
    double compareImages(String imageA, String imageB);

    /**
     * Compare two text descriptions and return a similarity score 0..100
     *
     * @param textA first text
     * @param textB second text
     * @return similarity score from 0 to 100
     */
    double compareText(String textA, String textB);

    /**
     * Compare one image against many others (batch comparison)
     *
     * @param imageUrl the base image URL/base64
     * @param otherImageUrls iterable of other image URLs/base64
     * @return a map of other image URL -> similarity score
     */
    Map<String, Double> compareImageAgainstMany(String imageUrl, Iterable<String> otherImageUrls);
}



