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

        final GzipCodec gzipCodec = new GzipCodec();
        gzipCodec.setConf(new Configuration());

        final Random random = new Random();
        while (true) {
            try (CompressionOutputStream outputStream = gzipCodec.createOutputStream(new ByteArrayOutputStream())) {
                for (int i = 0; i < 1_000_000; i++) {
                    outputStream.write(random.nextInt(256));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
