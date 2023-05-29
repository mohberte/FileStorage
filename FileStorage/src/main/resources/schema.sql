
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    user_profile_id INT UNIQUE,
    role VARCHAR(255),
    
);

CREATE TABLE user_content (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fileName VARCHAR(255) NOT NULL,
    fileType VARCHAR(255) NOT NULL,
    fileSize BIGINT NOT NULL,
    uploadTime DATETIME NOT NULL,
    filePath VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);


CREATE TABLE user_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    user_id INT UNIQUE,
    login_attempt INT NOT NULL DEFAULT 0
);



