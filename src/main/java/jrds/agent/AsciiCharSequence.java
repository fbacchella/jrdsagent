package jrds.agent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;

/**
 * A {@link CharSequence} that read a file and can be manipulated without memory copy
 * It only accepts 7 bits ASCII files
 */
public final class AsciiCharSequence implements CharSequence {

    private static class DedicatedLinkedList {
        private DedicatedLinkedList next = null;
        private final byte[] data;
        private int size = -1;

        DedicatedLinkedList() {
            data = new byte[4096];
        }

        @Override
        public String toString() {
            return "DedicatedLinkedList{data=" + data.length + ", size=" + size + '}' + (next != null ? (" -> " + next) : "");
        }
    }

    public static AsciiCharSequence of(Path file, Duration timeout) throws IOException, TimeoutException {
        DedicatedLinkedList head = new DedicatedLinkedList();
        int totalSize = 0;
        long startTime = System.nanoTime();

        try (InputStream is = Files.newInputStream(file)) {
            DedicatedLinkedList curs = head;
            DedicatedLinkedList previous = head;

            while ((curs.size = is.read(curs.data)) > 0) {
                checkTimeoutDelay(timeout, startTime);
                totalSize += curs.size;
                previous = curs;
                curs.next = new DedicatedLinkedList();
                curs = curs.next;
            }
            previous.next = null;
        }
        if (head.size == -1) {
            return new AsciiCharSequence(ByteBuffer.allocate(0));
        } else if (head.next == null) {
            ByteBuffer buffer = ByteBuffer.wrap(head.data, 0, head.size);
            return new AsciiCharSequence(buffer);
        } else {
            byte[] data = new byte[totalSize];
            DedicatedLinkedList curs = head;
            int pos = 0;
            do {
                System.arraycopy(curs.data, 0, data, pos, curs.size);
                pos += curs.size;
                DedicatedLinkedList next = curs.next;
                curs.next = null; // Help GC
                curs = next;
            } while (curs != null);

            ByteBuffer buffer = ByteBuffer.wrap(data);
            return new AsciiCharSequence(buffer);
        }
    }

    private static void checkTimeoutDelay(Duration timeout, long startTime) throws TimeoutException {
        if (timeout != null) {
            Duration delay = Duration.ofNanos(System.nanoTime() - startTime);
            if (delay.compareTo(timeout) > 0) {
                throw new TimeoutException("Read duration " + delay.toMillis() + "ms");
            }
        }
    }

    public static AsciiCharSequence of(Path file) throws IOException, TimeoutException {
        return of(file, null);
    }

    /*
     * Used for tests
     */
    static AsciiCharSequence of(byte[] buffer) {
        return new AsciiCharSequence(ByteBuffer.wrap(buffer));
    }

    private final ByteBuffer buffer;

    private AsciiCharSequence(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int length() {
        return buffer.remaining();
    }

    @Override
    public char charAt(int index) {
        return (char) (buffer.get(index) & 0x7F); // ASCII safe
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        ByteBuffer slice = buffer.duplicate();
        int pos = buffer.position();
        slice.position(pos + start);
        slice.limit(pos + end);
        return new AsciiCharSequence(slice);
    }

    @Override
    public String toString() {
        return StandardCharsets.US_ASCII.decode(buffer.duplicate()).toString();
    }

    public Iterable<CharSequence> readLines(int skip) {
        ByteBuffer copy = buffer.duplicate();
        for (int i = 0; i < skip; i++) {
            readLine(copy);
        }
        return () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                return copy.hasRemaining();
            }

            @Override
            public CharSequence next() {
                return readLine(copy);
            }
        };
    }

    public Iterable<CharSequence> readLines() {
        return readLines(0);
    }

    private CharSequence readLine(ByteBuffer buffer) {
        ByteBuffer lineBuffer = buffer.slice();
        int length = 0;
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            if (b == '\n' || b == '\r') {
                // Skip a possible \n\r or \r\n
                if (buffer.hasRemaining()) {
                    byte next = buffer.get(buffer.position());
                    if ((next == '\n' || next == '\r') && next != b) {
                        buffer.position(buffer.position() + 1);
                    }
                }
                lineBuffer.limit(length);
                break;
            } else {
                length += 1;
            }
        }
        if (buffer.position() > 0) {
            return new AsciiCharSequence(lineBuffer);
        } else {
            return "";
        }
    }

}
