package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.Address;
import ru.sartfoms.personinfo.entity.CompositeKey;

public interface AddressRepository extends JpaRepository<Address, CompositeKey> {

	Collection<Address> findAllByRid(Long rid);

}
