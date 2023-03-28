package com.automation.tasks;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SongsPlaylist {

	private Map<String, Song> songsMap;
	private int songsCount;

	private class Song {

		String songId;
		Song next;
		Song prev;

		Song(String songId) {
			this.songId = songId;
			this.next = null;
			this.prev = null;
		}
	}

	public SongsPlaylist(int count) {
		this.songsMap = new HashMap<String, Song>();
		this.songsCount = count;
	}

	public SongsPlaylist addNewSong(String userId, String songId) {
		Song newSong = new Song(songId);

		if (songsMap.containsKey(userId)) {
			Song song = songsMap.get(userId);
			newSong.next = song;
			song.prev = newSong;
			songsMap.put(userId, newSong);
		} else {
			songsMap.put(userId, newSong);
		}

		if (songsMap.get(userId).prev != null) {
			songsMap.get(userId).prev.next = null;
		}

		int songsTotalCount = getSongsTotalCount(userId);

		if (songsTotalCount > songsCount) {
			removeLastSong(userId);
		}
		return this;
	}

	public List<String> getRecentSongs(String userId) {
		List<String> recentSongs = new LinkedList<String>();
		Song song = songsMap.get(userId);

		while (song != null) {
			recentSongs.add(song.songId);
			song = song.next;
		}
		return recentSongs;
	}

	public void removeSong(String userId, String songId) {
		Song song = songsMap.get(userId);

		while (song != null && !song.songId.equals(songId)) {
			song = song.next;
		}

		if (song == null) {
			return;
		}

		if (song.prev != null) {
			song.prev.next = song.next;
		} else {
			songsMap.put(userId, song.next);
		}

		if (song.next != null) {
			song.next.prev = song.prev;
		}
	}

	private int getSongsTotalCount(String userId) {
		int count = 0;
		Song song = songsMap.get(userId);

		while (song != null) {
			count++;
			song = song.next;
		}
		return count;
	}

	private void removeLastSong(String userId) {
		Song song = songsMap.get(userId);

		while (song.next != null) {
			song = song.next;
		}

		if (song.prev != null) {
			song.prev.next = null;
		} else {
			songsMap.put(userId, null);
		}
	}

}
