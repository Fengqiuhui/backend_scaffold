package com.maoniunet.common;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;

public interface SuperMapper<T> extends BaseMapper<T> {

    default Condition condition(Long userId) {
        Condition condition = Condition.create();
        condition.eq("user_id", userId);
        return condition;
    }

    default Wrapper<T> wrapper(Long userId) {
        Wrapper<T> wrapper = Condition.wrapper();
        wrapper.eq("user_id", userId);
        return wrapper;
    }

    default List<T> selectListByUserId(Long userId) {
        return selectList(wrapper(userId));
    }

    default void deleteByUserId(Long userId) {
        delete(wrapper(userId));
    }
}
