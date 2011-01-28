
create table Unit (ID int not null generated always as identity,
name varchar(40),
cost int not null,
movement varchar(4),
weaponSkill varchar(4),
ballisticSkill varchar(4),
strength varchar(4),
toughness varchar(4),
wounds varchar(4),
initiative varchar(4),
attack varchar(4),
leadership varchar(4),
unitType varchar(2),
armyType varchar(7));

create table equipment (ID int generated always as identity,
cost int,
name varchar(30),
type varchar(15));

create table unitEquipment(unitID int, equipmentID int);

create table cases(ID int not null generated always as identity,
ArmyPoints int,
race varchar(20),
victory varchar(20));

create table caseUnits(caseID int, unitID int);