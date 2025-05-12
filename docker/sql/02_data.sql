-- 1. Insertar provincias
INSERT INTO provinces (name, normalized_name) VALUES
                                                  ('Jaén', 'jaen'),
                                                  ('Madrid', 'madrid'),
                                                  ('Barcelona', 'barcelona'),
                                                  ('Sevilla', 'sevilla'),
                                                  ('Valencia', 'valencia');

-- 2. Insertar municipios
INSERT INTO municipalities (name, province_id, normalized_name) VALUES
-- Municipios de Jaén
('Jaén', 1, 'jaen'),
('Linares', 1, 'linares'),
('Úbeda', 1, 'ubeda'),
('Andújar', 1, 'andujar'),
('Martos', 1, 'martos'),
-- Municipios de otras provincias
('Madrid', 2, 'madrid'),
('Alcalá de Henares', 2, 'alcala de henares'),
('Barcelona', 3, 'barcelona'),
('Hospitalet de Llobregat', 3, 'hospitalet de llobregat'),
('Sevilla', 4, 'sevilla'),
('Dos Hermanas', 4, 'dos hermanas'),
('Valencia', 5, 'valencia'),
('Torrent', 5, 'torrent');

-- 3. Insertar usuarios (contraseña para todos los usarios: password123)
INSERT INTO users (first_name, last_name, email, password, telephone, avatar, description, role) VALUES
                                                                                               ('Juan', 'García', 'juan@email.com', '$2a$10$GDf2uEdY7QZej.Ts8X7zEebINElnvgEGY/78Z6atxvGfc7BCwL3li', '600111222', 'default-avatar.png', 'Amante del fútbol y pádel', 'USER'),
                                                                                               ('María', 'López', 'maria@email.com', '$2a$10$GDf2uEdY7QZej.Ts8X7zEebINElnvgEGY/78Z6atxvGfc7BCwL3li', '600222333', 'default-avatar.png', 'Jugadora de baloncesto los fines de semana', 'USER'),
                                                                                               ('Carlos', 'Martínez', 'carlos@email.com', '$2a$10$GDf2uEdY7QZej.Ts8X7zEebINElnvgEGY/78Z6atxvGfc7BCwL3li', '600333444', 'default-avatar.png', 'Organizo partidos de tenis regularmente', 'USER'),
                                                                                               ('Ana', 'Rodríguez', 'ana@email.com', '$2a$10$GDf2uEdY7QZej.Ts8X7zEebINElnvgEGY/78Z6atxvGfc7BCwL3li', '600444555', 'default-avatar.png', 'Nueva en la ciudad buscando grupo para deportes', 'USER'),
                                                                                               ('David', 'Sánchez', 'david@email.com', '$2a$10$GDf2uEdY7QZej.Ts8X7zEebINElnvgEGY/78Z6atxvGfc7BCwL3li', '600555666', 'default-avatar.png', 'Entrenador de fútbol sala buscando jugadores', 'USER');

-- 4. Insertar deportes
INSERT INTO sports (name, icon, alt) VALUES
                                                 ('Fútbol', 'soccer-ball.png', 'Icono de fulbol'),
                                                 ('Baloncesto', 'basketball_icon.png','Icono de baloncesto'),
                                                 ('Tenis', 'tennis.png','Icono de tenis'),
                                                 ('Pádel', 'padel.png','Icono de padel'),
                                                 ('Voleibol', 'volleyball.png','Icono de volleyball'),
                                                 ('Calistenia', 'calistenia.png','Icono de calistenia');

-- 5. Insertar instalaciones deportivas (con coordenadas aproximadas)
INSERT INTO sport_facilities (name, municipality_id, street, description, latitude, longitude) VALUES
-- Instalaciones en Jaén
('Polideportivo La Salobreja', 1, 'Calle Deportes, 12', 'Polideportivo municipal con pistas de pádel y tenis', 37.7749, -3.7903),
('Pabellón Municipal de Linares', 2, 'Av. de Madrid, 45', 'Pabellón cubierto para baloncesto y voleibol', 38.0934, -3.6365),
('Pistas de Tenis Úbeda', 3, 'Ctra. de Granada, km 2', '4 pistas de tenis al aire libre', 38.0133, -3.3705),
('Complejo Deportivo Andújar', 4, 'Calle Nueva, 22', 'Pistas municipales al aire libre', 38.0392, -4.0506),
-- Instalaciones en otras provincias
('Polideportivo Moratalaz', 6, 'Calle del Arroyo Belincoso, 7', 'Instalaciones modernas para múltiples deportes', 40.4076, -3.6493),
('Pabellón Olímpico de Badalona', 9, 'Av. Alfons XIII, 6', 'Pabellón de baloncesto de alto nivel', 41.4339, 2.2280),
('Club de Tenis Valencia', 13, 'Av. de las Cortes Valencianas, 57', 'Club privado con excelentes instalaciones', 39.4699, -0.3763);

-- 6. Insertar imágenes de instalaciones
INSERT INTO facility_pictures (filename, facility_id, alt) VALUES
                                                          ('polideportivo-jaen1.jpg', 1, 'Polideportivo de Jaén imagen 1'),
                                                          ('polideportivo-jaen2.jpg', 1, 'Polideportivo Jaén imagen 2'),
                                                          ('pabellon-linares1.jpg', 2, 'Pabellón Linares imagen 1'),
                                                          ('pistas-tenis-ubeda1.jpg', 3, 'Pistas de tenis Úbeda imagen 1'),
                                                          ('complejo-andujar1.jpg', 4, 'Complejo Andújar imagen 1'),
                                                          ('polideportivo-moratalaz1.jpg', 5, 'Polideportivo Moratalaz imagen 1'),
                                                          ('pabellon-badalona1.jpg', 6, 'Pabellón Badalona imagen 1'),
                                                          ('club-tenis-valencia1.jpg', 7, 'Club Tenis Valencia imagen 1');

-- 7. Insertar eventos/partidos
INSERT INTO matches (title, description, sport_id, facility_id, owner_id, event_date, end_date, price_per_person, max_participants, skill_level) VALUES
-- Eventos en Jaén
('Torneo de Pádel', 'Torneo amistoso de pádel nivel intermedio', 4, 1, 1, '2023-12-15 17:00:00', '2023-12-15 21:00:00', 10.00, 16, 'INTERMEDIO'),
('Fútbol 7 Semanal', 'Partido semanal de fútbol 7', 1, 2, 2, '2023-12-10 11:00:00', '2023-12-10 13:00:00', 5.00, 14, 'PRINCIPIANTE'),
('Clínica de Tenis', 'Sesión de entrenamiento con monitor', 3, 3, 3, '2023-12-12 18:00:00', '2023-12-12 20:00:00', 15.00, 8, 'PRINCIPIANTE'),
-- Eventos en otras provincias
('Baloncesto 3x3', 'Torneo rápido de baloncesto', 2, 5, 4, '2023-12-16 12:00:00', '2023-12-16 16:00:00', 3.50, 12, 'EXPERTO'),
('Partido Voleibol', 'Partido amistoso de voleibol', 5, 6, 5, '2023-12-17 10:00:00', '2023-12-17 12:00:00', 0.00, 12, 'PRINCIPIANTE');

-- 8. Asociar usuarios a municipios de interés
INSERT INTO user_municipalities (user_id, municipality_id) VALUES
-- Intereses de Juan
(1, 1), -- Jaén
(1, 2), -- Linares
-- Intereses de María
(2, 1), -- Jaén
(2, 6), -- Madrid
-- Intereses de Carlos
(3, 3), -- Úbeda
(3, 9), -- Barcelona
-- Intereses de Ana
(4, 6), -- Madrid
(4, 13), -- Valencia
-- Intereses de David
(5, 1), -- Jaén
(5, 10); -- Sevilla

-- 9. Registrar participantes en eventos
INSERT INTO match_participants (match_id, user_id) VALUES
-- Participantes en Torneo de Pádel (evento 1)
(1, 2), -- María
(1, 3), -- Carlos
-- Participantes en Fútbol 7 (evento 2)
(2, 1), -- Juan
(2, 3), -- Carlos
(2, 5), -- David
-- Participantes en Clínica de Tenis (evento 3)
(3, 1), -- Juan
(3, 4), -- Ana
-- Participantes en Baloncesto 3x3 (evento 4)
(4, 2), -- María
(4, 5), -- David
-- Participantes en Voleibol (evento 5)
(5, 1), -- Juan
(5, 3), -- Carlos
(5, 4); -- Ana