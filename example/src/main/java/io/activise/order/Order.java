package io.activise.order;

import java.lang.String;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(
    name = "orders",
    indexes = @Index(columnList = "customerReference")
)
public class Order {
  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  private String id;

  private LocalDateTime createdDate;

  private String customerReference;

  private List<OrderEntry> orderEntries;

  public String getId() {
    return id;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public String getCustomerReference() {
    return customerReference;
  }

  public List<OrderEntry> getOrderEntries() {
    return orderEntries;
  }
}
