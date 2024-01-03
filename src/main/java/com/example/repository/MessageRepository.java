package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    /*@Query("UPDATE Message SET message_text = :message_text WHERE message_id = :message_id")
    int updateTextById(@Param("message_id") int message_id, @Param("message_text") String message_text);*/
    @Query("FROM Message WHERE posted_by = :id_var")
    List<Message> findByPosted_by(@Param("id_var") int account_id);

    @Modifying
    @Query("UPDATE Message SET message_text = :message_textVar WHERE message_id = :message_idVar")
    int updateMessage(@Param("message_textVar") String message_textVar, @Param("message_idVar") int message_idVar);
}
