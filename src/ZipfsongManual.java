import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class ZipfsongManual {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            final StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");
            final int songsInAlbum = Integer.parseInt(tokenizer.nextToken());
            int songsToSelect = Integer.parseInt(tokenizer.nextToken());

            Song x;
            StringTokenizer tokenizer1;

            int index = 1;

            tokenizer1 = new StringTokenizer(br.readLine(), " ");
            SongNode queue = new SongNode(new Song(Long.parseLong(tokenizer1.nextToken()), tokenizer1.nextToken(), index));

            index++;

            while (index <= songsInAlbum) {
                tokenizer1 = new StringTokenizer(br.readLine(), " ");
                x = new Song(Long.parseLong(tokenizer1.nextToken()), tokenizer1.nextToken(), index);
                index++;
                if (x.qi > queue.song.qi) {
                    //If it should go to the head, replace it here so that we can replace the reference as well
                    final SongNode newSongNode = new SongNode(x);
                    newSongNode.next = queue;
                    queue = newSongNode;
                } else if (queue.next == null) {
                    //If there are no more elements, just attach it
                    queue.next = new SongNode(x);
                } else {
                    //Otherwise, find its place
                    pushNonTopSong(queue.next, x);
                }
            }

            SongNode node = queue;
            for (; songsToSelect > 0; songsToSelect--) {
                System.out.println(node.song.toString());
                node = node.next;
            }
        } catch (IOException io) {
            io.printStackTrace(System.err);
        }
    }

    /**
     * This method allocates the given song in the queue of results according to the statement
     *
     * @param node The queue of results
     * @param x    The song to allocate
     */
    private static void pushNonTopSong(final SongNode node, final Song x) {
        if (node.next == null) {
            //If it's the lowest qi song, there are no more songs remaining to compare with, so just straightforward add it
            node.next = new SongNode(x);
        } else {
            //If the qi is larger than the current song, since we're traversing the structure in descending order of qi, it belongs here
            if (x.qi > node.song.qi) {
                final SongNode bridge = new SongNode(node.song);
                node.song = x;
                bridge.next = node.next;
                node.next = bridge;
            } else {
                //Otherwise it does not belong here, so we have to compare with the next one
                pushNonTopSong(node.next, x);
            }
        }
    }

    /**
     * Dummy data structure to be able to refer back to the song names
     */
    private static class Song {
        private final long qi;
        private final String name;

        private Song(long fi, String name, int indexInAlbum) {
            this.name = name;
            qi = fi * indexInAlbum;
        }

        public String toString() {
            return name;
        }
    }

    private static class SongNode {
        private SongNode next;
        private Song song;

        private SongNode(final Song _song) {
            song = _song;
        }
    }
}