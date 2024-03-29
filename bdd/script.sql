CREATE TABLE client (
	id_client int IDENTITY,
	nom varchar(50),
	prenom varchar(50),
	numTel varchar(50),
	nbPointFidelite INTEGER
);

CREATE TABLE facture(
	id_facture int IDENTITY,
	id_client int,
	est_paye boolean
);

CREATE TABLE forfait(
	id_forfait int IDENTITY,
	id_client int,
	nb_heure int,
	date_debut DATE,
	date_fin DATE,
	montant int,
	type_salle varchar(250)
	
);

CREATE TABLE Reservation(
	id_reservation int IDENTITY,
	id_facture int,
	id_salle int,
	nb_heure int,
	date_debut TIMESTAMP,
	date_fin TIMESTAMP,
	type_reservation int
);

CREATE TABLE Salle(
	id_salle int IDENTITY,
	description varchar(250),
	type_salle varchar(250)
);
