package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;


@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	public Page<EventDTO> findAllPaged(Pageable pageable){
		Page<Event> list = repository.findAll(pageable);
		return list.map(x -> new EventDTO(x));
	}
	
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
        copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new EventDTO(entity);
	}
	
	private void copyDtoToEntity(EventDTO dto, Event entity) {
		 entity.setName(dto.getName());
		 entity.setDate(dto.getDate());
		 entity.setUrl(dto.getUrl());
		 
		 entity.getCities().clear();
		 for(CityDTO cityDto : dto.getCities()) {
			 City city = cityRepository.getOne(cityDto.getId());
			 entity.getCities().add(city);
		 }
	}
}
