package com.example.bokningsapp.service;

import com.example.bokningsapp.enums.EquipmentStatus;
import com.example.bokningsapp.enums.EquipmentType;
import com.example.bokningsapp.exception.EquipmentNotFoundException;
import com.example.bokningsapp.model.Equipment;
import com.example.bokningsapp.repository.EquipmentRepo;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepo equipmentRepo;


    @Autowired
    public EquipmentServiceImpl(EquipmentRepo equipmentRepo) {
        this.equipmentRepo = equipmentRepo;
    }

    @Override
    public Equipment saveEquipment(Equipment equipment) {

        Equipment _equipment = new Equipment(equipment.getEquipmentName(), equipment.getEquipmentLocation(), equipment.getEquipmentImg(), equipment.getMaxDaysToRent(), equipment.getEquipmentDescription(),equipment.getEquipmentBrand(), equipment.getEquipmentType(), equipment.getEquipmentStatus(), equipment.isAvailable(LocalDate.now(), LocalDate.now().plusDays(3)));

        equipmentRepo.save(_equipment);

        return _equipment;
    }




    @Override
    public void updateEquipmentStatus(int equipmentId, boolean isBooked) {
        Equipment updateEquipment = equipmentRepo.findById(equipmentId).orElse(null);

        if (updateEquipment != null) {
            if (isBooked) {
                updateEquipment.setEquipmentStatus(EquipmentStatus.UNAVAILABLE);
            } else {
                updateEquipment.setEquipmentStatus(EquipmentStatus.AVAILABLE);
            }
            equipmentRepo.save(updateEquipment);
        }
    }
    @Override
    public List<Equipment> findEquipmentByType(EquipmentType equipmentType) {
        return equipmentRepo.findByEquipmentType(equipmentType);
    }

    @Transactional
    @Override
    public void deleteEquipment(int id) {
        Equipment equipment = equipmentRepo.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found with id " + id));
        equipmentRepo.delete(equipment);
    }


}
