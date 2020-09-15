package lost.canvas.vavr_test.matches;

import org.junit.Assert;
import org.junit.Test;

import static io.vavr.API.*;

public class MatchTest {

    @Test
    public void t() {
        int score = 77;
        Grade grade = Match(score).of(
                Case($(i -> i >= 90), Grade.excellent),
                Case($(i -> i >= 80 && i < 90), Grade.good),
                Case($(i -> i >= 70 && i < 80), Grade.medium),
                Case($(i -> i >= 60 && i < 70), Grade.pass),
                Case($(), Grade.no_pass)
        );

        Assert.assertEquals(Grade.medium, grade);
    }

    @Test
    public void t2() {
        int rank = 1;

        Grade v = Match(rank).of(
                Case($(1), () -> Grade.excellent),
                Case($(2), () -> Grade.good),
                Case($(3), () -> Grade.medium),
                Case($(4), () -> Grade.pass),
                Case($(), () -> Grade.no_pass)
        );

        Assert.assertEquals(Grade.excellent, v);
    }

    enum Grade {
        excellent, good, medium, pass, no_pass
    }
}
