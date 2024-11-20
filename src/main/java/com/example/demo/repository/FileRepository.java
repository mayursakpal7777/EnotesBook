package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer>{
	

}
