package com.example.bokningsapp.controller;


import com.example.bokningsapp.dto.BookingRequest;
import com.example.bokningsapp.model.Equipment;
import com.example.bokningsapp.model.EquipmentBooking;
import com.example.bokningsapp.repository.EquipBookingRepository;
import com.example.bokningsapp.repository.EquipmentRepository;
import com.example.bokningsapp.security.payload.response.BookingResponse;
import com.example.bokningsapp.service.bookingService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.*;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class BookingController {

    private static final Logger LOGGER = Logger.getLogger(BookingController.class.getName());
    private final BookingService bookingService;
    private final EquipBookingRepository equipBookingRepository;
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public BookingController(BookingService bookingService, EquipBookingRepository equipBookingRepository, EquipmentRepository equipmentRepository) {
        this.bookingService = bookingService;
        this.equipBookingRepository = equipBookingRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @PostMapping("/bookEquipment")
    public ResponseEntity <BookingResponse> createEquipmentBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.placeEquipmentBooking(bookingRequest));

        //NEXT STEP: GET CURRENTLY LOGGED IN USER AND ASSOCIATE BOOKING WITH THAT USER
        //VALIDATE BOOKING REQUEST, CHECK FOR AVAILABILITY
    }
    @GetMapping("/getBookingsOnEquipment/{equipmentId}")
    public List<EquipmentBooking> getBookingsForEquipment (@PathVariable int equipmentId) {
        List<EquipmentBooking> booking = equipBookingRepository.findByEquipmentId(equipmentId);
        LOGGER.info("booking" + booking);
        return booking;
    }

    @GetMapping("/allEquipmentBookings")
    public List <EquipmentBooking> getAllEquipmentBookings(){
        List <EquipmentBooking> bookings = equipBookingRepository.findAll();
        return bookings;
    }
}
