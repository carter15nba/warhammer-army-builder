--Empire case 1
----ArchLector
insert into Unit(name,cost, movement,weaponSkill,ballisticSkill,strength,toughness,wounds,initiative,attack,leadership,unitType,armyType) values('Arch Lector of Sigmar',125,'4','4','3','4','4','3','4','2','9','In','Lords');
insert into equipment(cost, name, type) values (60,'The Mace of Helstrum','Mace');
insert into equipment(cost,name,type)values(30,'Armour of Fortune','Armour');
insert into equipment(cost,name,type)values(10,'Potion of Speed','Potion');
insert into unitEquipment values(1,1);
insert into unitEquipment values(1,2);
insert into unitEquipment values(1,3);
----Gold Wizard Lord
insert into Unit(name,cost, movement,weaponSkill,ballisticSkill,strength,toughness,wounds,initiative,attack,leadership,unitType,armyType) values('Gold Wizard Lord',175,'4','3','3','3','4','3','3','1','8','In','Lords');
insert into equipment(cost, name, type) values (35,'Lvl 4','Upgrade');
insert into equipment(cost,name,type)values(30,'Rod of power','Arcane');
insert into equipment(cost,name,type)values(10,'Potion of Speed','Potion');
insert into unitEquipment values(2,4);
insert into unitEquipment values(2,5);
insert into unitEquipment values(2,6);

