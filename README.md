# WildLife Tracker


# The Wildlife Tracker

#### Is an app where users enter a sighting of certain wildlife they see in an area.You can register as a ranger and do even more.

#### By
Jasper Migiro only.

## Description
Is an app where users enter a sighting of certain wildlife they see in an area.You can register as a ranger. It uses pre-selected location and animals but you can add a new animal you have noticed on your own which is not on the list.

##Database Setup
﻿CREATE TABLE "normalanimal" (
	"id" serial,
	"name" varchar(255) NOT NULL,
	"creation" TIMESTAMP NOT NULL,
	CONSTRAINT "normalanimal_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Ranger" (
	"id" serial,
	"name" varchar(255) NOT NULL,
	"password" varchar(255),
	"number" varchar(255),
	"no_of_animals" integer,
	"no_of_endageredanimals" integer,
	"creationdate" TIMESTAMP(6),
	CONSTRAINT "Ranger_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "sightings" (
	"id" serial,
	"location" integer NOT NULL,
	"creation" TIMESTAMP(6) NOT NULL,
	"ranger_id" integer NOT NULL,
	"animal_id" integer,
	"endangeredanimal_id" integer,
	CONSTRAINT "sightings_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "endangeredanimals" (
	"id" serial NOT NULL,
	"name" varchar(255),
	"health" varchar(255) NOT NULL,
	"age" varchar(255) NOT NULL,
	"creation" TIMESTAMP NOT NULL,
	CONSTRAINT "endangeredanimals_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "location" (
	"id" serial NOT NULL,
	"place" varchar(255) NOT NULL,
	"timesvisited" varchar(255),
	CONSTRAINT "location_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);





ALTER TABLE "sightings" ADD CONSTRAINT "sightings_fk0" FOREIGN KEY ("location") REFERENCES "location"("id");
ALTER TABLE "sightings" ADD CONSTRAINT "sightings_fk1" FOREIGN KEY ("ranger_id") REFERENCES "Ranger"("id");
ALTER TABLE "sightings" ADD CONSTRAINT "sightings_fk2" FOREIGN KEY ("animal_id") REFERENCES "normalanimal"("id");
ALTER TABLE "sightings" ADD CONSTRAINT "sightings_fk3" FOREIGN KEY ("endangeredanimal_id") REFERENCES "endangeredanimals"("id");





## Setup/Installation Requirements
1.PC With JVM and Java
2. IDE or Notepad
3. Command Line Interface
4.Web Browser



## Known Bugs
No bugs. Incase of any you can contact me below.
## Technologies Used
Java

## Support and contact details
Contact:
Email:jkmigiro@gmail.com
Mobile number:0724036732
Incase of any bugs or improvements recommended, my contacts are above.
### License
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
