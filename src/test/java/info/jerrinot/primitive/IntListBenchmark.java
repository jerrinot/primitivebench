package info.jerrinot.primitive;

import info.jerrinot.primitive.impl.IntListFactory;
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

import java.util.Random;

@State(Scope.Benchmark)
public class IntListBenchmark {

    private static final int MAXIMUM_VALUE = 10;
    private static final int ITEM_COUNT = 10_000_000;

    @Param({"ARRAY", "UNSAFE", "HEAP_BB", "DIRECT_BB"})
    private IntListFactory.Type type;

    private IntList list1;
    private IntList list2;
    private Random r;

    private int valueToFind;


    @Setup(Level.Invocation)
    public void selectNextIndex() {
        valueToFind = r.nextInt(ITEM_COUNT);
    }

    @Setup
    public void setUp() {
        createLists();
        r = new Random();
        loadInitialData();
    }

    private void loadInitialData() {
        for (int i = 0; i < ITEM_COUNT; i++) {
            int value = r.nextInt(MAXIMUM_VALUE);
            list1.add(value);
            value = r.nextInt(MAXIMUM_VALUE);
            list2.add(value);
        }
    }

    private void createLists() {
        list1 = IntListFactory.newList(type, ITEM_COUNT);
        list2 = IntListFactory.newList(type, ITEM_COUNT);
    }

    @Benchmark
    public int fullScan_lazy() {
        int findCount = 0;
        for (int i = 0; i < list1.size(); i++) {
            if (list1.elementAt(i) == valueToFind || list2.elementAt(i) == valueToFind) {
                findCount++;
            }
        }
        return findCount;
    }

    @Benchmark
    public int fullScan_eager() {
        int findCount = 0;
        for (int i = 0; i < list1.size(); i++) {
            int value1 = list1.elementAt(i);
            int value2 = list2.elementAt(i);
            if (value1 == valueToFind || value2 == valueToFind) {
                findCount++;
            }
        }
        return findCount;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(IntListBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .threads(1)
                .forks(2)
                .build();

        new Runner(opt).run();
    }

}
