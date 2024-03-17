package in.upcode.cat.domain;

import java.util.UUID;

public class ValidationRuleTestSamples {

    public static ValidationRule getValidationRuleSample1() {
        return new ValidationRule().id("id1").ruleType("ruleType1");
    }

    public static ValidationRule getValidationRuleSample2() {
        return new ValidationRule().id("id2").ruleType("ruleType2");
    }

    public static ValidationRule getValidationRuleRandomSampleGenerator() {
        return new ValidationRule().id(UUID.randomUUID().toString()).ruleType(UUID.randomUUID().toString());
    }
}
