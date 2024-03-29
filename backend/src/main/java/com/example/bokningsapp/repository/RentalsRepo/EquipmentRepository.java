package com.example.bokningsapp.repository.RentalsRepo;

import com.example.bokningsapp.enums.EquipmentStatus;
import com.example.bokningsapp.enums.EquipmentType;
import com.example.bokningsapp.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository <Equipment, Integer> {

    List<Equipment> findByEquipmentType(EquipmentType equipmentType);
    List<Equipment> findAllByEquipmentStatus(EquipmentStatus equipmentStatus);

    Equipment findAllById(int id);

}
