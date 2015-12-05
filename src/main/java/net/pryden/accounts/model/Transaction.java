package net.pryden.accounts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ComparisonChain;

import java.math.BigDecimal;

/**
 * Represents a single transaction (one line in the accounts sheet).
 *
 * <p>Note: This implementation only supports a single account (the optional extra bank account on
 * the accounts sheet is not yet implemented.)
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_Transaction.Builder.class)
public abstract class Transaction implements Comparable<Transaction> {
  Transaction() {}

  /** The day of the month. */
  @JsonProperty("date")
  public abstract int date();

  /** The description of the transaction. */
  @JsonProperty("description")
  public abstract String description();

  /** The category of the transaction. */
  public abstract TransactionCategory category();

  @JsonProperty("category")
  String serializedCategory() {
    return String.valueOf(category().code());
  }

  /** The value of money received as receipts. */
  @JsonProperty("receipts-in")
  public abstract BigDecimal receiptsIn();

  /** The value of money being deposited or otherwise spent out of receipts. */
  @JsonProperty("receipts-out")
  public abstract BigDecimal receiptsOut();

  /** The value of money being deposited into the checking account. */
  @JsonProperty("checking-in")
  public abstract BigDecimal checkingIn();

  /** The value of money being spent out of the checking account. */
  @JsonProperty("checking-out")
  public abstract BigDecimal checkingOut();

  @Override
  public int compareTo(Transaction other) {
    return ComparisonChain.start()
        .compare(date(), other.date())
        .compare(category(), other.category())
        .result();
  }

  /** Returns a new {@link Builder} instance. */
  public static Builder builder() {
    return new AutoValue_Transaction.Builder();
  }

  /** Builder for {@link Transaction} instances. */
  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {
      setReceiptsIn(BigDecimal.ZERO);
      setReceiptsOut(BigDecimal.ZERO);
      setCheckingIn(BigDecimal.ZERO);
      setCheckingOut(BigDecimal.ZERO);
    }

    @JsonProperty("date")
    public abstract Builder setDate(int date);

    @JsonProperty("description")
    public abstract Builder setDescription(String description);

    @JsonProperty("category")
    Builder setCategory(String category) {
      return setCategory(TransactionCategory.fromCode(category));
    }

    public abstract Builder setCategory(TransactionCategory category);

    @JsonProperty("receipts-in")
    public abstract Builder setReceiptsIn(BigDecimal receiptsIn);

    @JsonProperty("receipts-out")
    public abstract Builder setReceiptsOut(BigDecimal receiptsOut);

    @JsonProperty("checking-in")
    public abstract Builder setCheckingIn(BigDecimal checkingIn);

    @JsonProperty("checking-out")
    public abstract Builder setCheckingOut(BigDecimal checkingOut);

    public abstract Transaction build();
  }
}