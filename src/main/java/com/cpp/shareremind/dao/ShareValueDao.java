package com.cpp.shareremind.dao;

import com.cpp.shareremind.model.ShareHoldNow;
import com.cpp.shareremind.model.ShareValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShareValueDao {

    @Select("select * from share")
    List<ShareValue> getShareValue();

    @Select("insert into share (share_code, value, share_time) values (#{shareCode},#{value},#{shareTime})")
    List<ShareValue> insert(ShareValue shareValue);

    @Select("select min(value) min_max_value from (select value from share where share_code=#{share_code} and share_time>#{today}  order by value desc limit 10) a")
    Double getMinMax(@Param("share_code") String share_code, @Param("today") String today);

    @Select("select max(value) from share where share_code=#{share_code} and share_time>#{today}")
    Double getTodayMax(@Param("share_code") String share_code, @Param("today") String today);

    @Select("select a.*,b.value*a.share now_total,a.cost*a.share cost_total,b.share_time,(b.value-a.cost)*a.share profit_total,b.value from share_hold a left join (select share_code,value,share_time from share where id in (select max(id) from share group by share_code having share_code in (select code from share_hold))) b on a.code=b.share_code")
    List<ShareHoldNow> getLatestPrice();
}
