package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Notes;

public interface NotesRespository extends JpaRepository<Notes, Integer>{

}
