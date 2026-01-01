package domain;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {
    private final BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money of(BigDecimal value) {
        Objects.requireNonNull(value, "value");
        return new Money(value);
    }

    public static Money of(double value) {
        return of(BigDecimal.valueOf(value));
    }

    public BigDecimal asBigDecimal() {
        return value;
    }

    public Money add(Money other) {
        return Money.of(this.value.add(other.value));
    }

    public Money subtract(Money other) {
        return Money.of(this.value.subtract(other.value));
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }
}
