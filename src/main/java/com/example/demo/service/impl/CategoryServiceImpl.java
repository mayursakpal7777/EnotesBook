package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
		//Dto data transfer ojbject set
		//Category category=new Category();
		//category.setName(categoryDto.getName());
		//category.setDescription(categoryDto.getDescription());
		//category.setIsActive(categoryDto.getIsActive());
     Category category = mapper.map(categoryDto,Category.class);
     //update category logic
     if(ObjectUtils.isEmpty(category.getId()))
     {
    	  
    	   category.setIsDeleted(false);
    	   category.setCreatedBy(1);
    	   category.setCreatedOn(new Date()); 
     }else {
    	 updateCategory(category);
     }
  
   //category.setIsDeleted(false);
   //category.setCreatedBy(1);
   //category.setCreatedOn(new Date());
	Category saveCategory=categoryRepository.save(category);
	if(ObjectUtils.isEmpty(saveCategory))
	{
		return false;
	}
		return true;
	}
     //create method in updatge
	private void updateCategory(Category category) {
		Optional<Category> findById = categoryRepository.findById(category.getId());
		if(findById.isPresent()){
			Category existCategory = findById.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
			
			//update value set
			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());
			
		}
		
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findByIsDeletedFalse();
		List<CategoryDto>categoryDtoList=categories.stream().map(cat ->mapper.map(cat,CategoryDto.class)).toList();
		return categoryDtoList ;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class))
				.toList();
		return categoryList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {
		Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(()->new ResourceNotFoundException("Category not found with id="+id));
		
		if(!ObjectUtils.isEmpty(category))
		{
			category.getName().toUpperCase();
		return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
      Optional<Category> findByCategory = categoryRepository.findById(id);
		
		if(findByCategory.isPresent())
		{
		Category category=findByCategory.get();
		category.setIsDeleted(true);
		categoryRepository.save(category);
		return true;
		}
		return false;
		
	}

	

}
