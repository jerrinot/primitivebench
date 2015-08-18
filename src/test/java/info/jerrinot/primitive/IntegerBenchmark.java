package info.jerrinot.primitive;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@State(Scope.Benchmark)
public class IntegerBenchmark {

    private static final int MAXIMUM_VALUE = 1_000_000;
    private static final int ITEM_COUNT = 10_000_000;

    private List<Integer> list1;
    private List<Integer> list2;
    private Random r;

    private Integer valueToFind;

    @Setup(Level.Invocation)
    public void selectNextIndex() {
        valueToFind = r.nextInt(ITEM_COUNT);
    }

    @Setup
    public void setUp() {
        r = new Random();
        list1 = new ArrayList<>(ITEM_COUNT);
        list2 = new ArrayList<>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {
            int value = r.nextInt(MAXIMUM_VALUE);
            list1.add(value);
            value = r.nextInt(MAXIMUM_VALUE);
            list2.add(value);
        }
    }

    @Benchmark
    public int testFullScan() {
        int findCount = 0;

        for (int i = 0; i < list1.size(); i++) {
            Integer value1 = list1.get(i);
            Integer value2 = list2.get(i);
            if (valueToFind.equals(value1) || valueToFind.equals(value2)) {
                findCount++;
            }
        }
        return findCount;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(IntegerBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .threads(1)
                .forks(2)
                .build();

        new Runner(opt).run();
    }

}
