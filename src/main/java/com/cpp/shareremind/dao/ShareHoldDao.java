package com.cpp.shareremind.dao;

import com.cpp.shareremind.model.ShareHold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ShareHoldDao extends JpaRepository<ShareHold, Integer> {
    void deleteByCode(String code);

    @Modifying
    @Transactional
    @Query(value = "update share_hold set now_price=:nowPrice where code=:code", nativeQuery = true)
    void updateShareHoldNowPrice(@Param("code") String code, @Param("nowPrice") Double nowPrice);
}
