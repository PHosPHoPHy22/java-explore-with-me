package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.IntegrityViolationException;
import ru.practicum.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public Category addCategory(Category category) {
        log.info("Creating category: {}", category);
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IntegrityViolationException("Category name '" + category.getName() + "' already exists.");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long catId) {
        log.info("Deleting category with ID: {}", catId);
        categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("Category with id=" + catId + " not found"));
        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            throw new IllegalStateException("Cannot delete category " + catId + " because it's associated with events.");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public Category updateCategory(long catId, Category newCategory) {
        log.info("Updating category with ID {}: {}", catId, newCategory);
        Category updateCategory = categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("Category with id=" + catId + " not found"));
        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(newCategory.getName(), catId)) {
            throw new IntegrityViolationException("Category name '" + newCategory.getName() + "' already exists.");
        }
        updateCategory.setName(newCategory.getName());
        return updateCategory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories(int from, int size) {
        log.info("Finding categories, from={}, size={}", from, size);
        PageRequest pageRequest = PageRequest.of(from, size);
        return categoryRepository.findAll(pageRequest).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategory(long catId) {
        log.info("Finding category with ID: {}", catId);
        return categoryRepository.findById(catId);
    }
}