package org.codesignal.debugging.hashsets;

import java.util.*;

public class RecommendMoviesSoln {

    public int[] recommendMovies(int[] userHistory, int[] popularMovies, int[] unpopularMovies) {
        // TODO: implement the method to recommend movies.
        Set<Integer> popularMoviesSet = new HashSet<>();
        for (int movie : popularMovies) {
            popularMoviesSet.add(movie);
        }
        Set<Integer> userHistorySet = new HashSet<>();
        for (int movie : userHistory) {
            userHistorySet.add(movie);
        }
        Set<Integer> unpopularMoviesSet = new HashSet<>();
        for (int movie : unpopularMovies) {
            unpopularMoviesSet.add(movie);
        }
        List<Integer> recommendMovies = new ArrayList<>();
        for (int movie : popularMoviesSet) {
            if (!userHistorySet.contains(movie) && !unpopularMoviesSet.contains(movie)) {
                recommendMovies.add(movie);
            }
        }
        Collections.sort(recommendMovies);

        return recommendMovies.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        RecommendMoviesSoln recommendMoviesSoln = new RecommendMoviesSoln();
        int[] userHistory = {1, 2, 3};
        int[] popularMovies = {2, 3, 4, 5};
        int[] unpopularMovies = {5, 6};
        int[] recommended = recommendMoviesSoln.recommendMovies(userHistory, popularMovies, unpopularMovies);
        System.out.println(Arrays.toString(recommended)); // Output: [4]
    }

}
