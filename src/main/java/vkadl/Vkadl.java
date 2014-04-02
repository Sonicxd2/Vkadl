package vkadl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Vkadl {
	private String ownerId;
	private String accessToken;
	private ExecutorService pool;
	private Set<Callable<Object>> tasks = new HashSet<>();
	private File outputLoc;

	public Vkadl(String accessToken, String ownerId, int numThreads, File outputLoc) {
		this.ownerId = ownerId;
		this.accessToken = accessToken;
		this.outputLoc = outputLoc;
		pool = Executors.newFixedThreadPool(numThreads);
	}

	public void start() {
		List<VkAudio> audios = new ArrayList<>();
		VkAudioCountRequest vkACR = new VkAudioCountRequest(accessToken, ownerId);
		int numAudios = vkACR.get();
		VkAudioGetRequest vkAGR = new VkAudioGetRequest(accessToken, ownerId);

		for (int i = 1; i <= numAudios / 6000; i++) {
			audios.addAll(vkAGR.get(i * 6000));
		}

		if (numAudios % 6000 != 0) {
			audios.addAll(vkAGR.get(numAudios - (numAudios % 6000)));
		}

		System.out.println("Added " + audios.size() + " audios to queue.");

		audios.forEach((audio) -> {
			VkDownloadThread thread = new VkDownloadThread(audio, outputLoc);
			tasks.add(() -> {
				thread.run();
				return thread;
			});
		});

		List<Future<Object>> futures = new ArrayList<>();
		for (Callable c : tasks) {
			futures.add(pool.submit(c));
		}

		int delta = 0;
		while (!pool.isTerminated()) {
			int remaining = 0;
			for (Future future : futures) {
				if (!future.isDone()) {
					remaining++;
				}
			}

			if (remaining != delta) {
				delta = remaining;
				System.out.println(remaining + " remaining.");
			}

			if (remaining == 0) break;
		}

		pool.shutdown();
	}
}
