package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.FileDetails;
import com.example.demo.dto.NotesDto;
import com.example.demo.dto.NotesDto.CategoryDto;
import com.example.demo.entity.Notes;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.NotesRespository;
import com.example.demo.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRespository notesRespository;
	
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Value("${file.upload.path}")
	private  String uploadpath;
     
	@Autowired
	private FileRepository fileRepository;
	
     
	@Override
	public Boolean saveNotes(String notes, MultipartFile file)throws Exception {
		
		ObjectMapper ob= new ObjectMapper();
		NotesDto notesDto =ob.readValue(notes, NotesDto.class);
		
		

		
		//category validation
		checkCategoryExist(notesDto.getCategory());
		
		// validation notes
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
		//file details save 
				FileDetails fileDtls=saveFileDetails(file);
				
				if(!ObjectUtils.isEmpty(fileDtls))
				{
				notesMap.setFileDetails(fileDtls);	
				}else {
					notesMap.setFileDetails(null);
				}
				
		Notes saveNotes =notesRespository.save(notesMap);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
	    return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		
		if(!ObjectUtils.isEmpty(file)  && !file.isEmpty())
		{
			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);
			List<String> extensionAllow = Arrays.asList("pdf","xlsx","jpg");
			if(!extensionAllow.contains(extension))
			{
				throw new IllegalArgumentException("invalid  file format !  Upload only .pdf,.xlsx,.jpg");
			}
	       //FileDetails fileDtls=new FileDetails();
	      // String originalFilename = file.getOriginalFilename();
	       //fileDtls.setOriginalFileName(originalFilename);
	       //fileDtls.setDisplayFileName(getDisplayName(originalFilename));
	       
	       //rendom number
	       String rndString=UUID.randomUUID().toString();
	       //String extension =FilenameUtils.getExtension(originalFilename);
			String uploadfileName = rndString+ "." +extension;
			//fileDtls.setUploadFileName(uploadfileName);
			//fileDtls.setFileSize(file.getSize());
			
			File saveFile = new File(uploadpath);
			if(!saveFile.exists())
			{
				saveFile.mkdir();
			}
			//path : enotesapiservice/notes/java.pdf
			String storePath=uploadpath.concat(uploadfileName);
			
			//fileDtls.setPath(storePath);
			
			//upload file
		//Files.copy(file.getInputStream(), Paths.get(storePath));
			long upload =Files.copy(file.getInputStream(), Paths.get(storePath));
			if(upload!=0)
			{
				FileDetails fileDtls=new FileDetails();
				fileDtls.setOriginalFileName(originalFileName);
			    fileDtls.setDisplayFileName(getDisplayName(originalFileName));
			   	fileDtls.setUploadFileName(uploadfileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				FileDetails saveFileDtls = fileRepository.save(fileDtls);
				return saveFileDtls;
			}
	
	       
		}
		return null;
	}

	private String getDisplayName(String originalFilename) {
		//java_programmimng_tutorials.pdf
		//file extenisin and pdf
		String extension =FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		
		if(fileName.length()>8)
		{
			fileName=fileName.substring(0,7);
		}
		fileName = fileName +"."+extension;
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
	categoryRepository.findById(category.getId())
	.orElseThrow(() ->new ResourceNotFoundException("category is invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
	return	notesRespository.findAll().stream()
		      .map(note->mapper.map(note,NotesDto.class)).toList();
		
	}

}
