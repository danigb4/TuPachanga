-- Generar Random UUID para Users
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tabla de provincias
CREATE TABLE provinces (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL UNIQUE,
                           normalized_name VARCHAR(100) NOT NULL -- para busquedas (sin mayusculas ni acentos)
);

-- Tabla de municipios
CREATE TABLE municipalities (
                                id SERIAL PRIMARY KEY,
                                name VARCHAR(100) NOT NULL,
                                province_id INT NOT NULL REFERENCES provinces(id) ON DELETE CASCADE,
                                normalized_name VARCHAR(100) NOT NULL, -- para busquedas (sin mayusculas ni acentos)
                                UNIQUE (name, province_id)
);

-- Tabla: usuarios
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       uuid UUID NOT NULL DEFAULT gen_random_uuid(),
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       email VARCHAR(150) UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       telephone VARCHAR(20),
                       avatar TEXT,
                       description TEXT,
                       role VARCHAR(20) CHECK (role IN ('ADMIN', 'USER')) DEFAULT 'USER',
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       active BOOLEAN DEFAULT TRUE
);

-- Tabla: deportes
CREATE TABLE sports (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL UNIQUE,
                        icon TEXT,
                        alt VARCHAR(20) NOT NULL
);

-- Tabla: instalaciones deportivas (con coordenadas para el iframe de mapa)
CREATE TABLE sport_facilities (
                                  id SERIAL PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL,
                                  municipality_id INT NOT NULL REFERENCES municipalities(id),
                                  street VARCHAR(255),
                                  description TEXT,
                                  latitude NUMERIC(9, 6) NOT NULL,
                                  longitude NUMERIC(9, 6) NOT NULL,
                                  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: imágenes de instalaciones
CREATE TABLE facility_pictures (
                                   id SERIAL PRIMARY KEY,
                                   filename TEXT NOT NULL,
                                   facility_id INT REFERENCES sport_facilities(id) ON DELETE CASCADE,
                                   uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   alt varchar(50) NOT NULL
);

-- Tabla: partidos/eventos deportivos
CREATE TABLE matches (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(150) NOT NULL,
                         description TEXT NOT NULL,
                         sport_id INT NOT NULL REFERENCES sports(id),
                         facility_id INT NOT NULL REFERENCES sport_facilities(id),
                         owner_id INT NOT NULL REFERENCES users(id),
                         event_date TIMESTAMP NOT NULL,
                         end_date TIMESTAMP,
                         price_per_person NUMERIC(6,2) DEFAULT 0,
                         max_participants INT,
                         skill_level VARCHAR(20) CHECK (skill_level IN ('PRINCIPIANTE', 'INTERMEDIO', 'EXPERTO')),
                         visible BOOLEAN DEFAULT TRUE,
                         created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: solicitudes

CREATE TABLE join_requests (
                               id SERIAL PRIMARY KEY,
                               message VARCHAR(100) NOT NULL,
                               created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                               status VARCHAR(20) CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED')),
                               match_id INT REFERENCES matches(id),
                               sender_id INT REFERENCES users(id)
);

-- Tabla intermedia: municipios de interés para usuarios
CREATE TABLE user_municipalities (
                                     user_id INT REFERENCES users(id) ON DELETE CASCADE,
                                     municipality_id INT REFERENCES municipalities(id) ON DELETE CASCADE,
                                     PRIMARY KEY (user_id, municipality_id)
);

-- Tabla: participantes en partidos
CREATE TABLE match_participants (
                                    match_id INT REFERENCES matches(id) ON DELETE CASCADE,
                                    user_id INT REFERENCES users(id) ON DELETE CASCADE,
                                    PRIMARY KEY (match_id, user_id)
);

-- Tabla: seleccion deportes de un usuario
CREATE TABLE user_sports (
                                    sport_id INT REFERENCES sports(id) ON DELETE CASCADE,
                                    user_id INT REFERENCES users(id) ON DELETE CASCADE,
                                    PRIMARY KEY (sport_id, user_id)
);