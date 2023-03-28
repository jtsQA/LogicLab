package com.automation.tests;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Test;
import com.automation.tasks.SongsPlaylist;

public class SongsPlaylistTest {

	/* OVERVIEW
	 * Create an in-memory store for recently played songs that can accommodate N songs per user, with a fixed initial capacity. This store must have the
	 * capability to store a list of song-user pairs, with each song linked to a user. It should also be able to fetch recently played songs based on the
	 * user and eliminate the least recently played songs when the store becomes full.
	 * EXAMPLE
	 * Illustration, when 4 different songs were played by a user & Initial capacity is 3:
	 * Let's assume that the user has played 3 songs - S1, S2 and S3.
	 * The playlist would look like -> S1,S2,S3
	 * When S4 song is played -> S2,S3,S4
	 * When S2 song is played -> S3,S4,S2
	 * When S1 song is played -> S4,S2,S1 */

	private final String USER_1 = "user_1", USER_2 = "user_2", USER_3 = "user_3";
	private final String SONG_1 = "song_1", SONG_2 = "song_2", SONG_3 = "song_3", SONG_4 = "song_4", SONG_5 = "song_5";
	private final int SONGS_COUNT = 4;

	@Test
	public void testGetSongsPlaylist() {
		SongsPlaylist playlist = new SongsPlaylist(SONGS_COUNT);

		playlist.addNewSong(USER_1, SONG_1).addNewSong(USER_1, SONG_2).addNewSong(USER_1, SONG_3).addNewSong(USER_1, SONG_4).addNewSong(USER_1, SONG_1);
		System.out.println("User 1: " + playlist.getRecentSongs(USER_1));
		assertEquals("User 1 songs are not matching!", playlist.getRecentSongs(USER_1), Arrays.asList(SONG_1, SONG_4, SONG_3, SONG_2));

		playlist.addNewSong(USER_2, SONG_1).addNewSong(USER_2, SONG_2).addNewSong(USER_2, SONG_3);
		System.out.println("User 2: " + playlist.getRecentSongs(USER_2));
		assertEquals("User 2 songs are not matching!", playlist.getRecentSongs(USER_2), Arrays.asList(SONG_3, SONG_2, SONG_1));

		playlist.addNewSong(USER_3, SONG_1).addNewSong(USER_3, SONG_2).addNewSong(USER_3, SONG_3).addNewSong(USER_3, SONG_4);
		playlist.removeSong(USER_3, SONG_3);
		playlist.addNewSong(USER_3, SONG_5).addNewSong(USER_3, SONG_1);
		System.out.println("User 3: " + playlist.getRecentSongs(USER_3));
		assertEquals("User 3 songs are not matching!", playlist.getRecentSongs(USER_3), Arrays.asList(SONG_1, SONG_5, SONG_4, SONG_2));
	}
}
