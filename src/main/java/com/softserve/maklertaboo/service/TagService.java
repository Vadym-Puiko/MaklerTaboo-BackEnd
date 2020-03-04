package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public Set<Tag> getTags(Set<String> tags) {
        return tagRepository.findByNameIn(tags);
    }

    public Page<Tag> getAll(Pageable pageable){
        return tagRepository.findAll(pageable);
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

}
