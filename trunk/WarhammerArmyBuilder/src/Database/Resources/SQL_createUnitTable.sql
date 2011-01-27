drop table UnitCase;

create table UnitCase (ID int not null generated always as identity,
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
armyType varchar(7),
unique(ID)
);

insert into UnitCase(name,cost, movement,weaponSkill,ballisticSkill,strength,toughness,wounds,initiative,attack,leadership,unitType,armyType) values('Battle Pilgrim',
125,
'4',
'2',
'2',
'3',
'3',
'1',
'3',
'1',
'8',
'In',
'Heroes'
)