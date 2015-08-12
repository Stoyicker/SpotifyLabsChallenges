import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Zipfsong {

    public static void main(String[] args) {

        final int songsInAlbum, songsToSelect;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            final StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");
            songsInAlbum = Integer.parseInt(tokenizer.nextToken());
            songsToSelect = Integer.parseInt(tokenizer.nextToken());

            Song[] songs = new Song[songsToSelect];

            Song x;
            StringTokenizer tokenizer1;
            int index = 1;
            while (index <= songsInAlbum) {
                tokenizer1 = new StringTokenizer(br.readLine(), " ");
                x = new Song(Long.parseLong(tokenizer1.nextToken()), tokenizer1.nextToken(), index);
                index++;
                if (songs[songs.length - 1] == null || x.qi > songs[songs.length - 1].qi)
                    pushNewSong(songs, x);
            }

            for (Song s : songs) {
                System.out.println(s.toString());
            }
        } catch (IOException io) {
            io.printStackTrace(System.err);
        }
    }

    /**
     * This method allocates the given song in the array of results according to the statement
     *
     * @param songs The array of results
     * @param x     The song to allocate
     */
    private static void pushNewSong(Song[] songs, Song x) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i] == null || x.qi > songs[i].qi) {
                for (int a = songs.length - 1; a > i; a--) {
                    songs[a] = songs[a - 1];
                }
                songs[i] = x;
                break;
            }
        }
    }

    /**
     * Dummy data structure to be able to refer back to the song names
     */
    private static class Song {
        private final float qi;
        private final String name;

        private Song(long fi, String name, int indexInAlbum) {
            this.name = name;
            qi = fi * indexInAlbum;
        }

        public String toString() {
            return name;
        }
    }
}