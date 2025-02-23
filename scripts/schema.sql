CREATE TABLE Product
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       DECIMAL(19, 4) CHECK (price > 0),
    category    VARCHAR(255) NOT NULL,
    status      CHAR(1) DEFAULT 'A'
);


-- Load initial data

-- INSERT INTO Product (name, description, price, category, status)
-- VALUES ("Laptop", "High-end gaming laptop", 1200.99, "Electronics", 'A'),
--        ("Smartphone", "Latest Android smartphone", 699.50, "Electronics", 'A'),
--        ("Coffee Maker", "Automatic coffee machine", 89.99, "Home Appliances", 'A'),
--        ("Desk Chair", "Ergonomic office chair", 149.99, "Furniture", 'A'),
--        ("Headphones", "Wireless noise-canceling headphones", 199.99, "Electronics", 'A'),
--        ("Tablet", "10-inch display tablet", 329.99, "Electronics", 'A'),
--        ("Monitor", "4K UHD monitor", 450.00, "Electronics", 'A'),
--        ("Keyboard", "Mechanical gaming keyboard", 99.99, "Electronics", 'A'),
--        ("Mouse", "Wireless gaming mouse", 49.99, "Electronics", 'A'),
--        ("Smartwatch", "Fitness tracking smartwatch", 249.99, "Wearable Tech", 'A'),
--        ("Backpack", "Water-resistant laptop backpack", 59.99, "Accessories", 'A'),
--        ("Sneakers", "Running shoes", 89.99, "Fashion", 'A'),
--        ("Sunglasses", "UV protection sunglasses", 39.99, "Fashion", 'A'),
--        ("Book", "Best-selling novel", 14.99, "Books", 'A'),
--        ("Desk Lamp", "LED adjustable desk lamp", 29.99, "Home", 'A'),
--        ("Smart Speaker", "Voice assistant speaker", 129.99, "Electronics", 'A'),
--        ("Bluetooth Speaker", "Portable Bluetooth speaker", 79.99, "Electronics", 'A'),
--        ("Fitness Tracker", "Heart rate & step tracking", 59.99, "Wearable Tech", 'A'),
--        ("External Hard Drive", "1TB USB 3.0 HDD", 99.99, "Storage", 'A'),
--        ("Water Bottle", "Insulated stainless steel bottle", 24.99, "Lifestyle", 'A');