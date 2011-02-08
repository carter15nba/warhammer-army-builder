--Empire case 1
insert into CASES(CASEID,ARMYPOINTS,OPPONENTRACE,OUTCOME,PLAYERRACE)
 values(1,2500,'Bretonnia','Defeat','Empire');
---Units
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(1,'Arch Lector of Sigmar',1,125,'4','4','3','4','4','3','4','2','9','In','Lord');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(2,'Gold Wizard Lord',1,175,'4','3','3','3','4','3','3','1','8','In','Lord');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(3,'Captain of the Empire',1,50,'4','5','5','4','4','2','5','3','8','In','Hero');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(4,'Warrior Priest',1,90,'4','4','3','4','4','2','4','2','8','In','Hero');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(5,'Master Engineer',1,65,'4','3','4','3','3','2','3','1','7','In','Hero');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(6,'Halberdiers',38,5,'4','3','3','3','3','1','3','1','7','In','Core');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(7,'Spearmen',20,5,'4','3','3','3','3','1','3','1','7','In','Core');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(8,'Swordsmen',20,6,'4','4','3','3','3','1','4','1','7','In','Core');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(9,'Crossbowmen',10,8,'4','3','3','3','3','1','3','1','7','In','Core');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(10,'Handgunners',10,8,'4','3','3','3','3','1','3','1','7','In','Core');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(11,'Greatswords',19,10,'4','4','3','3','3','1','3','1','8','In','Special');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(12,'Mortar',2,75,'0','0','0','0','7','3','0','0','0','WM','Special');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(13,'Great Canon',2,100,'0','0','0','0','7','3','0','0','0','WM','Special');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(14,'Helstorm Rocket Battery',1,115,'0','0','0','0','7','3','0','0','0','WM','Rare');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(15,'Steam Tank',1,300,'special','0','0','6','6','10','0','special','0','Un','Special');
---Equipment
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(1,'Mace',60, 'The Mace Of Helsturm');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(2,'Armour',20,'Armour of Fortune');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(3,'Potion',15,'Potion of Speed');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(4,'Upgrade',35,'Lvl 4');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(5,'Item',30,'Rod Of Power');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(6,'Item',15,'Opal Amulet');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(7,'Upgrade',25,'Battle Standard Bearer');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(8,'Armour',8,'Full Plate Armour');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(9, 'Standard',55,'Griffon Standard');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
  values(10,'Armour',25,'Armour of Meteoric Iron');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(11,'2xHand-Weapon',4,'Warhammer');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(12,'Ranged weapon',10,'Repeater Pistol');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(13,'One unit upgrade',10,'Sergeant');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(14,'One unit upgrade',10,'Battle standard bearer');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(15,'One unit upgrade',4,'Musician');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(16,'One unit upgrade',5,'Marksman');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(17,'One unit upgrade',15,'Repeater Handgun');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(18,'One unit upgrade',12,'Counts champion');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(19,'One unit upgrade',12,'Battle standard bearer');
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(20,'One unit upgrade',6,'Musician');
---Assign equipment to Unit
insert into UNIT_EQUIPMENT values(1,1);
insert into UNIT_EQUIPMENT values(1,2);
insert into UNIT_EQUIPMENT values(1,3);
insert into UNIT_EQUIPMENT values(2,4);
insert into UNIT_EQUIPMENT values(2,5);
insert into UNIT_EQUIPMENT values(2,6);
insert into UNIT_EQUIPMENT values(3,7);
insert into UNIT_EQUIPMENT values(3,8);
insert into UNIT_EQUIPMENT values(3,9);
insert into UNIT_EQUIPMENT values(4,10);
insert into UNIT_EQUIPMENT values(4,11);
insert into UNIT_EQUIPMENT values(5,12);
insert into UNIT_EQUIPMENT values(6,13);
insert into UNIT_EQUIPMENT values(6,14);
insert into UNIT_EQUIPMENT values(6,15);
insert into UNIT_EQUIPMENT values(7,13);
insert into UNIT_EQUIPMENT values(7,14);
insert into UNIT_EQUIPMENT values(7,15);
insert into UNIT_EQUIPMENT values(8,13);
insert into UNIT_EQUIPMENT values(8,14);
insert into UNIT_EQUIPMENT values(8,15);
insert into UNIT_EQUIPMENT values(10,16);
insert into UNIT_EQUIPMENT values(10,17);
insert into UNIT_EQUIPMENT values(11,18);
insert into UNIT_EQUIPMENT values(11,19);
insert into UNIT_EQUIPMENT values(11,20);
--UtilityUnits
insert into UTILITYUNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE)
 values(1,'Barded steed',1,21,'5','3','3','3','2','3','4','4','3','_na');
insert into UTILITYUNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE)
 values(2,'Crewman',3,0,'4','3','3','3','3','1','3','1','7','_na');
insert into UTILITYUNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE)
 values(3,'Engineer Commander',1,0,'0','0','4','0','0','0','0','0','10','_na');
--Assign utilityunit to unit
insert into UNIT_UTILITY values(2,1);
insert into UNIT_UTILITY values(12,2);
insert into UNIT_UTILITY values(13,2);
insert into UNIT_UTILITY values(14,2);
insert into UNIT_UTILITY values(15,3);
---Assign unit to case
insert into CASE_UNITS values(1,1);
insert into CASE_UNITS values(1,2);
insert into CASE_UNITS values(1,3);
insert into CASE_UNITS values(1,4);
insert into CASE_UNITS values(1,5);
insert into CASE_UNITS values(1,6);
insert into CASE_UNITS values(1,7);
insert into CASE_UNITS values(1,8);
insert into CASE_UNITS values(1,9);
insert into CASE_UNITS values(1,10);
insert into CASE_UNITS values(1,11);
insert into CASE_UNITS values(1,12);
insert into CASE_UNITS values(1,13);
insert into CASE_UNITS values(1,14);
insert into CASE_UNITS values(1,15);