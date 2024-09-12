INSERT INTO Hotel (name, location, price_night, amenities, num_rooms, num_guests, evaluation)
VALUES
                  ('Hotel A', 'Porto de Galinhas', 150.00, 'WiFi, Piscina, Banheira, Roupa de Cama e Banho', 2, 5, 4.5),
                  ('Hotel B', 'Cabo Branco', 200.00, 'WiFi, Academia, Espaço Infantil, Secador', 1, 3, 4.2),
                  ('Hotel C', 'Muro Alto', 180.00, 'WiFi, Televisao, Ar-Condicionado, Sofa', 3, 6, 4.7),
                  ('Hotel D', 'Maragogi', 170.00, 'WiFi, Piscina, Academia, Roupa de Banho, Lavanderia', 1, 2, 4.6),
                  ('Hotel E', 'Sao Jose da Coroa Grande', 220.00, 'WiFi, Spa, Coworking, Geladeira', 1, 2, 4.3);

-- Inserindo dados na tabela Reservation
INSERT INTO Reservation (hotel_id, date_reservation, date_checkin, date_checkout, name_client, contact, payment_method)
VALUES
                         (1, '2024-09-01', '2024-09-10', '2024-09-15', 'Harlan Pierre', 'harlan.pierre@email.com', 'Credit'),
                         (2, '2024-09-02', '2024-09-12', '2024-09-17', 'Alice Soares', 'alice.soares@email.com', 'Debit'),
                         (3, '2024-09-03', '2024-09-15', '2024-09-20', 'Lailson Santos', 'lailson.santos@email.com', 'Pix'),
                         (4, '2024-09-04', '2024-09-18', '2024-09-22', 'Joao Pedro', 'joao_pedro@email.com', 'Credit'),
                         (5, '2024-09-05', '2024-09-20', '2024-09-25', 'Maria Gabriela', 'maria_gabriela@email.com', 'Pix');