package com.github.chenyiliang.watchservice.demo1;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class Listener implements Runnable {
	private WatchService watchService;

	private String rootPath;

	public Listener(WatchService watchService, String rootPath) {
		this.watchService = watchService;
		this.rootPath = rootPath;
	}

	@Override
	public void run() {
		try {
			while (true) {
				WatchKey watchKey = this.watchService.take();
				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
				for (WatchEvent<?> event : watchEvents) {
					System.out.println("[" + rootPath + "/" + event.context() + "]文件发生了[" + event.kind() + "]事件");
				}
				watchKey.reset();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				this.watchService.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
