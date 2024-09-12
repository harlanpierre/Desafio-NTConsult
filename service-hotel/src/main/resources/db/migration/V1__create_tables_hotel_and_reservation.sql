CREATE TABLE IF NOT EXISTS hotel (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    price_night DECIMAL(6, 2) NOT NULL,
    amenities VARCHAR(255),
    num_rooms INT NOT NULL,
    num_guests INT NOT NULL,
    evaluation DECIMAL(3, 2)
);

CREATE TABLE IF NOT EXISTS reservation (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    hotel_id INT REFERENCES hotel(id) ON DELETE CASCADE,
    date_reservation DATE NOT NULL,
    date_checkin DATE NOT NULL,
    date_checkout DATE NOT NULL,
    name_client VARCHAR(100) NOT NULL,
    contact VARCHAR(100) NOT NULL,
    payment_method VARCHAR(10) NOT NULL
);

CREATE INDEX idx_hotel_location ON hotel(location);
CREATE INDEX idx_hotel_num_rooms ON hotel(num_rooms);
CREATE INDEX idx_hotel_num_guests ON hotel(num_guests);

CREATE INDEX idx_reservation_checkin ON reservation(date_checkin);
CREATE INDEX idx_reservation_checkout ON reservation(date_checkout);

CREATE INDEX idx_reservation_hotel_id ON reservation(hotel_id);

