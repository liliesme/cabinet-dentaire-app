-- Jeu de données de Test
INSERT INTO role(libelle) VALUES ('ADMIN'), ('MEDECIN'), ('SECRETAIRE')
ON DUPLICATE KEY UPDATE libelle=VALUES(libelle);



INSERT INTO utilisateur (nom, prenom, email, login, mot_de_passe, role_id) VALUES
('Dr. Kebbaj', 'Ali', 'dr.Kebbaj@cabinet.ma', 'dr.Kebbaj', 'p235', 2),
('Rossafi', 'Houda', 'f.Rossafi@cabinet.ma', 'h.Rossafi', 'p123', 3);

INSERT INTO staff (id_staff, salaire, prime, date_recrutement) VALUES
(1, 15000.00, 2000.00, '2020-01-10'),
(2, 5000.00, 500.00, '2021-03-15');

INSERT INTO charges (titre, description, montant, date) VALUES
('Loyer', 'Loyer du cabinet - Novembre 2024', 7000.00, '2024-11-01 00:00:00'),
('Équipement dentaire', 'Achat de matériel médical', 15000.00, '2024-11-05 10:30:00'),
('Salaires personnel', 'Salaires du mois de novembre', 35000.00, '2024-11-30 00:00:00'),
('Électricité et eau', 'Factures services publics', 1200.00, '2024-11-15 00:00:00');

INSERT INTO revenues (titre, description, montant, date) VALUES
('Consultations', 'Revenus consultations - Semaine 1', 12000.00, '2024-11-07 00:00:00'),
('Interventions chirurgicales', 'Implants et extractions', 25000.00, '2024-11-14 00:00:00');

INSERT INTO patient (nom, prenom, adresse, telephone, email, date_naissance, sexe, assurance) VALUES
('Radi', 'ahmed', '15 Rue Oued Ziz, Casablanca', '0656789012', 'ahed.radi@email.com', '1975-04-12', 'Homme', 'CNOPS'),
('ouasmi', 'Amina', '22 Avenue Mers Sultan, Casablanca', '0667890123', 'amina.ouasmi@email.com', '1982-08-25', 'Femme', 'CNSS');


INSERT INTO antecedents (nom, categorie) VALUES
('Hypertension', 'Médical'),
('Problèmes cardiaques', 'Cardiaque');


INSERT INTO medecin (id_medecin, specialite) VALUES
(1, 'Chirurgie dentaire');


INSERT INTO secretaire (id_secretaire, num_cnss, commission) VALUES
(2, 'CNSS-2021-001', 500.00);


INSERT INTO agenda (annee, mois, heure_debut, heure_fin, duree_consultation, medecin_id) VALUES
(2024, 11, '09:00:00', '18:00:00', 30, 1),
(2024, 12, '09:00:00', '18:00:00', 30, 1);


INSERT INTO rdv (date, heure, motif, statut, patient_id, medecin_id) VALUES
('2024-11-20', '10:00:00', 'Mal de dents', 'TERMINE', 2, 1),
('2024-11-22', '09:30:00', 'Détartrage', 'PLANIFIE', 4, 1);

INSERT INTO medicament (nom, laboratoire, type_dosage, forme, remboursable, posologie, description) VALUES
('Paracétamol', 'lab1', '1000mg', 'COMPRIME', TRUE, '1 comprimé 3 fois par jour', 'Antalgique et antipyrétique'),
('Bain de bouche ', 'lab2', '0.12%', 'SIROP', FALSE, '2 fois par jour', 'Antiseptique buccal');


INSERT INTO acte (libelle, categorie, prix_de_base) VALUES
('Consultation dentaire', 'Consultation', 300.00),
('Détartrage', 'Hygiène', 400.00);

INSERT INTO intervention_medecin (prix_de_patient, nomb_delit, acte_id, medecin_id) VALUES
(300.00, 1, 1, 1),
(500.00, 1, 3, 1),
(1200.00, 1, 6, 1);


INSERT INTO dossier_medicale (date_de_creation, patient_id) VALUES
('2024-01-15', 1),
('2024-02-20', 2);


INSERT INTO consultation (date, statut, observation_medecin, dossier_id, intervention_id, rdv_id, medecin_id, patient_id) VALUES
('2024-11-16', 'TERMINEE', 'Extraction dent de sagesse .', 2, 2, 2, 2, 2),
('2024-11-15', 'TERMINEE', '- Gencives inflammées.', 1, 1, 1, 2, 1);



INSERT INTO ordonnance (date, consultation_id) VALUES
('2024-11-16', 2),
('2024-11-17', 3);


INSERT INTO prescription (description, frequence, duree_en_jours, ordonnance_id, medicament_id) VALUES
('Prendre après extraction', 3, 7, 1, 1),
('Rincer matin et soir', 2, 7, 2, 4);

INSERT INTO certificat (date_debut, date_fin, duree, note_medecin, consultation_id) VALUES
('2024-11-16', '2024-11-18', 3, 'repos suite à extraction dentaire', 2);


INSERT INTO facture (total_facture, total_paye, statut, patient_id) VALUES
(300.00, 300.00, 'PAYEE', 1),
(500.00, 250.00, 'PAYEE_PARTIELLEMENT', 2);



INSERT INTO situation_financiere (total_des_actes, totale_paye, statut, observation, patient_id) VALUES
(3200.00, 1800.00, 'EN_ATTENTE', 'Paiement partiel reçu, solde en cours', 2),
(2800.00, 0.00, 'IMPAYE', 'Aucun paiement reçu, relance nécessaire', 3);




