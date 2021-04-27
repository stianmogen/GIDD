package com.ntnu.gidd.service.equipment;

import com.ntnu.gidd.dto.EquipmentDto;
import com.ntnu.gidd.model.Equipment;
import com.ntnu.gidd.repository.EquipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl implements EquipmentService{

    @Autowired
    EquipmentRepository equipmentRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Equipment> saveAndReturnEquipments(List<EquipmentDto> equipmentDtos) {
        List<Equipment> equipment = equipmentDtos.stream().map(s -> modelMapper.map(s, Equipment.class)).collect(Collectors.toList());
        return equipmentRepository.saveAll(equipment);
    }
}
