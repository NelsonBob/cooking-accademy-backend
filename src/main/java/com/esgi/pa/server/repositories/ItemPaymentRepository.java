package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.ItemPayment;
import com.esgi.pa.domain.enums.TypeCommandeEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPaymentRepository
  extends JpaRepository<ItemPayment, Long> {
  List<ItemPayment> findByTypeAndElementId(
    TypeCommandeEnum type,
    Long elementId
  );
}
