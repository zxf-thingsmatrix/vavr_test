package lost.canvas.vavr_test.values;

import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.Test;

public class OptionTest {
    @Test
    public void test_some() {
        Object v = Option.of("lost")
                .map(e -> null)
                .getOrElse("canvas");

        Assert.assertNull(v);
    }

    @Test
    public void test_none() {
        String v = Option.none()
                .map(e -> "lost")
                .getOrElse("canvas");

        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_some_to_none() {
        Object v = Option.of("lost")
                .flatMap(e -> Option.none())
                .getOrElse("canvas");

        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_some_to_none_2() {
        String v = Option.of("lost")
                .filter(e -> e.contains("canvas"))
                .getOrElse("canvas");

        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_none_to_some() {
        Object v = Option.none()
                .orElse(Option.some("lost"))
                .getOrElse("canvas");

        Assert.assertEquals("lost", v);
    }

    @Test
    public void test_anti_null_point_exception() {
        int docker0 = (172 & 0xFF) << 24 | (18 & 0xFF) << 16 | (1 & 0xFF) << 8 | (1 & 0xFF);
        Integer ip = Option.of(new Http(new Host(null, (short) 44444)))
                .flatMap(e -> Option.of(e.getHost()))
                .flatMap(e -> Option.of(e.getIp()))
                .getOrElse(docker0);

        Assert.assertEquals(docker0, (int) ip);
    }

    private static class Http {
        private final Host host;

        public Http(Host host) {
            this.host = host;
        }

        public Host getHost() {
            return host;
        }
    }

    private static class Host {
        private final Integer ip;
        private final Short port;

        public Host(Integer ip, Short port) {
            this.ip = ip;
            this.port = port;
        }

        public Integer getIp() {
            return ip;
        }

        public Short getPort() {
            return port;
        }
    }
}
