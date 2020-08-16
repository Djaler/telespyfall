CREATE TABLE locations
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE games
(
    id          SERIAL PRIMARY KEY,
    location_id SMALLINT NOT NULL REFERENCES locations (id),
    message_id  BIGINT   NOT NULL,
    state       VARCHAR  NOT NULL
);

CREATE TABLE games_players
(
    id          SERIAL PRIMARY KEY,
    game_id     INT     NOT NULL REFERENCES games (id) ON DELETE CASCADE,
    telegram_id INT     NOT NULL,
    username    VARCHAR NOT NULL,
    spy         BOOLEAN NOT NULL,
    UNIQUE (game_id, telegram_id)
);

INSERT INTO locations(name)
VALUES ('База террористов'),
       ('Банк'),
       ('Больница'),
       ('Киностудия'),
       ('Корпоративная вечеринка'),
       ('Овощебаза'),
       ('Партизанский отряд'),
       ('Пассажирский поезд'),
       ('Пиратский корабль'),
       ('Полярная станция'),
       ('Посольство'),
       ('Ресторан'),
       ('Супермаркет'),
       ('Театр'),
       ('Университет'),
       ('Воинская часть'),
       ('Войско крестоносцев'),
       ('Казино'),
       ('Океанский лайнер'),
       ('Орбитальная станция'),
       ('Отель'),
       ('Пляж'),
       ('Подводная лодка'),
       ('Полицейский участок'),
       ('Самолёт'),
       ('Спа-салон'),
       ('Станция техобслуживания'),
       ('Церковь'),
       ('Цирк-шапито'),
       ('Школа')
