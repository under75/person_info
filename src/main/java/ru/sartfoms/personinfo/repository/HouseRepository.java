package ru.sartfoms.personinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.House;
import ru.sartfoms.personinfo.entity.HouseId;

public interface HouseRepository extends JpaRepository<House, HouseId> {

	House findByObjectguidAndIsActualAndIsActive(String hsguid, boolean b, boolean c);

}
