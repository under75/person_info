package ru.sartfoms.personinfo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ru.sartfoms.personinfo.entity.Address;
import ru.sartfoms.personinfo.entity.AsAddrObj;
import ru.sartfoms.personinfo.entity.House;
import ru.sartfoms.personinfo.repository.AddressRepository;
import ru.sartfoms.personinfo.repository.AsAddrObjRepository;
import ru.sartfoms.personinfo.repository.AsAdmHierarchyRepository;
import ru.sartfoms.personinfo.repository.HouseRepository;

@Service
public class AddressService {
	private final AddressRepository addressRepository;
	private final HouseRepository houseRepository;
	private final AsAddrObjRepository addrObjRepository;
	private final AsAdmHierarchyRepository admHierarchyRepository;

	public AddressService(AddressRepository addressRepository, HouseRepository houseRepository,
			AsAddrObjRepository addrObjRepository, AsAdmHierarchyRepository admHierarchyRepository) {
		this.addressRepository = addressRepository;
		this.houseRepository = houseRepository;
		this.addrObjRepository = addrObjRepository;
		this.admHierarchyRepository = admHierarchyRepository;
	}

	public Collection<Address> findAllByRid(Long rid) {
		return addressRepository.findAllByRid(rid);
	}

	public House getHouseByObjectguid(String hsguid) {
		return houseRepository.findByObjectguidAndIsActualAndIsActive(hsguid, true, true);
	}

	public AsAddrObj getAddrByObjectguid(String aoguid) {
		return addrObjRepository.findByObjectguidAndIsActualAndIsActive(aoguid, true, true);
	}

	public String getAddressChain(String aoguid) {
		AsAddrObj street = addrObjRepository.findByObjectguidAndIsActualAndIsActive(aoguid, true, true);
		String path = admHierarchyRepository.findByObjectidAndIsActive(street.getObjectid(), true).getPath();
		String[] objidsStr = path.split("\\.");
		Collection<String> list = new ArrayList<>();
		for (String objidStr : objidsStr) {
			Long objid = Long.valueOf(objidStr);
			AsAddrObj parent = addrObjRepository.findByObjectidAndIsActualAndIsActive(objid, true, true);
			if (parent.getLevel() > 2)
				list.add(parent.getName() + " " + parent.getTypename());
		}

		return StringUtils.collectionToCommaDelimitedString(list);
	}
}
