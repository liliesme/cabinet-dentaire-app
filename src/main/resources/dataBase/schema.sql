-- FULL SCHEMA (users/staff/medecin/secretaire + roles ...

CREATE TABLE IF NOT EXISTS adresse (
  id_adresse BIGINT PRIMARY KEY AUTO_INCREMENT,
  rue VARCHAR(255),
  ville VARCHAR(100),
  code_postal VARCHAR(20),
  pays VARCHAR(100) DEFAULT 'Maroc',
  complement VARCHAR(255),
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cabinet_medicale (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(200) NOT NULL,
  email VARCHAR(160),
  logo VARCHAR(255),
  tel1 VARCHAR(40),
  tel2 VARCHAR(40),
  site_web VARCHAR(255),
  instagram VARCHAR(100),
  facebook VARCHAR(100),
  description TEXT,
  adresse_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_cabinet_adresse FOREIGN KEY (adresse_id) REFERENCES adresse(id_adresse) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS role (
  id_role BIGINT PRIMARY KEY AUTO_INCREMENT,
  libelle VARCHAR(100) NOT NULL UNIQUE,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64)
);



CREATE TABLE IF NOT EXISTS charges (
  id_charge BIGINT PRIMARY KEY AUTO_INCREMENT,
  titre VARCHAR(200) NOT NULL,
  description TEXT,
  montant DECIMAL(12,2) NOT NULL,
  date DATETIME NOT NULL,
  cabinet_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_charges_cabinet FOREIGN KEY (cabinet_id) REFERENCES cabinet_medicale(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS revenues (
  id_revenue BIGINT PRIMARY KEY AUTO_INCREMENT,
  titre VARCHAR(200) NOT NULL,
  description TEXT,
  montant DECIMAL(12,2) NOT NULL,
  date DATETIME NOT NULL,
  cabinet_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_revenues_cabinet FOREIGN KEY (cabinet_id) REFERENCES cabinet_medicale(id) ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS utilisateur (
  id_user BIGINT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(120) NOT NULL,
  prenom VARCHAR(120) NOT NULL,
  email VARCHAR(160) NOT NULL UNIQUE,
  tel VARCHAR(40),
  cin VARCHAR(32) UNIQUE,
  sexe ENUM('Homme','Femme') DEFAULT 'Homme',
  login VARCHAR(64) NOT NULL UNIQUE,
  mot_de_passe VARCHAR(255) NOT NULL,
  date_login_date DATE,
  date_naissance DATE,
  adresse_id BIGINT,
  role_id BIGINT,
  cabinet_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64),
  CONSTRAINT fk_utilisateur_adresse FOREIGN KEY (adresse_id) REFERENCES adresse(id_adresse) ON DELETE SET NULL,
  CONSTRAINT fk_utilisateur_role FOREIGN KEY (role_id) REFERENCES role(id_role) ON DELETE SET NULL,
  CONSTRAINT fk_utilisateur_cabinet FOREIGN KEY (cabinet_id) REFERENCES cabinet_medicale(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS staff (
  id_staff BIGINT PRIMARY KEY,
  salaire DECIMAL(12,2) DEFAULT 0,
  prime DECIMAL(12,2) DEFAULT 0,
  date_recrutement DATE,
  date_depart DATE,
  CONSTRAINT fk_staff_utilisateur FOREIGN KEY (id_staff) REFERENCES utilisateur(id_user) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS medecin (
  id_medecin BIGINT PRIMARY KEY,
  specialite VARCHAR(100),
  agenda_mensuel VARCHAR(255),
  CONSTRAINT fk_medecin_staff FOREIGN KEY (id_medecin) REFERENCES staff(id_staff) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS secretaire (
  id_secretaire BIGINT PRIMARY KEY,
  num_cnss VARCHAR(50) UNIQUE,
  commission DECIMAL(12,2) DEFAULT 0,
  CONSTRAINT fk_secretaire_staff FOREIGN KEY (id_secretaire) REFERENCES staff(id_staff) ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS patient (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(120) NOT NULL,
  prenom VARCHAR(120) NOT NULL,
  adresse VARCHAR(255),
  telephone VARCHAR(40),
  email VARCHAR(160),
  date_naissance DATE,
  date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  sexe ENUM('Homme','Femme') DEFAULT 'Homme',
  assurance ENUM('CNOPS','CNSS','Autre','Aucune') DEFAULT 'Aucune'
);

CREATE TABLE IF NOT EXISTS antecedents (
  id_antecedent BIGINT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(200) NOT NULL,
  categorie VARCHAR(100),
  niveau_de_base DATETIME,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS agenda (
  id_agenda BIGINT PRIMARY KEY AUTO_INCREMENT,
  annee INT NOT NULL,
  mois INT NOT NULL CHECK (mois BETWEEN 1 AND 12),
  heure_debut TIME,
  heure_fin TIME,
  duree_consultation INT,
  medecin_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_agenda_medecin FOREIGN KEY (medecin_id) REFERENCES medecin(id_medecin) ON DELETE CASCADE,
  UNIQUE KEY uk_agenda_medecin_annee_mois (medecin_id, annee, mois)
);


CREATE TABLE IF NOT EXISTS rdv (
  id_rdv BIGINT PRIMARY KEY AUTO_INCREMENT,
  date DATE NOT NULL,
  heure TIME NOT NULL,
  motif VARCHAR(255),
  statut ENUM('PLANIFIE','ANNULE','TERMINE') DEFAULT 'PLANIFIE',
  note_rdv_medecin TEXT,
  patient_id BIGINT,
  medecin_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_rdv_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
  CONSTRAINT fk_rdv_medecin FOREIGN KEY (medecin_id) REFERENCES medecin(id_medecin) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS medicament (
  id_med BIGINT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(200) NOT NULL,
  laboratoire VARCHAR(150),
  type_dosage VARCHAR(100),
  forme ENUM('COMPRIME','SIROP','GELULE','INJECTABLE','POMMADE','CREME','AUTRE'),
  remboursable BOOLEAN DEFAULT FALSE,
  posologie TEXT,
  description TEXT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS acte (
  id_acte BIGINT PRIMARY KEY AUTO_INCREMENT,
  libelle VARCHAR(200) NOT NULL,
  categorie VARCHAR(100),
  prix_de_base DECIMAL(12,2) NOT NULL,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS intervention_medecin (
  id_im BIGINT PRIMARY KEY AUTO_INCREMENT,
  prix_de_patient DECIMAL(12,2),
  nomb_delit INT,
  acte_id BIGINT,
  medecin_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_intervention_acte FOREIGN KEY (acte_id) REFERENCES acte(id_acte) ON DELETE SET NULL,
  CONSTRAINT fk_intervention_medecin FOREIGN KEY (medecin_id) REFERENCES medecin(id_medecin) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS dossier_medicale (
  id_dm BIGINT PRIMARY KEY AUTO_INCREMENT,
  date_de_creation DATE NOT NULL,
  patient_id BIGINT UNIQUE,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_dossier_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS consultation (
  id_consultation BIGINT PRIMARY KEY AUTO_INCREMENT,
  date DATE NOT NULL,
  statut ENUM('PLANIFIEE','EN_COURS','TERMINEE','ANNULEE') DEFAULT 'PLANIFIEE',
  observation_medecin TEXT,
  dossier_id BIGINT,
  intervention_id BIGINT,
  rdv_id BIGINT,
  medecin_id BIGINT,
  patient_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_consultation_dossier FOREIGN KEY (dossier_id) REFERENCES dossier_medicale(id_dm) ON DELETE CASCADE,
  CONSTRAINT fk_consultation_intervention FOREIGN KEY (intervention_id) REFERENCES intervention_medecin(id_im) ON DELETE SET NULL,
  CONSTRAINT fk_consultation_rdv FOREIGN KEY (rdv_id) REFERENCES rdv(id_rdv) ON DELETE SET NULL,
  CONSTRAINT fk_consultation_medecin FOREIGN KEY (medecin_id) REFERENCES medecin(id_medecin) ON DELETE SET NULL,
  CONSTRAINT fk_consultation_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS certificat (
  id_certif BIGINT PRIMARY KEY AUTO_INCREMENT,
  date_debut DATE NOT NULL,
  date_fin DATE NOT NULL,
  duree INT,
  note_medecin TEXT,
  consultation_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_certificat_consultation FOREIGN KEY (consultation_id) REFERENCES consultation(id_consultation) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ordonnance (
  id_ord BIGINT PRIMARY KEY AUTO_INCREMENT,
  date DATE NOT NULL,
  consultation_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_ordonnance_consultation FOREIGN KEY (consultation_id) REFERENCES consultation(id_consultation) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS prescription (
  id_pr BIGINT PRIMARY KEY AUTO_INCREMENT,
  description TEXT,
  frequence INT,
  duree_en_jours INT,
  ordonnance_id BIGINT,
  medicament_id BIGINT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_prescription_ordonnance FOREIGN KEY (ordonnance_id) REFERENCES ordonnance(id_ord) ON DELETE CASCADE,
  CONSTRAINT fk_prescription_medicament FOREIGN KEY (medicament_id) REFERENCES medicament(id_med) ON DELETE SET NULL
);



CREATE TABLE IF NOT EXISTS facture (
  id_facture BIGINT PRIMARY KEY AUTO_INCREMENT,
  total_facture DECIMAL(12,2) NOT NULL,
  total_paye DECIMAL(12,2) DEFAULT 0,
  reste DECIMAL(12,2) GENERATED ALWAYS AS (total_facture - total_paye) STORED,
  statut ENUM('IMPAYEE','PAYEE_PARTIELLEMENT','PAYEE','ANNULEE') DEFAULT 'IMPAYEE',
  date_facture DATETIME DEFAULT CURRENT_TIMESTAMP,
  patient_id BIGINT,
  CONSTRAINT fk_facture_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS situation_financiere (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  total_des_actes DECIMAL(12,2) NOT NULL DEFAULT 0,
  totale_paye DECIMAL(12,2) NOT NULL DEFAULT 0,
  credit DECIMAL(12,2) GENERATED ALWAYS AS (total_des_actes - totale_paye) STORED,
  statut ENUM('SOLDE','EN_ATTENTE','IMPAYE','ECHEANCE') DEFAULT 'EN_ATTENTE',
  observation TEXT,
  patient_id BIGINT UNIQUE,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_situation_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);


CREATE INDEX idx_utilisateur_login ON utilisateur(login);
CREATE INDEX idx_utilisateur_email ON utilisateur(email);
CREATE INDEX idx_utilisateur_cabinet ON utilisateur(cabinet_id);
CREATE INDEX idx_patient_nom ON patient(nom);
CREATE INDEX idx_patient_telephone ON patient(telephone);
CREATE INDEX idx_rdv_date ON rdv(date);
CREATE INDEX idx_rdv_statut ON rdv(statut);
CREATE INDEX idx_consultation_date ON consultation(date);
CREATE INDEX idx_consultation_medecin ON consultation(medecin_id);
CREATE INDEX idx_consultation_patient ON consultation(patient_id);
CREATE INDEX idx_facture_statut ON facture(statut);
CREATE INDEX idx_facture_date ON facture(date_facture);
CREATE INDEX idx_charges_date ON charges(date);
CREATE INDEX idx_revenues_date ON revenues(date);
CREATE INDEX idx_medicament_nom ON medicament(nom);
CREATE INDEX idx_acte_categorie ON acte(categorie);
CREATE INDEX idx_situation_statut ON situation_financiere(statut);
