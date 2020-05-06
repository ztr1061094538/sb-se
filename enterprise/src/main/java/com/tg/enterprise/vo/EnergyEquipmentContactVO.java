package com.tg.enterprise.vo;

import com.tg.enterprise.model.EnergyEquipmentContact;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnergyEquipmentContactVO extends EnergyEquipmentContact {
	private String EquipmentName;
	private String codeName;
	private String CodeUnitName;
	private String collectTypeName;
	private String categoryName;
	private String energyUseName;
	private String inputTypeName;
	private String TerminalName;
	private String statTypeName;

}
