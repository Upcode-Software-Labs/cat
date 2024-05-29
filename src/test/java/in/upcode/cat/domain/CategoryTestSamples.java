package in.upcode.cat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Category getCategorySample1() {
        return new Category().id("id1").assignmentType("type1");
    }

    public static Category getCategorySample2() {
        return new Category().id("id2").assignmentType("type2");
    }

    public static Category getCategoryRandomSampleGenerator() {
        return new Category().id(UUID.randomUUID().toString()).assignmentType(UUID.randomUUID().toString());
    }
}
