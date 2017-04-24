package com.github.chenyiliang.watchservice.demo1;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http://blog.csdn.net/maosijunzi/article/details/42030403
 */
public class ResourceListener {

	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

	private WatchService watchService;

	private String listenerPath;

	private ResourceListener(String path) {
		try {
			this.watchService = FileSystems.getDefault().newWatchService();
			this.listenerPath = path;
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	private void start() {
		this.fixedThreadPool.execute(new Listener(watchService, this.listenerPath));
	}

	public static void addListener(String path) throws IOException {
		ResourceListener resourceListener = new ResourceListener(path);
		Path p = Paths.get(path);
		p.register(resourceListener.watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
	}

	public static void main(String[] args) throws IOException {
		ResourceListener.addListener("D:/data");
	}

}
