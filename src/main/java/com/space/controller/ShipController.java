package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.SearchCriteria;
import com.space.repository.ShipSpecification;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ShipController {
    @Autowired
    private ShipService shipService;

    @RequestMapping(value = "/rest/ships", method = RequestMethod.GET)
    public ResponseEntity<List<Ship>> getShipsList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String planet,
            @RequestParam(required = false) ShipType shipType,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean isUsed,
            @RequestParam(required = false) Double minSpeed,
            @RequestParam(required = false) Double maxSpeed,
            @RequestParam(required = false) Integer minCrewSize,
            @RequestParam(required = false) Integer maxCrewSize,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "3") Integer pageSize,
            @RequestParam(required = false, defaultValue = "ID") ShipOrder order
            ) {
        ShipSpecification spec = new ShipSpecification(new SearchCriteria().setName(name).setPlanet(planet).setShipType(shipType).
                setAfter(after).setBefore(before).setUsed(isUsed).setMinSpeed(minSpeed).setMaxSpeed(maxSpeed).setMinCrewSize(minCrewSize).setMaxCrewSize(maxCrewSize).
                setMinRating(minRating).setMaxRating(maxRating));
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.DEFAULT_DIRECTION, order.getFieldName()));
        Page<Ship> all = shipService.findAll(spec, page);
        return ResponseEntity.ok(all.getContent());
    }


    @RequestMapping(value = "/rest/ships/count", method = RequestMethod.GET)
    public ResponseEntity<Long> getShipsCount(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String planet,
            @RequestParam(required = false) ShipType shipType,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean isUsed,
            @RequestParam(required = false) Double minSpeed,
            @RequestParam(required = false) Double maxSpeed,
            @RequestParam(required = false) Integer minCrewSize,
            @RequestParam(required = false) Integer maxCrewSize,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating
    ) {
        ShipSpecification spec = new ShipSpecification(new SearchCriteria().setName(name).setPlanet(planet).setShipType(shipType).
                setAfter(after).setBefore(before).setUsed(isUsed).setMinSpeed(minSpeed).setMaxSpeed(maxSpeed).setMinCrewSize(minCrewSize).setMaxCrewSize(maxCrewSize).
                setMinRating(minRating).setMaxRating(maxRating));
        Long count = shipService.countWithSpec(spec);
        return ResponseEntity.ok(count);
    }

    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Ship> deleteShip(@PathVariable String id) {
        Long myId;
        try{
            myId = Long.parseLong(id);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
        if(myId<=0)
            return ResponseEntity.badRequest().build();
        try{
            shipService.delete(myId);
            return ResponseEntity.ok().build();
                }catch(Exception e){
        return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/rest/ships", method = RequestMethod.POST)
    public ResponseEntity<Ship> createShip(
            @RequestBody Ship body
            ) {
        if(body.getName()==null || body.getPlanet()==null || body.getShipType()==null || body.getProdDate()==null|| body.getSpeed()==null|| body.getCrewSize()==null)
            return ResponseEntity.badRequest().build();
        if(body.getName().length()>50 || body.getPlanet().length()>50|| body.getName().equals("") || body.getPlanet().equals(""))
            return ResponseEntity.badRequest().build();
        double mySpeed = Math.round(body.getSpeed()*100)/100.00;
        if(mySpeed<0.01 || mySpeed>0.99)
            return ResponseEntity.badRequest().build();
        if(body.getCrewSize()<1||body.getCrewSize()>9999)
            return ResponseEntity.badRequest().build();
        if(body.getProdDate().getTime()<26192246400000L || body.getProdDate().getTime()>33103209600000L)
            return ResponseEntity.badRequest().build();
        if(body.getUsed()==null)
            body.setUsed(false);

        Double rating = (80*mySpeed*(body.getUsed()?0.5:1))/(3019-(body.getProdDate().getYear()+1900)+1);
        BigDecimal bd = new BigDecimal(Double.toString(rating));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        rating = bd.doubleValue();
        Ship ship = new Ship(body.getName(),body.getPlanet(),body.getShipType(),body.getProdDate(),body.getUsed(),mySpeed, body.getCrewSize(),rating);

        shipService.save(ship);
        return ResponseEntity.ok(ship);
    }

    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ship> getShip(@PathVariable Long id) {
        if (id <= 0)
            return ResponseEntity.badRequest().build();
        try {
            return ResponseEntity.of(shipService.get(id));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.POST)
    public ResponseEntity<Ship> updateShip(@PathVariable Long id, @RequestBody Ship body ) {
        Ship ship;
        if(id<=0)
            return ResponseEntity.badRequest().build();
        if(body.getName()!=null && (body.getName().equals("")||body.getName().length()>50))
            return ResponseEntity.badRequest().build();
        if(body.getPlanet()!=null && (body.getPlanet().equals("")||body.getPlanet().length()>50))
            return ResponseEntity.badRequest().build();
        if(body.getProdDate()!=null && (body.getProdDate().getTime()<26192246400000L || body.getProdDate().getTime()>33103209600000L))
            return ResponseEntity.badRequest().build();
        if(body.getCrewSize()!=null && (body.getCrewSize()<1||body.getCrewSize()>9999))
            return ResponseEntity.badRequest().build();
        if(body.getSpeed()!=null && (body.getSpeed()<0.01 || body.getSpeed()>0.99))
            return ResponseEntity.badRequest().build();

        try {
            ship = shipService.get(id).get();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
        if(body.getName()!=null)
            ship.setName(body.getName());
        if(body.getPlanet()!=null)
            ship.setPlanet(body.getPlanet());
        if(body.getShipType()!=null)
            ship.setShipType(body.getShipType());
        if(body.getProdDate()!=null)
            ship.setProdDate(body.getProdDate());
        if(body.getUsed()!=null)
            ship.setUsed(body.getUsed());
        if(body.getCrewSize()!=null)
            ship.setCrewSize(body.getCrewSize());
        double mySpeed=0.0;
        if(body.getSpeed()!=null ){
            mySpeed = Math.round(body.getSpeed()*100)/100.00;
            ship.setSpeed(mySpeed);
        }

        double rating = (80*(mySpeed==0.0?ship.getSpeed():mySpeed)*(ship.getUsed()?0.5:1))/(3019-(ship.getProdDate().getYear()+1900)+1);
        BigDecimal bd = new BigDecimal(Double.toString(rating));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        rating = bd.doubleValue();
        ship.setRating(rating);
        shipService.save(ship);
        return ResponseEntity.ok(ship);
    }

}
