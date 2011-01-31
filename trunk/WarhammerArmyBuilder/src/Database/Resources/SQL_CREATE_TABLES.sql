--Table to store every case.
create table cases(ID int not null generated always as identity,
ArmyPoints int,
PlayerRace varchar(25),
OpponentRace varchar(25),
Outcome varchar(25),
UNIQUE(ID));

--Many-to-may relationship table between cases and units
create table caseUnits(caseID int,
unitID int,
unitNumber int);

--Table to store a unit represented in the casebase.
create table Unit (ID int not null generated always as identity,
name varchar(40), 
cost int, -- The base cost of the unit (not counting equipment, upgrades or mounts/crew.
movement varchar(4),
weaponSkill varchar(4),
ballisticSkill varchar(4),
strength varchar(4),
toughness varchar(4),
wounds varchar(4),
initiative varchar(4),
attack varchar(4),
leadership varchar(4),
unitType varchar(2), --unit type represents if this unit is classified as infantry, cavalry, monster etc.
armyType varchar(7)); --army type represents if this unit is classified as core, rare, special, hero or lord

--Table to store a crew or mount represented in the casebase.
create table CrewMount(ID int not null generated always as identity,
name varchar(40),
cost int, -- The base cost of the crew/mount unit
movement varchar(4),
weaponSkill varchar(4),
ballisticSkill varchar(4),
strength varchar(4),
toughness varchar(4),
wounds varchar(4),
initiative varchar(4),
attack varchar(4),
leadership varchar(4),
unitType varchar(2), --unit type represents if this crew/mount is classified as infantry, cavalry, monster etc.
armyType varchar(7)); --army type represents if this crew/mount is classified as core, rare, special, hero or lord

--Many-to-many relationship table between units and crews/mounts
create table unitCrew(unitID int , crewID int);

--Table to store the equipment represented in the casebase
create table equipment (ID int generated always as identity,
cost int,
name varchar(30),
type varchar(15));

--Many-to-many relationshipt table betwen units and equipment.
create table unitEquipment(unitID int, equipmentID int);

