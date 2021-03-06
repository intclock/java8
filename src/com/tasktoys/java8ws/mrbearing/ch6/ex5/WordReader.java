package com.tasktoys.java8ws.mrbearing.ch6.ex5;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

//ファイルを幾つか読み込み。
//結果を表示
//
public class WordReader {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		List<String> books = new ArrayList<>();
		books.add("http://www.gutenberg.org/cache/epub/19033/pg19033.txt");// alice
		books.add("http://www.gutenberg.org/cache/epub/2600/pg2600.txt"); // warAndPeace
		books.add("http://www.gutenberg.org/cache/epub/1210/pg1210.txt"); // ラフカディオ・ハーン

		Path workDirectory = Files.createDirectories(Paths
				.get("out/mrBearing/ch6ex5/"));
		int i = 0;
		for (String target : books) {
			try (InputStream input = new URL(target).openStream()) {
				Files.copy(input,
						Paths.get("out/mrBearing/ch6ex5/#" + i + ".txt"),
						StandardCopyOption.REPLACE_EXISTING);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				i++;
			}
		}
		// ココまでファリル読み込み
		List<File> targetFiles = new ArrayList<>();

		Files.walkFileTree(workDirectory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				targetFiles.add(file.toFile());
				return FileVisitResult.CONTINUE;
			}
		});

		ConcurrentHashMap<String, Set<File>> result = new ConcurrentHashMap<String, Set<File>>();

		List<Thread> threads = new ArrayList<Thread>();
		for (File file : targetFiles) {
			threads.add(new Thread(() -> {
				String[] s = new String[0];
				try {
					s = new String(Files.readAllBytes(file.toPath()),
							StandardCharsets.UTF_8).split("[\\P{L}]+");
				} catch (Exception e) {
					e.printStackTrace();
				}

				Arrays.stream(s).peek((word) -> {
					Set<File> set = result.get(word);
					if (set == null) {
						Set<File> fileSet = new HashSet<File>();
						fileSet.add(file);
						result.put(word, fileSet);
						return;
					} else {

						boolean flag = true;
						// 存在を探索
						for (File reg_file : set)
							if (reg_file.getName().equals(file.getName()))
								flag = false;
						// なかったら
						if (flag) { // アトミック保証できない？。。。。
							set.add(file);// 追加
							return;
						}

					}

				});

			}));
		}

		threads.forEach(th -> th.start());
		//終わるの待機
		threads.forEach(th -> {
			try {
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Path resultFile = Files.createDirectories(Paths
				.get("out/mrBearing/ch6ex5/result"));




		/*
		 * Stream<Stream<String>> streams = (Stream<Stream<String>>)
		 * Arrays.stream(targetFiles.toArray(new File[0])) .parallel()
		 * .map((File file)->{
		 *
		 * String[] s = new String[0]; try { s = new
		 * String(Files.readAllBytes(file.toPath()),
		 * StandardCharsets.UTF_8).split("[\\P{L}]+");
		 *
		 * } catch (Exception e) { e.printStackTrace(); } return
		 * Arrays.stream(s); });
		 *
		 * streams.parallel() .peek((stringStream)-> {
		 *
		 * stringStream.parallel() .peek( word -> { Set<File> set =
		 * result.get(word); if(set == null) result.put(word, new
		 * HashSet<File>() );
		 *
		 * }); }).close();
		 */

	}

}
