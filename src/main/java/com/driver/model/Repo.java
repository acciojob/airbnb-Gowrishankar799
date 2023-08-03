package com.driver.model;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


public class Repo {
    HashMap<String,Hotel> hotel = new HashMap<>();
    HashMap<Integer,User> user = new HashMap<>();
    HashMap<String,Booking> bookinghashMap = new HashMap<>();
    public String addHotel(Hotel hot) {
      /* if(hot.getHotelName() == null){
            return "FAILURE";
        }
        if(hot == null) return "FAILURE";
        for(Hotel ho : hotel.values()){
            if(ho.getHotelName().equals(hot.getHotelName())){
                return "FAILURE";
            }
        }
        hotel.put(hot.getHotelName(),hot);
        return "SUCCESS";*/
      / if (hot.getHotelName() == null)return "FAILURE";
        if (hotel.containsKey(hot.getHotelName()))return "FAILURE";
        String hotelName = hot.getHotelName();
        hotel.put(hotelName, hotel);
        return "SUCCESS";
    }

    public Integer addUser(User us){
        if(user.containsKey(us.getaadharCardNo())) return null;
        user.put(us.getaadharCardNo(),us);
        return us.getaadharCardNo();
    }
    public String getHotelWithMostFacilities(){
        int max = Integer.MAX_VALUE;
        for(Hotel h : hotel.values()){
            List<Facility> list = h.getFacilities();
            if(max>list.size()){
                max = list.size();
            }
        }
        List<String>al = new ArrayList<>();
        if(max == 0) return "";
        for(Hotel h : hotel.values()){
            List<Facility>list = h.getFacilities();
            if(list.size() == max)
                al.add(h.getHotelName());
        }
        Collections.sort(al);
        return al.get(0);
    }
    public int bookARoom(Booking booking){
        if(!hotel.containsKey(booking.getHotelName())){
            return -1;
        }
        Hotel h = hotel.get(booking.getHotelName());
        if(h.getAvailableRooms()>=booking.getNoOfRooms()){
            int remainingrooms = h.getAvailableRooms()-booking.getNoOfRooms();
            h.setAvailableRooms(remainingrooms);
            String name = h.getHotelName();
            hotel.put(name,h);
            String ss = UUID.randomUUID()+"";
            int amount = booking.getNoOfRooms()*h.getPricePerNight();
            booking.setBookingId(ss);
            booking.setAmountToBePaid(amount);
            bookinghashMap.put(ss,booking);
            return amount;
        }
        return -1;

    }
    public int getBookings(int a){
        int count = 0;
        for(Booking b : bookinghashMap.values()){
            if(b.getBookingAadharCard() == a){
                count++;
            }
        }
        return count;
    }
    public Hotel updateFacilities(List<Facility>list,String h){
        if(!hotel.containsKey(h)){
            return null;
        }
        Hotel hot = hotel.get(h);
        List<Facility>al = hot.getFacilities();
        for(int i = 0; i<list.size();i++){
            if(!al.contains(list.get(i))){
                al.add(list.get(i));
            }
        }
        hot.setFacilities(al);
        String s = hot.getHotelName();
        hotel.put(s,hot);
        return hot;
    }

}
