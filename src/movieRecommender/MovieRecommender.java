package movieRecommender;

import java.io.*;
import java.util.HashMap;
import java.util.regex.*;

/** MovieRecommender. A class that is responsible for:
 - Reading movie and ratings data from the file and loading it in the data structure UsersList.
 *  - Computing movie recommendations for a given user and printing them to a file.
 *  - Computing movie "anti-recommendations" for a given user and printing them to file.
 *  Fill in code in methods of this class.
 *  Do not modify signatures of methods.
 */
public class MovieRecommender {
    private UsersList usersData; // linked list of users
    private HashMap<Integer, String> movieMap; // maps each movieId to the movie title


    public MovieRecommender() {
        movieMap = new HashMap<>();
        usersData = new UsersList();

    }

    /**
     * Read user ratings from the file and save data for each user in this list.
     * For each user, the ratings list will be sorted by rating (from largest to
     * smallest).
     * @param movieFilename name of the file with movie info
     * @param ratingsFilename name of the file with ratings info
     */
    /*public void loadData(String movieFilename, String ratingsFilename) {

        loadMovies(movieFilename);
        loadRatings(ratingsFilename);
    }*/

    public void loadData(String movieFilename, String ratingsFilename) {

        loadMovies(movieFilename);
        loadRatings(ratingsFilename);
    }

    /** Load information about movie ids and titles from the given file.
     *  Store information in a hashmap that maps each movie id to a movie title
     *
     * @param movieFilename csv file that contains movie information.
     *
     */
    private void loadMovies(String movieFilename)
    {
        // FILL IN CODE
        int movieId = 0;
        String title = "";
        try (FileReader f = new FileReader(movieFilename);
             BufferedReader br = new BufferedReader(f))
        {
            br.readLine();
            String line;
            //String [] splitlineFinal;

            while ((line = br.readLine()) != null)
            {

                String [] splitline = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                movieId = Integer.parseInt(splitline[0]);
                title = splitline[1];
//                String genres = splitline[2];

//                System.out.println(splitline[0] + " " + splitline[1] + " " + splitline[2]);
                movieMap.put(movieId, title);

            }

        }
        catch (IOException e) {
            System.out.println("No such file");
        }


//        System.out.println(movieMap);
    }



    /**
     * Load users' movie ratings from the file into UsersList
     * @param ratingsFilename name of the file that contains ratings
     */
    private void loadRatings(String ratingsFilename)
    {
        // FILL IN CODE
        int userId = 0;
        int movieId = 0;
        double rating = 0.0;


        try (FileReader f = new FileReader(ratingsFilename);
             BufferedReader br = new BufferedReader(f))
        {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] splitLine = line.split(",");


//                System.out.println(splitLine[2]);

                userId = Integer.parseInt(splitLine[0]);
                movieId = Integer.parseInt(splitLine[1]);
                rating = Double.parseDouble(splitLine[2]);

                usersData.insert(userId,movieId,rating);






            }
        }
        catch (IOException e) {
            System.out.println("No such file");
        }

//        usersData.printUserList();




    }

    /**
     * * Computes up to num movie recommendations for the user with the given user
     * id and prints these movie titles to the given file. First calls
     * findMostSimilarUser and then getFavoriteMovies(num) method on the
     * "most similar user" to get up to num recommendations. Prints movies that
     * the user with the given userId has not seen yet.
     * @param userid id of the user
     * @param num max number of recommendations
     * @param filename name of the file where to output recommended movie titles
     *                 Format of the file: one movie title per each line
     */
    public void findRecommendations(int userid, int num, String filename) {

        // compute similarity between userid and all the other users
        // find the most similar user and recommend movies that the most similar
        // user rated as 5.
        // Recommend only the movies that userid has not seen (has not
        // rated).
        // FILL IN CODE


        UserNode curr = usersData.findMostSimilarUser(userid);;
        int [] movies_similarUser = curr.getFavoriteMovies(num);

        for(int i = 0; i < num; i++)
        {
            if(!movieMap.containsKey(movies_similarUser[i]))
            {
                try (PrintWriter pw = new PrintWriter(filename)) {
                    for (Integer movie_recommendations: movies_similarUser) {
                        pw.println(movieMap.get(movie_recommendations));

                    }
                    pw.flush();

                }
                catch (IOException e) {
                    System.out.println("Error writing to a file: "  + e);
                }


            }
        }


    }

    /**
     * Computes up to num movie anti-recommendations for the user with the given
     * user id and prints these movie titles to the given file. These are the
     * movies the user should avoid. First calls findMostSimilarUser and then
     * getLeastFavoriteMovies(num) method on the "most similar user" to get up
     * to num movies the most similar user strongly disliked. Prints only
     * those movies to the file that the user with the given userid has not seen yet.
     * Format: one movie title per each line
     * @param userid id of the user
     * @param num max number of anti-recommendations
     * @param filename name of the file where to output anti-recommendations (movie titles)
     */
    public void findAntiRecommendations(int userid, int num, String filename) {

        // compute similarity between userid and all the other users
        // find the most similar user and anti-recommend movies that the most similar
        // user rated as 1.
        // Anti-recommend only the movies that userid has not seen (has not
        // rated).
        // FILL IN CODE

        UserNode curr = usersData.findMostSimilarUser(userid);;
        int [] movies_similarUser = curr.getLeastFavoriteMovies(num);

        for(int i = 0; i < num; i++)
        {
            if(!movieMap.containsKey(movies_similarUser[i]))
            {
                try (PrintWriter pw = new PrintWriter(filename)) {
                    for (Integer movie_recommendations: movies_similarUser) {
                        pw.println(movieMap.get(movie_recommendations));

                    }
                    pw.flush();

                }
                catch (IOException e) {
                    System.out.println("Error writing to a file: "  + e);
                }


            }
        }




    }

}
