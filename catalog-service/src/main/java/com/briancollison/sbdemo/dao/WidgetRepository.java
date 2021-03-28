package com.briancollison.sbdemo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.briancollison.sbdemo.model.Widget;

public interface WidgetRepository extends CrudRepository<Widget, Long> {
    List<Widget> findWidgetByName(String name);
}
