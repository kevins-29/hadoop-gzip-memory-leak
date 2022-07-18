package org.apache.hadoop.gzipmemoryleak;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class App {

    public static void main(String[] args) {

        System.out.println("Running test... press ctrl+C to cancel!");

        final int fileSize = args.length == 1 ? Integer.parseInt(args[0]) : 1024 * 1024;

        final GzipCodec gzipCodec = new GzipCodec();
        gzipCodec.setConf(new Configuration());

        final Random random = new Random();
        while (true) {
            try (CompressionOutputStream outputStream = gzipCodec.createOutputStream(new ByteArrayOutputStream())) {
                for (int i = 0; i < fileSize; i++) {
                    outputStream.write(random.nextInt(256));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
