package io.activise.order;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(
    name = "order_entries"
)
public class OrderEntry {
  private Order order;

  public Order getOrder() {
    return order;
  }
}
