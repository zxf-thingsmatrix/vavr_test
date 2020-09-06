package lost.canvas.vavr_test.values;

import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;

public class TryTest {
    @Test
    public void test_success() {
        String v = Try.success("lost")
                .getOrElse("canvas");

        Assert.assertEquals("lost", v);
    }

    @Test
    public void test_failure() {
        Object v = Try.failure(new RuntimeException("runtime error"))
                .getOrElse("canvas");

        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_success_to_failure() {
        String v = Try.success("lost")
                .andThen(e -> {
                    throw new RuntimeException("runtime error");
                })
                .getOrElse("canvas");

        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_success_to_failure_2() {
        Object v = Try.success("lost")
                .map(e -> {
                    throw new RuntimeException("runtime error");
                })
                .getOrElse("canvas");
        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_success_to_failure3() {
        Object v = Try.success("lost")
                .flatMap(e -> Try.of(() -> {
                    throw new Exception("checked error");
                }))
                .getOrElse("canvas");

        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_failure_to_success() {
        Object v = Try.of(() -> {
            throw new Exception("checked error");
        })
                .orElse(Try.success("lost"))
                .getOrElse("canvas");

        Assert.assertEquals("lost", v);
    }

    @Test
    public void test_failure_to_success_2() {
        Object v = Try.of(() -> {
            throw new Exception("checked error");
        }).recover(Exception.class, "lost")
                .getOrElse("canvas");

        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_failure_to_success_3() {
        Object v = Try.of(() -> {
            throw new Exception("checked error");
        }).recoverWith(Exception.class, Try.success("lost"))
                .getOrElse("canvas");

        Assert.assertEquals("lost", v);
    }

    @Test
    public void test_failure_to_success_4() {
//        Object v = Try.of(() -> {
//            throw new Exception("checked error");
//        }).mapFailure()
//                .getOrElse("canvas");
//
//        Assert.assertEquals("canvas", v);
    }

    @Test
    public void test_finally() {
        Try.of(() -> {
            throw new Exception("checked error");
        }).andFinally(() -> System.out.println("release resource"))
                .recover(ex -> {
                    System.out.println("checked error ---> runtime error");
                    return new RuntimeException(ex);
                })
                .andFinally(() -> System.out.println("release resource again"));
    }


}
