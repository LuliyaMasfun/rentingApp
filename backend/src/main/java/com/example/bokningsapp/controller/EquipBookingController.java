package com.example.bokningsapp.controller;


import com.example.bokningsapp.dto.EquipBookingDto;
import com.example.bokningsapp.dto.UpdatedEquipBookingDto;
import com.example.bokningsapp.exception.BookingNotFoundException;
import com.example.bokningsapp.exception.EquipmentNotAvailableException;
import com.example.bokningsapp.exception.UnauthorizedUserException;
import com.example.bokningsapp.model.EquipmentBooking;
import com.example.bokningsapp.model.User;
import com.example.bokningsapp.repository.EquipBookingRepo;
import com.example.bokningsapp.repository.EquipmentRepo;
import com.example.bokningsapp.service.bookingService.EquipBookingService;
import com.example.bokningsapp.service.equipmentService.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class EquipBookingController {
    private final EquipBookingService equipBookingService;

    @Autowired
    public EquipBookingController(EquipBookingService equipBookingService) {
        this.equipBookingService = equipBookingService;
    }

    @PostMapping("/createBooking")
    public ResponseEntity<EquipmentBooking>createBooking(@Validated @RequestBody EquipBookingDto booking){
        try {
            EquipmentBooking equipmentBooking = equipBookingService.createBooking(booking);
            return new ResponseEntity<>(equipmentBooking,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateBooking/{id}")
    public ResponseEntity<EquipmentBooking> updateBooking(@PathVariable int id, @RequestBody UpdatedEquipBookingDto updatedEquipmentBookingDto, @AuthenticationPrincipal User user) {
        try {
          equipBookingService.updateBooking(id, updatedEquipmentBookingDto, user);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (BookingNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        }
        catch (EquipmentNotAvailableException ex) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

  /*  @GetMapping("/user/{userId}/equipmentType/{equipmentType}")
    public ResponseEntity<List<EquipmentBooking>> getBookingsByUserAndEquipment(@PathVariable Long userId, @PathVariable EquipmentType equipmentType) {
        List<EquipmentBooking> bookings = equipBookingService.findAllByUserIdAndEquipmentType(userId, equipmentType);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    } */
}
