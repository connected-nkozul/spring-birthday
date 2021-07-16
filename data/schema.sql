CREATE DATABASE IF NOT EXISTS db;

USE db;
DROP TABLE IF EXISTS hit;
DROP TABLE IF EXISTS chart;

CREATE TABLE chart (
    week_id DATE NOT NULL UNIQUE,
    url VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (week_id)
);

CREATE TABLE hit (
    song_id VARCHAR(200) NOT NULL,
    week_id DATE NOT NULL,
    week_rank int NOT NULL,
    song varchar(200) NOT NULL,
    artist varchar(100) NOT NULL,
    weeks_on_chart int DEFAULT NULL,
    peak_position int DEFAULT NULL,
    previous_week int DEFAULT NULL,
    Instance int DEFAULT NULL,
    FOREIGN KEY (week_id) REFERENCES chart(week_id)
);

CREATE TABLE birthday_hit (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  birthday DATE NOT NULL,
  chart_week_id DATE NOT NULL,
  hit_song VARCHAR(100) NOT NULL,
  hit_artist VARCHAR(100) NOT NULL
);

CREATE TABLE popular_artist (
  artist VARCHAR(100) NOT NULL PRIMARY KEY ,
  birthdays INT NOT NULL
);