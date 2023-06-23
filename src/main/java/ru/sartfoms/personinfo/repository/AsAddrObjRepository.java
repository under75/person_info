package ru.sartfoms.personinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.AsAddrObj;

public interface AsAddrObjRepository extends JpaRepository<AsAddrObj, Long> {

	AsAddrObj findByObjectguidAndIsActualAndIsActive(String aoguid, boolean b, boolean c);

	AsAddrObj findByObjectidAndIsActualAndIsActive(Long objid, boolean b, boolean c);

}
