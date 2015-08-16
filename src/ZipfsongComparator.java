import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class ZipfsongComparator {

    public static void main(String[] args) {

        final int songsInAlbum, songsToSelect;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");
            songsInAlbum = Integer.parseInt(tokenizer.nextToken());
            songsToSelect = Integer.parseInt(tokenizer.nextToken());

            List<Song> songs = new ArrayList<>();

            int index = 1;
            while (index <= songsInAlbum) {
                tokenizer = new StringTokenizer(br.readLine(), " ");
                songs.add(new Song(Long.parseLong(tokenizer.nextToken()), tokenizer.nextToken(), index));

                index++;
            }

            Collections.sort(songs, new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    if (o1 == null) {
                        return o2 == null ? 0 : 1;
                    }

                    float diff = o2.qi - o1.qi;
                    float result = diff == 0 ? o1.index - o2.index : diff;

                    return (int) Math.signum(result);
                }
            });

            for (int i = 0; i < songsToSelect; i++) {
                System.out.println(songs.get(i).toString());
            }
        } catch (IOException io) {
            io.printStackTrace(System.err);
        }
    }

    /**
     * Dummy data structure to be able to refer back to the song names
     */
    private static class Song {
        private final long qi;
        private final String name;
        private final int index;

        private Song(long fi, String name, int indexInAlbum) {
            this.name = name;
            qi = fi * indexInAlbum;
            index = indexInAlbum;
        }

        public String toString() {
            return name;
        }
    }
}
