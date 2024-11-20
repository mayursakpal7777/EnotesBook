package com.example.demo.dto;

import org.springframework.stereotype.Service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
@Entity
public class FileDetails {
//java programing_tutorials.pdf
	//java prog.pdf
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uploadFileName;
	private String originalFileName;
	private String displayFileName;
	private String path;
	private Long fileSize;
}
