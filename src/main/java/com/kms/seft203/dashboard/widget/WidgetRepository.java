package com.kms.seft203.dashboard.widget;

import com.kms.seft203.dashboard.widget.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, String> {
}
