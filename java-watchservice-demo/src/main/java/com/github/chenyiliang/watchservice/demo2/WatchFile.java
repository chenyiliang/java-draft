package com.github.chenyiliang.watchservice.demo2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class WatchFile {

	private static final String FILE_PATH = "D:/data";

	public static void main(String[] args) throws IOException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Paths.get(FILE_PATH).register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
		while (true) {
			WatchKey watchKey = watchService.take();
			List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
			for (WatchEvent<?> event : watchEvents) {
				System.out.println(event.context() + " --> " + event.kind());
			}
			boolean valid = watchKey.reset();
			if (!valid) {
				break;
			}
		}
	}

}
