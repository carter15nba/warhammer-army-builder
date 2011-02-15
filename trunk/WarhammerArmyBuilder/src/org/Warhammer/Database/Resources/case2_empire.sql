--Empire case 1
insert into CASES(CASEID,ARMYPOINTS,OPPONENTRACE,OUTCOME,PLAYERRACE)
 values(2,-1,'Wood_Elves','Victory','Empire');
---Units
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(16,'Templar Grand Master',1,145,'4','6','3','4','4','3','6','4','9','In','Lord');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(17,'Warrior Priest',1,90,'4','4','3','4','4','2','4','2','8','In','Hero');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(18,'Battle Wizard',2,65,'4','3','3','3','3','2','3','1','7','In','Hero');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(19,'Captain of the Empire',1,50,'4','5','5','4','4','2','5','3','8','In','Hero');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(20,'Halberdiers',40,5,'4','3','3','3','3','1','3','1','7','In','Core');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(21,'Greatswords',20,10,'4','4','3','3','3','1','3','1','8','In','Special');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(22,'Knightly Orders',23,12,'4','4','3','3','3','1','3','1','8','Ca','Core');
insert into UNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE,ARMYTYPE)
 values(23,'Outriders',21,5,'4','3','4','3','3','1','3','1','7','Ca','Special');

---Equipment
insert into EQUIPMENT(EQUIPMENTID,TYPE,COST,NAME)
 values(22,'One unit upgrade',25,'Battle standard bearer');
 

---Assign equipment to Unit
insert into UNIT_EQUIPMENT values(19,22);

--UtilityUnits
insert into UTILITYUNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE)
 values(4,'Barded warhorse',1,0,'8','3','0','3','3','1','3','1','5','_na');
insert into UTILITYUNIT(ID,NAME,NUMBER,COST,MOVEMENT,WEAPONSKILL,BALLISTICSKILL,STRENGTH,TOUGHNESS,WOUNDS,INITIATIVE,ATTACK,LEADERSHIP,UNITTYPE)
 values(5,'Warhorse',1,0,'8','3','0','3','3','1','3','1','5','_na');

--Assign utilityunit to unit
insert into UNIT_UTILITY values(16,4);
insert into UNIT_UTILITY values(22,4);
insert into UNIT_UTILITY values(23,5);

---Assign unit to case
insert into CASE_UNITS values(2,9);
insert into CASE_UNITS values(2,10);
insert into CASE_UNITS values(2,16);
insert into CASE_UNITS values(2,17);
insert into CASE_UNITS values(2,18);
insert into CASE_UNITS values(2,19);
insert into CASE_UNITS values(2,20);
insert into CASE_UNITS values(2,21);
insert into CASE_UNITS values(2,22);
insert into CASE_UNITS values(2,23);